package ch.supsi.rubattu.core;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.TSPFile;
import ch.supsi.rubattu.model.Utility;

public class NN2opt {

    private String[] args;

    public NN2opt(String[] args) {
        this.args = args.clone();
    }

    public void run() {
        String tspProblem = "";
        args = args[0].split(" ");
        if (args.length == 1) {
            tspProblem = args[0];
        } else {
            System.out.println("error");
            return;
        }
        TSPFile file = new TSPFile();
        City[] cities = file.parse(tspProblem);
        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance());
        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        double min = Double.MAX_VALUE;
        int ind = 0;
        LocalSearch localSearch = new Opt2h(matrix);
        double ratio;
        for (int i=0; i<cities.length; i++) {
            System.out.println(i);
            int[] tour = new NearestNeighbor(i).build(matrix);
            localSearch.optimize(tour);
            int finalCost = Utility.costOf(tour, matrix);
            ratio = (((double) finalCost - best) / best) * 100;
            if (ratio < min) { min = ratio; ind = i; }
            if (ratio == 0) break;
        }
        System.out.println("Min: " + min + " start= " + ind);
    }
}