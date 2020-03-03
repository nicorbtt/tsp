package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Result;

import java.util.ArrayList;
import java.util.List;

public class Opt2 {

    public static Result optimize(Result result, DistanceMatrix distanceMatrix) {

        List<City> tour = new ArrayList<>(result.tour());
        int best = result.cost();

        int improvement, bestImprovement;
        int I, J;
        boolean swaps;

        do {
            bestImprovement = I = J = 0;
            for (int i = 1; i < tour.size() - 2; i++) {
                for (int j = i + 1; j < tour.size() - 1; j++) {
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
                swap(tour, I, J);
                best -= bestImprovement;
                swaps = true;
            }
        } while (swaps);
        return new Result(tour, best);
    }

    private static int checkImprovement(int i, int j, List<City> route, DistanceMatrix distanceMatrix) {
        int a = route.get(i-1).id() - 1;
        int b = route.get(i).id() - 1;
        int c = route.get(j).id() - 1;
        int d = route.get(j+1).id() - 1;
        Integer[][] matrix = distanceMatrix.data();
        return  matrix[a][b] + matrix[c][d] - (matrix[a][c] + matrix[b][d]);
    }

    private static void swap(List<City> tour, int i, int j) {
        for (;i < j; i++, j--) {
            City tmp = tour.get(i);
            tour.set(i, tour.get(j));
            tour.set(j, tmp);
        }
    }
}
