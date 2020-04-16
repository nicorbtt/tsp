package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.DistanceMatrix;

public class Opt2 implements LocalSearch {

    private DistanceMatrix distanceMatrix;

    public Opt2(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int[] optimize(int[] result) {

        int[] tour = new int[result.length];
        System.arraycopy(result, 0, tour, 0, result.length);

        int improvement;
        boolean swaps;

        do {
            swaps = false;
            for (int i = 1; i < tour.length - 2; i++) {
                for (int j = i + 1; j < tour.length - 1; j++) {
                    improvement = checkImprovement(i, j, tour);
                    if (improvement > 0) {
                        exchange(tour, i, j);
                        swaps = true;
                    }
                }
            }
        } while (swaps);
        return tour;
    }

    private int checkImprovement(int i, int j, int[] tour) {
        int a = tour[i-1];
        int b = tour[i];
        int c = tour[j];
        int d = tour[j+1];
        return distanceMatrix.db(a, b) + distanceMatrix.db(c, d) - distanceMatrix.db(a, c) - distanceMatrix.db(b, d);
    }

    private void exchange(int[] tour, int i, int j) {
        for (;i < j; i++, j--) {
            int tmp = tour[i];
            tour[i] = tour[j];
            tour[j] = tmp;
        }
    }
}
