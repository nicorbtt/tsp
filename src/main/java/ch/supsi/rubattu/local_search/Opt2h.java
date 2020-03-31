package ch.supsi.rubattu.local_search;

import ch.supsi.rubattu.model.DistanceMatrix;

public class Opt2h implements LocalSearch {

    private class Improvement {
        String name;
        int value;
        private Improvement(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    private DistanceMatrix distanceMatrix;

    public Opt2h(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int[] optimize(int[] result) {
        //long start = System.currentTimeMillis();

        int dim = result.length;
        int[] tour = new int[dim];
        System.arraycopy(result, 0, tour, 0, dim);
        int I, J;
        Improvement a    = new Improvement("A", 0);
        Improvement b    = new Improvement("B", 0);
        Improvement c    = new Improvement("C", 0);
        Improvement best = new Improvement("-", 0);
        boolean performA = true;
        boolean performB = true;
        boolean performC = true;

        do {
            I = J = 0;
            best.name = "-";
            best.value = 0;
            for (int i = 1; i < dim - 2; i++) {
                for (int j = i + 1; j < dim - 1; j++) {

                    a.name = "A";
                    b.name = "B";
                    c.name = "C";
                    a.value = b.value = c.value = 0;

                    int x1 = i-1;
                    int x2 = i;
                    int y1 = j;
                    int y2 = j+1;

                    int currentCost =   distanceMatrix.db(tour[x1], tour[x2]) +
                                        distanceMatrix.db(tour[y1], tour[y2]);

                    if (performA) {
                        a.value  =  currentCost                             -
                                    distanceMatrix.db(tour[x1], tour[y1])   -
                                    distanceMatrix.db(tour[x2], tour[y2]);
                    }
                    if (performB && tour[x2+1] != tour[y1]) {
                        b.value  =  currentCost                             +
                                    distanceMatrix.db(tour[x2], tour[x2+1]) -
                                    distanceMatrix.db(tour[x2+1], tour[x1]) -
                                    distanceMatrix.db(tour[x2], tour[y1])   -
                                    distanceMatrix.db(tour[y2], tour[x2]);
                    }
                    if (performC && tour[y1-1] != tour[x2]) {
                        c.value  =  currentCost                             +
                                    distanceMatrix.db(tour[y1-1], tour[y1]) -
                                    distanceMatrix.db(tour[x1], tour[y1])   -
                                    distanceMatrix.db(tour[y1], tour[x2])   -
                                    distanceMatrix.db(tour[y1-1], tour[y2]);
                    }

                    Improvement tmp;
                    if (a.value > b.value) {
                        tmp = a;
                        a = b;
                        b = tmp;
                    }
                    if (b.value > c.value) {
                        tmp = b;
                        b = c;
                        c = tmp;
                    }
                    if (a.value > b.value) {
                        tmp = a;
                        a = b;
                        b = tmp;
                    }

                    if (c.value > best.value) {
                        best.name = c.name;
                        best.value = c.value;
                        I = i;
                        J = j;
                    }
                }
            }
            if (best.value > 0) {
                if (best.name.equals("A") && performA) {
                    for (;I < J; I++, J--) {
                        int tmp = tour[I];
                        tour[I] = tour[J];
                        tour[J] = tmp;
                    }
                    continue;
                }
                int[] tmp = new int[dim];
                System.arraycopy(tour, 0, tmp, 0, dim);
                int x1 = I-1;
                int x2 = I;
                int y1 = J;
                int y2 = J+1;
                int index = 0;
                if (best.name.equals("B") && performB) {
                    tour[index++] = tmp[x2];
                    for (int q = y1; q>=x2+1; q--, index++) tour[index] = tmp[q];
                    for (int q=x1; q>=0; q--, index++) tour[index] = tmp[q];
                    for (int q=dim-2; q>=y2; q--, index++) tour[index] = tmp[q];
                    tour[index] = tmp[x2];
                    continue;
                }
                if (best.name.equals("C") && performC) {
                    for (int q=0; q<=x1; q++, index++) tour[index] = tmp[q];
                    tour[index++] = tmp[y1];
                    for (int q=x2; q<=y1-1; q++, index++) tour[index] = tmp[q];
                    for (int q=y2; q<dim; q++, index++) tour[index] = tmp[q];
                }
            }
        } while (best.value > 0);
        //long end = System.currentTimeMillis();
        //System.out.println(end-start);
        return tour;
    }
}
