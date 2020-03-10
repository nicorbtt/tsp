package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Result;

public class Opt2 {

    public static Result optimize(Result result, DistanceMatrix distanceMatrix) {

        City[] tour = result.tour().clone();
        int best = result.cost();

        int improvement, bestImprovement;
        int I, J;
        boolean swaps;

        do {
            bestImprovement = I = J = 0;
            for (int i = 1; i < tour.length - 2; i++) {
                for (int j = i + 1; j < tour.length - 1; j++) {
                    improvement = checkImprovement(i, j, tour, distanceMatrix);
                    if (improvement > bestImprovement) {
                        bestImprovement = improvement;
                        I = i;
                        J = j;
                    }
                }
            }
            swaps = false;
            if (bestImprovement > 0) {
                exchange(tour, I, J);
                swaps = true;
            }
        } while (swaps);
        best = calculateCost(tour, distanceMatrix);
        return new Result(tour, best);
    }

    private static int calculateCost(City[] tour, DistanceMatrix distanceMatrix) {
        int cost = 0;
        int[][] distances = distanceMatrix.data();
        for (int q = 0; q<tour.length - 1; ++q) {
            int city = tour[q].id();
            int nextCity = tour[q + 1].id();
            cost += distances[city - 1][nextCity - 1];
        }
        return cost;
    }

    private static int checkImprovement(int i, int j, City[] route, DistanceMatrix distanceMatrix) {
        int a = route[i-1].id() - 1;
        int b = route[i].id() - 1;
        int c = route[j].id() - 1;
        int d = route[j+1].id() - 1;
        int[][] matrix = distanceMatrix.data();
        return  matrix[a][b] + matrix[c][d] - (matrix[a][c] + matrix[b][d]);
    }

    private static void exchange(City[] tour, int i, int j) {
        for (;i < j; i++, j--) {
            City tmp = tour[i];
            tour[i] = tour[j];
            tour[j] = tmp;
        }
    }
}
