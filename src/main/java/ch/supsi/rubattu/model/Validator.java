package ch.supsi.rubattu.model;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public boolean validate(int[] tour, DistanceMatrix distanceMatrix) {
        int start = tour[0];
        int end = tour[tour.length - 1];
        if (start != end) return false;

        List<Integer> checkpoints = new ArrayList<>();

        int costInspector = 0;

        for (int q = 0; q<tour.length - 1; ++q) {
            int current = tour[q];
            int next = tour[q + 1];
            costInspector += distanceMatrix.db(current, next);
            if (checkpoints.contains(current)) {
                return false;
            }
            checkpoints.add(current);
        }
        return (costInspector == Utility.costOf(tour, distanceMatrix));
    }
}
