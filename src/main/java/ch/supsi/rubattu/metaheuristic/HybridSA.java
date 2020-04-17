package ch.supsi.rubattu.metaheuristic;

import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.ExecutionInfo;
import ch.supsi.rubattu.model.Stopwatch;
import ch.supsi.rubattu.model.Utility;

import java.util.Random;

// class that perform a metaheuristic tour optimization according to Simulated Annealing + local search
// the class has a field that refers to the distance matrix passed during construction, an integer represent
// the know optimum for the tsp problem, the time it can run (millis), the random object for seed, the
// stopwatch object to which refer to and the local search algorithm to perform inside (as intensification process)
public class HybridSA implements Metaheuristic {

    private DistanceMatrix distanceMatrix;
    private int optimum;
    private long time;
    private Random random;
    private Stopwatch stopwatch;
    private LocalSearch localSearch;

    public HybridSA(
            DistanceMatrix distanceMatrix,
            int optimum,
            long time,
            Random random,
            Stopwatch stopwatch,
            LocalSearch localSearch
    ) {
        this.distanceMatrix = distanceMatrix;
        this.optimum = optimum;
        this.time = time;
        this.random = random;
        this.stopwatch = stopwatch;
        this.localSearch = localSearch;
    }

    // optimize method takes the tour that needs to be optimized, an object contains best solution info that will be
    // filled up at the end of execution and return the optimized tour
    public void optimize(int[] tour, ExecutionInfo executionInfo) {
        // number of cities
        int n = distanceMatrix.dim();
        // let's create an array that we will use as current solution during optimization and fill the corresponding
        // initial cost
        int[] currentSolution = new int[n + 1];
        System.arraycopy(tour, 0, currentSolution, 0, n + 1);
        int currentCost = Utility.costOf(tour, distanceMatrix);
        // let's create an array that will hold the best solution that will be discovered during the execution and
        // fill its corresponding cost (initially equals to previews current cost
        int[] bestSolution = new int[n + 1];
        System.arraycopy(tour, 0, bestSolution, 0, n + 1);
        int bestCost = currentCost;
        int[] newSolution = new int[n + 1];   // this array will hold a temporary tour during diversification and
        // intensification process so as not to change the current tour
        // initial temperature as been calculated according to Johnson formula which allows an initial acceptance
        // rate of 50%. as L parameter as been considered the initial solution cost
        double temp = (1.5 * currentCost) / (Math.sqrt(n));
        // temperature lenght is by default 150, a random value with whom smallest tsp problem (eil76, kroA100, ch130,
        // d198 and lin318) have been resolved to optimum in a short time of tuning
        // considering that the execution time of a round is depend on problem dimension, for the other cities the
        // length of the temperature has been set in detail to reach a final temperature of 10-3 in all cases (on my
        // hardware of course...).
        double tempLength = 150;
        if (n > 400) tempLength = 70;
        if (n > 700) tempLength = 20;
        if (n > 1000) tempLength = 10;
        if (n > 1500) tempLength = 3;
        // alpha is the cooling rate, always 0.95 for every problem
        double alpha = 0.95;
        // some variables and array using during the execution
        int i;
        int[] newResult;
        int fNext;          // cost of solution after diversification and intensification step
        int deltaE;         // current cost minus fNext
        // dice and probability for accept the next solution when it's worse or not
        double dice;
        double probability;
        int iterations = 0;         // iterations count
        int iterationBest = 0;      // best iteration count
        int[] tmpTour = new int[tour.length];
        int a, b, c, d, tmp;

        // let's start the execution using my time
        while (stopwatch.time() < time) {
            if (bestCost == optimum) break; // check used for don't waste time in small problems
            // hold the temperature for 'temperatureLength' iterations
            for (i = 0; i < tempLength && stopwatch.time() < time; i++) {
                iterations++;
                System.arraycopy(currentSolution, 0, newSolution, 0, n + 1);
                // DIVERSIFICATION (Double Bridge)
                // Randomly generate 4 tour indexes and order them using a sorting network, then check their
                // validity (if not repeat) and perform double bridge move using a tmp array as a support
                System.arraycopy(newSolution, 0, tmpTour, 0, n + 1);
                do {
                    a = random.nextInt(n);
                    b = random.nextInt(n);
                    c = random.nextInt(n);
                    d = random.nextInt(n);
                    if (a > b) { tmp = a; a = b; b = tmp; }
                    if (c > d) { tmp = c; c = d; d = tmp; }
                    if (a > c) { tmp = a; a = c; c = tmp; }
                    if (b > d) { tmp = b; b = d; d = tmp; }
                    if (b > c) { tmp = b; b = c; c = tmp; }
                    if ((a + 1 < b) && (b + 1 < c) && (c + 1 < d)) {
                        System.arraycopy(tmpTour, c + 1, newSolution, a + 1, d - c);
                        System.arraycopy(tmpTour, b + 1, newSolution, a + d - c + 1, c - b);
                        System.arraycopy(tmpTour, a + 1, newSolution, a + d - c + c - b + 1, b - a);
                        break;
                    }
                } while (true);
                // INTENSIFICATION (local search - 2h-opt)
                localSearch.optimize(newSolution);
                // calculate cost of new solution and delta with current
                // N.B the calculation cost is O(n) perform integrally for simple implementation, could be optimized by
                // always hold updated the cost during operations but that will become more complex :)
                fNext = Utility.costOf(newSolution, distanceMatrix);
                deltaE = currentCost - fNext;
                if (deltaE > 0) { // we have found a better solution then current
                    // take it as current, update cost...
                    System.arraycopy(newSolution, 0, currentSolution, 0, n + 1);
                    currentCost = fNext;
                    if (fNext < bestCost) {
                        // and if we have found a best solution of all store it with its cost
                        System.arraycopy(newSolution, 0, bestSolution, 0, n + 1);
                        bestCost = fNext;
                        iterationBest = iterations;
                        if (bestCost == optimum) break;
                    }
                } else { // decide if proceed with this solution or discard it based on probability e^(∆E/T) N.B. I
                    // decide to take deltaE positive if there is an improvement instead of the opposite
                    dice = random.nextDouble();
                    probability = Math.exp(deltaE / temp);
                    if (dice < probability) { // we proceed with this solution even if worse
                        System.arraycopy(newSolution, 0, currentSolution, 0, n + 1);
                        currentCost = fNext;
                    }
                }
            }
            temp *= alpha; // cool down the temperature
        }
        // let's fill execution info object and return the best solution discovered
        executionInfo.setNumberOfIterations(iterations);
        executionInfo.setBestIterationAt(iterationBest);
        executionInfo.setCost(bestCost);
        // Reverse best solution into our tour
        System.arraycopy(bestSolution, 0, tour, 0, n + 1);
    }

    private static void randomSwap(int[] array, Random random) {
        int i, j;
        do {
            i = random.nextInt(array.length - 2) + 1;
            j = random.nextInt(array.length - 2) + 1;
        } while (i == j);
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}