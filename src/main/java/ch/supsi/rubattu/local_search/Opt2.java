package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.DistanceMatrix;

// class that perform a tour optimization according to 2-opt algorithm
// the class has a field that refers to the distance matrix passed during construction
// not used anymore by the Application (2h-opt instead)
public class Opt2 implements LocalSearch {

    private DistanceMatrix distanceMatrix; // matrix of distance used during optimization

    public Opt2(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    // the optimize method receive a tour and optimizes it
    public void optimize(int[] iTour) {
        // let's copy the tour provided by input and work on that
        int[] tour = new int[iTour.length];
        System.arraycopy(iTour, 0, tour, 0, iTour.length);
        int improvement; //will contains improvement (or not...)
        boolean move;
        // move: initialized to false at the beginning of each stage,
        // becomes true when a 2-opt move is perform
        // let's start optimization
        do {
            move = false;
            for (int i = 1; i < tour.length - 2; i++) {
                for (int j = i + 1; j < tour.length - 1; j++) {
                    // check the improvement by apply the 2-opt move with the current pair of arcs
                    // improvement could be:
                    // . > 0 real improvement
                    // . equals 0, 2-opt move leads to a different solution but no improvement has been made
                    // . < 0, a 2-opt move will produce a worse solution
                    improvement = checkImprovement(i, j, tour);
                    if (improvement > 0) {
                        // if improvement is positive, perform the 2-opt move
                        exchange(tour, i, j);
                        move = true;
                    }
                }
            }
        } while (move);
    }

    // this method check the improvement by the subtraction to the current arcs cost the candidate ones
    private int checkImprovement(int i, int j, int[] tour) {
        int a = tour[i-1];
        int b = tour[i];
        int c = tour[j];
        int d = tour[j+1];
        return distanceMatrix.db(a, b) + distanceMatrix.db(c, d) - distanceMatrix.db(a, c) - distanceMatrix.db(b, d);
    }

    // this method perform the 2-opt move
    private void exchange(int[] tour, int i, int j) {
        for (;i < j; i++, j--) {
            int tmp = tour[i];
            tour[i] = tour[j];
            tour[j] = tmp;
        }
    }
}
