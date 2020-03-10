package ch.supsi.rubattu.model;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static boolean validate(Result result, DistanceMatrix distanceMatrix) {
        int start = result.tour()[0].id();
        int end = result.tour()[result.tour().length - 1].id();
        if (start != end) {
            System.out.println("start != end");
            return false;
        }

        List<Integer> checkpoints = new ArrayList<>();

        int costValidator = 0;
        int[][] distances = distanceMatrix.data();

        for (int q = 0; q<result.tour().length - 1; ++q) {
            int city = result.tour()[q].id();
            int nextCity = result.tour()[q + 1].id();
            costValidator += distances[city - 1][nextCity - 1];
            if (checkpoints.contains(city)) {
                System.out.println("city twice");
                return false;
            }
            checkpoints.add(city);
        }

        if (costValidator != result.cost()) {
            System.out.println("cost error _ " + costValidator + " " + result.cost());
            return false;
        }

        return true;
    }
}
