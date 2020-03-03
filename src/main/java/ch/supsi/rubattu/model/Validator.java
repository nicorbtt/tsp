package ch.supsi.rubattu.model;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static boolean validate(Result result, DistanceMatrix distanceMatrix) {
        int start = result.tour().get(0).id();
        int end = result.tour().get(result.tour().size() - 1).id();
        if (start != end) return false;

        List<Integer> checkpoints = new ArrayList<>();

        int costValidator = 0;
        Integer[][] distances = distanceMatrix.data();

        for (int q = 0; q<result.tour().size() - 1; ++q) {
            int city = result.tour().get(q).id();
            int nextCity = result.tour().get(q + 1).id();
            costValidator += distances[city - 1][nextCity - 1];
            if (checkpoints.contains(city)) return false;
            checkpoints.add(city);
        }

        return costValidator == result.cost();
    }
}
