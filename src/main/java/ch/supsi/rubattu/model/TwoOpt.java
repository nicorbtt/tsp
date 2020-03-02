package ch.supsi.rubattu.model;

import java.util.ArrayList;
import java.util.List;

public class TwoOpt {

    public static Result optimize(Result result, Data data) {

        List<City> tmp = new ArrayList<>(result.tour());
        int best = result.cost();
        boolean swaps = true;

        while (swaps) {
            swaps = false;
            for (int i = 1; i < tmp.size() - 2; i++) {
                for (int j = i + 1; j < tmp.size() - 1; j++) {
                    int improvement = checkImprovement(i, j, tmp, data);
                    if (improvement > 0) {
                        int current = best - improvement;
                        if (current < best) {
                            swap(tmp, i, j);
                            best = current;
                            swaps = true;
                        }
                    }
                }
            }
        }
        return new Result(tmp, best);
    }

    private static int checkImprovement(int i, int j, List<City> route, Data data) {
        int a = route.get(i-1).getId() - 1;
        int b = route.get(i).getId() - 1;
        int c = route.get(j).getId() - 1;
        int d = route.get(j+1).getId() - 1;
        Integer[][] matrix = data.getData();
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
