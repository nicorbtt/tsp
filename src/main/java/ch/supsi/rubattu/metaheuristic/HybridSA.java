package ch.supsi.rubattu.metaheuristic;

import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.model.*;

import java.util.Random;

public class HybridSA {

    private int[] result;
    private DistanceMatrix distanceMatrix;
    private int bestKnow;
    private long time;
    private Random random;
    private Stopwatch stopwatch;
    private LocalSearch localSearch;

    public HybridSA(
            int[] result,
            DistanceMatrix distanceMatrix,
            int bestKnow,
            long time,
            Random random,
            Stopwatch stopwatch,
            LocalSearch localSearch
    ) {
        this.result = result;
        this.distanceMatrix = distanceMatrix;
        this.bestKnow = bestKnow;
        this.time = time;
        this.random = random;
        this.stopwatch = stopwatch;
        this.localSearch = localSearch;
    }

    public int[] optimize() {

        int n = distanceMatrix.dim();

        int[] currentSolution = new int[n+1];
        int currentCost = Utility.costOf(result, distanceMatrix);
        System.arraycopy(result, 0, currentSolution,0 , n+1);

        int[] bestSolution = new int[n+1];
        int bestCost = currentCost;
        System.arraycopy(result, 0, bestSolution,0 , n+1);

        int[] newSolution = new int[n+1];

        double temp = (1.5*currentCost)/(Math.sqrt(n));
        double tempLength = 100; //prima era 20
        double alpha = 0.95;

        while (stopwatch.time() < time) {
            if (bestCost == bestKnow) break;
            for (int i=0; i<tempLength && stopwatch.time() < time; ++i) {

                // Permutation
                System.arraycopy(currentSolution, 0, newSolution, 0, n+1);
                doubleBridge(newSolution);
                // Local search
                int[] newResult = localSearch.optimize(newSolution);

                int fNext = Utility.costOf(newResult, distanceMatrix);
                int deltaE = currentCost - fNext;
                if (deltaE > 0) {
                    currentSolution = newResult;
                    currentCost = fNext;
                    if (fNext < bestCost) {
                        System.arraycopy(newResult, 0, bestSolution,0 , n+1);
                        bestCost = fNext;
                        if (bestCost == bestKnow) break;
                    }
                }
                else {
                    double rand = random.nextDouble();
                    double probability = Math.exp(deltaE / temp);
                    if (rand < probability) {
                        currentSolution = newResult;
                        currentCost = fNext;
                    }
                }
            }
            temp *= alpha;
        }
        return bestSolution;
    }

    private void doubleBridge(int[] array) {
        int[] tmp = array.clone();
        int i, j, k, l;
        i = 1 + random.nextInt(array.length / 5);
        j = i + 1 + random.nextInt(array.length / 5);
        k = j + 1 + random.nextInt(array.length / 5);
        l = k + 1 + random.nextInt(array.length / 5);

        System.arraycopy(tmp, k+1, array, i+1, l-k);
        System.arraycopy(tmp, j+1, array, i+l-k+1, k-j);
        System.arraycopy(tmp, i+1, array, i+l-k+k-j+1, j-i);
    }
}
