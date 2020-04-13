package ch.supsi.rubattu.metaheuristic;

import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Stopwatch;
import ch.supsi.rubattu.model.Utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HybridSA {

    private DistanceMatrix distanceMatrix;
    private int bestKnow;
    private long time;
    private Random random;
    private Stopwatch stopwatch;
    private LocalSearch localSearch;
    private String tspProblem;

    public HybridSA(
            DistanceMatrix distanceMatrix,
            int bestKnow,
            long time,
            Random random,
            Stopwatch stopwatch,
            LocalSearch localSearch,
            String tspProbleam
    ) {
        this.distanceMatrix = distanceMatrix;
        this.bestKnow = bestKnow;
        this.time = time;
        this.random = random;
        this.stopwatch = stopwatch;
        this.localSearch = localSearch;
        this.tspProblem = tspProbleam;
    }

    public int[] optimize(int[] tour, AtomicInteger iterationOfBest) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, InstantiationException {

        int n = distanceMatrix.dim();

        int[] currentSolution = new int[n+1];
        int currentCost = Utility.costOf(tour, distanceMatrix);
        System.arraycopy(tour, 0, currentSolution,0 , n+1);

        int[] bestSolution = new int[n+1];
        int bestCost = currentCost;
        System.arraycopy(tour, 0, bestSolution,0 , n+1);

        int[] newSolution = new int[n+1];

        double temp = (1.5*currentCost)/(Math.sqrt(n));
        double tempLength;
        tempLength = 150;
        if (n > 400)    {
            tempLength = 70;
        }
        if (n > 700)    {
            tempLength = 20;
        }
        if (n > 1000)   {
            tempLength = 10;
        }
        if (n > 1500) tempLength = 3;
        double alpha = 0.95;

        int i;
        int[] newResult;
        int fNext;
        int deltaE;
        double dado;
        double probability;

        int cont = 0;
        int bestCont = 0;

        List<Method> permutations = new ArrayList<>();
        Method doubleBridge = Class.forName("ch.supsi.rubattu.metaheuristic.HybridSA")
                .getDeclaredMethod("doubleBridge", int[].class, Random.class);
        Method randomSwap = Class.forName("ch.supsi.rubattu.metaheuristic.HybridSA")
                .getDeclaredMethod("randomSwap", int[].class, Random.class);
        switch (tspProblem) {
            case "pcb442":
                permutations.add(randomSwap);
                permutations.add(doubleBridge);
                permutations.add(randomSwap);
                permutations.add(doubleBridge);
                break;
            case "rat783":
            case "fl1577":
                permutations.add(doubleBridge);
                permutations.add(doubleBridge);
                break;
            default:
                permutations.add(randomSwap);
                permutations.add(doubleBridge);
                permutations.add(randomSwap);
                break;
        }

        while (stopwatch.time() < time) {
            if (bestCost == bestKnow) break;
            for (i = 0; i < tempLength && stopwatch.time() < time; i++) {
                cont++;
                // Permutation
                System.arraycopy(currentSolution, 0, newSolution, 0, n + 1);
                for (Method method : permutations) method.invoke(null, newSolution, random);
                // Local search
                newResult = localSearch.optimize(newSolution);
                fNext = Utility.costOf(newResult, distanceMatrix);
                deltaE = currentCost - fNext;
                if (deltaE > 0) {
                    //better++;
                    currentSolution = newResult;
                    currentCost = fNext;
                    if (fNext < bestCost) {
                        System.arraycopy(newResult, 0, bestSolution, 0, n + 1);
                        bestCost = fNext;
                        bestCont = cont;
                        if (bestCost == bestKnow) break;
                    }
                } else {
                    if (deltaE == 0) {
                        //zero++;
                        continue;
                    }
                    //choise++;
                    dado = random.nextDouble();
                    probability = Math.exp(deltaE / temp);
                    if (dado < probability) {
                        //si++;
                        currentSolution = newResult;
                        currentCost = fNext;
                    } //else no++;
                }
            }
            temp *= alpha;
        }
//        out("Iterazioni", cont);
//        out("Best at", bestCont);
        iterationOfBest.set(bestCont);
//        out("Temp", temp);
//        out("Better", better);
//        out("Choise", choise);
//        out("Zero", zero);
//        out("SI", (double) si*100 / choise);
//        out("NO", (double) no*100 / choise);
        return bestSolution;
    }

    private void out(String s, double v) {
        System.out.println(s+": " + v);
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

    private static void doubleBridge(int[] array, Random random) {
        int[] copy = array.clone();
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
                System.arraycopy(copy, k+1, array, i+1, l-k);
                System.arraycopy(copy, j+1, array, i+l-k+1, k-j);
                System.arraycopy(copy, i+1, array, i+l-k+k-j+1, j-i);
                break;
            }
        } while (true);
    }

    private static void doubleBridgeOld(int[] array, Random random) {
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
