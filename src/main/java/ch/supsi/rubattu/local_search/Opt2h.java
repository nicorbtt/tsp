package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.DistanceMatrix;

// class that perform a tour optimization according to 2h-opt algorithm
// the class has a field that refers to the distance matrix passed during construction
// this algorithm is a progress of the basic 2-opt with the fact that two more variant of move are
// considered during improvement check
// i used this online source (http://tsp-basics.blogspot.com/2017/03/25-opt.html)
// for studying the variant connections. A, B, and C refers to the image into that link
public class Opt2h implements LocalSearch {

    private DistanceMatrix distanceMatrix; // matrix of distance used during optimization

    public Opt2h(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    // the optimize method receive a tour and optimizes it
    public void optimize(int[] tour) {
        int n = distanceMatrix.dim(); // number of cities
        int i,j,q; // for indices
        int improvement; // will contains improvement (or not...)
        int x1,x2,y1,y2; // indices of cities used during improvement check and moves
        int currentCost; // variable that will hold the cost of current arcs
        // tmp, tmpTour and index are used for moves
        int tmp;
        int[] tmpTour = new int[n+1];
        int index;
        boolean move;
        // move: initialized to false at the beginning of each stage,
        // becomes true when a 2-opt move is perform
        // let's start optimization
        do {
            move = false;
            for (i = 1; i < n - 1; i++) {
                for (j = i + 1; j < n; j++) {
                    // get indices of cities
                    x1 = i-1;
                    x2 = i;
                    y1 = j;
                    y2 = j+1;
                    // calculate cost of current arcs
                    currentCost =   distanceMatrix.db(tour[x1], tour[x2]) +
                                    distanceMatrix.db(tour[y1], tour[y2]);
                    // A VARIANT (equals to 2-opt) -----------------------------
                    improvement =   currentCost                             -
                                    distanceMatrix.db(tour[x1], tour[y1])   -
                                    distanceMatrix.db(tour[x2], tour[y2]);
                    if (improvement > 0) { // if with A variant there is an improvement, do it
                        for (;x2 < y1; x2++, y1--) {
                            tmp = tour[x2];
                            tour[x2] = tour[y1];
                            tour[y1] = tmp;
                        }
                        move = true;
                        continue; // i do a continue! (*)
                    }
                    // B VARIANT -----------------------------------------------
                    if (tour[x2+1] != tour[y1]) {
                        improvement  =  currentCost                             +
                                        distanceMatrix.db(tour[x2], tour[x2+1]) -
                                        distanceMatrix.db(tour[x2+1], tour[x1]) -
                                        distanceMatrix.db(tour[x2], tour[y1])   -
                                        distanceMatrix.db(tour[y2], tour[x2]);
                        if (improvement > 0) { // if with B variant there is an improvement, do it
                            System.arraycopy(tour, 0, tmpTour, 0, n+1);
                            index = 0;
                            tour[index++] = tmpTour[x2];
                            for (q = y1; q>=x2+1; q--, index++) tour[index] = tmpTour[q];
                            for (q=x1; q>=0; q--, index++) tour[index] = tmpTour[q];
                            for (q=n-1; q>=y2; q--, index++) tour[index] = tmpTour[q];
                            tour[index] = tmpTour[x2];
                            move = true;
                            continue; // i do a continue! (*)
                        }
                    }
                    // C VARIANT -----------------------------------------------
                    if (tour[y1-1] != tour[x2]) {
                        improvement  =    currentCost                             +
                                distanceMatrix.db(tour[y1-1], tour[y1]) -
                                distanceMatrix.db(tour[x1], tour[y1])   -
                                distanceMatrix.db(tour[y1], tour[x2])   -
                                distanceMatrix.db(tour[y1-1], tour[y2]);
                        if (improvement > 0) { // if with C variant there is an improvement, do it
                            System.arraycopy(tour, 0, tmpTour, 0, n+1);
                            index = 0;
                            System.arraycopy(tmpTour, 0, tour, index, x1+1);
                            index+=x1+1;
                            tour[index++] = tmpTour[y1];
                            System.arraycopy(tmpTour, x2, tour, index, y1-x2);
                            index+=y1-x2;
                            System.arraycopy(tmpTour, y2, tour, index, n-y2);
                            move = true;
                        }
                    }
                }
            }
        } while (move);
    }
}
// (*) I put the three variant in order. A takes priority over B and B over C.
// I do that instead of check every variant and then perform the best at each step
// before of performance. With this approach I can perform more iteration and
// btw proved to be efficient.
