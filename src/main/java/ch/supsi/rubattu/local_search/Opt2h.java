package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.DistanceMatrix;

public class Opt2h implements LocalSearch {

    private DistanceMatrix distanceMatrix;

    public Opt2h(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int[] optimize(int[] result) {
        int dim = result.length;
        int[] tour = new int[dim];
        System.arraycopy(result, 0, tour, 0, dim);
        int I, J;
        int a,b,c, best;
        String iType;
        int x1,x2,y1,y2;
        int i,j,q;
        int currentCost;
        int tmp;
        int[] tourTmp = new int[dim];
        int index;

        do {
            I = J = 0;
            best = 0;
            iType = "-";
            for (i = 1; i < dim - 2; i++) {
                for (j = i + 1; j < dim - 1; j++) {

                    x1 = i-1;
                    x2 = i;
                    y1 = j;
                    y2 = j+1;
                    currentCost =   distanceMatrix.db(tour[x1], tour[x2]) +
                                    distanceMatrix.db(tour[y1], tour[y2]);

                    a =     currentCost                             -
                            distanceMatrix.db(tour[x1], tour[y1])   -
                            distanceMatrix.db(tour[x2], tour[y2]);
                    if (a > best) {
                        iType = "A";
                        best = a;
                        I = i;
                        J = j;
                        continue;
                    }
                    if (tour[x2+1] != tour[y1]) {
                        b  =    currentCost                             +
                                distanceMatrix.db(tour[x2], tour[x2+1]) -
                                distanceMatrix.db(tour[x2+1], tour[x1]) -
                                distanceMatrix.db(tour[x2], tour[y1])   -
                                distanceMatrix.db(tour[y2], tour[x2]);
                        if (b > best) {
                            iType = "B";
                            best = b;
                            I = i;
                            J = j;
                            continue;
                        }
                    }
                    if (tour[y1-1] != tour[x2]) {
                        c  =    currentCost                             +
                                distanceMatrix.db(tour[y1-1], tour[y1]) -
                                distanceMatrix.db(tour[x1], tour[y1])   -
                                distanceMatrix.db(tour[y1], tour[x2])   -
                                distanceMatrix.db(tour[y1-1], tour[y2]);
                        if (c > best) {
                            iType = "C";
                            best = c;
                            I = i;
                            J = j;
                        }
                    }
                }
            }
            if (best > 0) {
                if (iType.equals("A")) {
                    for (;I < J; I++, J--) {
                        tmp = tour[I];
                        tour[I] = tour[J];
                        tour[J] = tmp;
                    }
                    continue;
                }
                System.arraycopy(tour, 0, tourTmp, 0, dim);
                x1 = I-1;
                x2 = I;
                y1 = J;
                y2 = J+1;
                index = 0;
                if (iType.equals("B")) {
                    tour[index++] = tourTmp[x2];
                    for (q = y1; q>=x2+1; q--, index++) tour[index] = tourTmp[q];
                    for (q=x1; q>=0; q--, index++) tour[index] = tourTmp[q];
                    for (q=dim-2; q>=y2; q--, index++) tour[index] = tourTmp[q];
                    tour[index] = tourTmp[x2];
                    continue;
                }
                for (q=0; q<=x1; q++, index++) tour[index] = tourTmp[q];
                tour[index++] = tourTmp[y1];
                for (q=x2; q<=y1-1; q++, index++) tour[index] = tourTmp[q];
                for (q=y2; q<dim; q++, index++) tour[index] = tourTmp[q];
            }
        } while (best > 0);
        return tour;
    }
}
