package ch.supsi.rubattu.metaheuristic;

import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Result;
import ch.supsi.rubattu.model.Stopwatch;

import java.util.Random;

public class HybridSA {

    public static Result optimize(Result result, DistanceMatrix distanceMatrix, int bestKnow, long time, long seed) {

        int n = distanceMatrix.data().length;
        Random random = new Random(seed);

        double tempI = (1.5*result.cost())/(Math.sqrt(n));   // [[Bonomi and Lutton, 1984]
        //double tempI = result.cost() / Math.sqrt(n);       // [Johnson]
        double temp = tempI;
        double alpha;

        double tempLength = 50;

        City[] currentSolution = result.tour().clone();
        int currentCost = result.cost();

        City[] bestSolution = currentSolution.clone();
        int bestCost = currentCost;


        do {
            double ratio = ((double)(currentCost-bestKnow)/bestKnow)*100;
            //System.out.println(temp + "\t\t" + ratio);
            if (ratio == 0) break;
            for (int i=0; i<tempLength; ++i) {
                if (Stopwatch.getInstance().time() > time) break;
                City[] newSolution = doubleBridge(currentSolution, random).clone();

                int newCost = distanceOf(newSolution, distanceMatrix);
                Result newResult = Opt2.optimize(new Result(newSolution, newCost), distanceMatrix);
                int fNext = newResult.cost();
                int deltaE =  fNext - currentCost;
                if (deltaE < 0) {
                    currentSolution = newResult.tour();
                    currentCost = newResult.cost();
                    if (fNext < bestCost) {
                        bestSolution = currentSolution.clone();
                        bestCost = currentCost;
                    }
                }
                else {
                    double rand = random.nextDouble();
                    double probability = Math.exp(-(deltaE / temp));
                    if (rand < probability) {
                        currentSolution = newResult.tour();
                        currentCost = newResult.cost();
                    }
                }
            }
            alpha = 1 - ((double) Stopwatch.getInstance().time() / time);
            if (alpha < 0) alpha = 0;
            temp = tempI * alpha;

            //temp *= alpha;
        } while (Stopwatch.getInstance().time() <= time);
        return new Result(bestSolution, bestCost);
    }

    private static City[] doubleBridge(City[] array, Random random) {
        int pos1, pos2, pos3, pos4;
        City[] temp = array.clone();
        do {
            pos1 = random.nextInt(array.length - 1);
            pos2 = random.nextInt(array.length - 1);
            pos3 = random.nextInt(array.length - 1);
            pos4 = random.nextInt(array.length - 1);
        } while (pos1 < 0 || pos2 < pos1 || pos3 < pos2 || pos4 < pos3);

        int k = pos1 + 1;
        System.arraycopy(temp,k,array,pos3 + 1,pos4-pos3);
        k+=pos4-pos3;
        System.arraycopy(temp,k,array,pos2 + 1,pos3-pos2);
        k+=pos3-pos2;
        System.arraycopy(temp,k,array,pos1 + 1,pos2-pos1);
        return array;
    }

    private static int distanceOf(City[] tour, DistanceMatrix distanceMatrix) {
        int cost = 0;
        int[][] distances = distanceMatrix.data();
        for (int q = 0; q<tour.length - 1; ++q) {
            int city = tour[q].id();
            int nextCity = tour[q + 1].id();
            cost += distances[city - 1][nextCity - 1];
        }
        return cost;
    }
}
