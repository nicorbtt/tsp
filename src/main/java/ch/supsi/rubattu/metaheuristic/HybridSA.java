package ch.supsi.rubattu.metaheuristic;

import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.model.*;

import java.util.Random;

public class HybridSA {

    private DistanceMatrix distanceMatrix;
    private int bestKnow;
    private long time;
    private Random random;
    private Stopwatch stopwatch;
    private LocalSearch localSearch;

    public HybridSA(
            DistanceMatrix distanceMatrix,
            int bestKnow,
            long time,
            Random random,
            Stopwatch stopwatch,
            LocalSearch localSearch
    ) {
        this.distanceMatrix = distanceMatrix;
        this.bestKnow = bestKnow;
        this.time = time;
        this.random = random;
        this.stopwatch = stopwatch;
        this.localSearch = localSearch;
    }

    public int[] optimize(int[] tour) {

        int n = distanceMatrix.dim();

        int[] currentSolution = new int[n+1];
        int currentCost = Utility.costOf(tour, distanceMatrix);
        System.arraycopy(tour, 0, currentSolution,0 , n+1);

        int[] bestSolution = new int[n+1];
        int bestCost = currentCost;
        System.arraycopy(tour, 0, bestSolution,0 , n+1);

        int[] newSolution = new int[n+1];

        double temp = (1.5*currentCost)/(Math.sqrt(n));
        //double temp = 100;
        double tempLength = 20;
        if (n > 450) tempLength /= 4;
        //double tempLength = 100; //prima era 20
        double alpha = 0.95;

        int i;
        int[] newResult;
        int fNext;
        int deltaE;
        double dado;
        double probability;

        while (stopwatch.time() < time) {
            if (bestCost == bestKnow) break;
            for (i = 0; i < tempLength && stopwatch.time() < time; i++) {
                // Permutation
                System.arraycopy(currentSolution, 0, newSolution, 0, n + 1);
                doubleBridge(newSolution);
                // Local search
                newResult = localSearch.optimize(newSolution);

                fNext = Utility.costOf(newResult, distanceMatrix);
                deltaE = currentCost - fNext;
                if (deltaE > 0) {
                    currentSolution = newResult;
                    currentCost = fNext;
                    if (fNext < bestCost) {
                        System.arraycopy(newResult, 0, bestSolution, 0, n + 1);
                        bestCost = fNext;
                        if (bestCost == bestKnow) break;
                    }
                } else {
                    dado = random.nextDouble();
                    probability = Math.exp(deltaE / temp);
                    if (dado < probability) {
                        currentSolution = newResult;
                        currentCost = fNext;
                    }
                }
            }
            temp *= alpha;
        }
        return bestSolution;
    }

    private void out(String s, double v) {
        System.out.println(s+": " + v);
    }

    private void randomSwap(int[] array) {
        int i, j;
        do {
            i = random.nextInt(array.length - 2) + 1;
            j = random.nextInt(array.length - 2) + 1;
        } while (i == j);
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void doubleBridge(int[] array) {
        int[] localCopy = array.clone();
        int i, j, k, l, tmp;
        do {
            i = random.nextInt(array.length - 1);
            j = random.nextInt(array.length - 1);
            k = random.nextInt(array.length - 1);
            l = random.nextInt(array.length - 1);

            if (i > j) {
                tmp = i;
                i = j;
                j = tmp;
            }
            if (k > l) {
                tmp = k;
                k = l;
                l = tmp;
            }
            if (i > k) {
                tmp = i;
                i = k;
                k = tmp;
            }
            if (j > l) {
                tmp = j;
                j = l;
                l = tmp;
            }
            if (j > k) {
                tmp = j;
                j = k;
                k = tmp;
            }

            if ((i+1<j) && (j+1<k) && (k+1<l)) {
                System.arraycopy(localCopy, k+1, array, i+1, l-k);
                System.arraycopy(localCopy, j+1, array, i+l-k+1, k-j);
                System.arraycopy(localCopy, i+1, array, i+l-k+k-j+1, j-i);
                break;
            }
        } while (true);
    }

    private void doubleBridgeOld(int[] array) {
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
