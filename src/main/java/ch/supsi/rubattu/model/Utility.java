package ch.supsi.rubattu.model;

public class Utility {

    public static int costOf(int[] tour, DistanceMatrix distanceMatrix) {
        int cost = 0;
        for (int q = 0; q<tour.length - 1; ++q) {
            int city = tour[q];
            int nextCity = tour[q + 1];
            cost += distanceMatrix.db(city, nextCity);
        }
        return cost;
    }
}
