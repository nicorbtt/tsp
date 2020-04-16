package ch.supsi.rubattu.model;

import java.util.HashSet;
import java.util.Set;

// utility class with some stuff
public class Utility {

    // method that calculate the length of a tour / cost of a solution
    public static int costOf(int[] tour, DistanceMatrix distanceMatrix) {
        int cost = 0;
        for (int q = 0; q<tour.length - 1; ++q)
            cost += distanceMatrix.db(tour[q], tour[q + 1]);
        return cost;
    }

    // validator method for tour / check is solution is feasible
    public static boolean validate(int[] tour, DistanceMatrix distanceMatrix) {
        int start = tour[0];
        int end = tour[tour.length - 1];
        // CHECK if start city is equal to end city
        if (start != end) return false;
        // set that holds the visited cities
        Set<Integer> checkpoints = new HashSet<>();
        // replicate cost calculation from raw distance matrix to verify cost
        int costInspector = 0;
        // let's go through the tour
        for (int q = 0; q<tour.length - 1; ++q) {
            int current = tour[q];
            int next = tour[q + 1];
            costInspector += distanceMatrix.db(current, next);
            // CHECK if city has been already visited
            if (checkpoints.contains(current)) {
                return false;
            }
            checkpoints.add(current);
        }
        // CHECK all cities has been visited
        // CHECK cost is valid
        return (checkpoints.size() == distanceMatrix.dim()
                &&
                costInspector == costOf(tour, distanceMatrix));
    }
}
