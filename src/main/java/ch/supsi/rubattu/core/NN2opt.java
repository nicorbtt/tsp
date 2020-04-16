package ch.supsi.rubattu.core;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Stopwatch;
import ch.supsi.rubattu.model.Utility;
import ch.supsi.rubattu.model.TSPFile;

import java.util.List;

public class NN2opt {

    private String[] args;

    public NN2opt(String[] args) {
        this.args = args.clone();
    }

    public void run() {

        Stopwatch stopwatch = new Stopwatch();

        boolean verbose = false;
        boolean output = false;
        int startingNode = 0;
        long seed = 1;
        String tspProblem = "";

        args = args[0].split(" ");
        switch (args.length) {
            case 1:
                tspProblem = args[0];
                break;
            case 2:
            case 3:
            case 4:
                tspProblem = args[args.length-1];
                List<String> options = List.of(args);
                if (options.contains("-v")) verbose = true;
                if (options.contains("-o")) output = true;
                for (String option : options) {
                    if (option.matches("-start[0-9]+")) {
                        String numberOnly = option.replaceAll("[^0-9]", "");
                        startingNode = Integer.parseInt(numberOnly);
                        continue;
                    }
                    if (option.matches("-seed[-+]?\\d+")) {
                        String numberOnly = option.replaceAll("-seed", "");
                        seed = Long.parseLong(numberOnly);
                    }
                }
                break;
            default:
        }

        TSPFile file = new TSPFile();
        City[] cities = file.parse(tspProblem);

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance());

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

        double min = Double.MAX_VALUE;
        int ind = 0;

        stopwatch.start();

        for (int i=0; i<cities.length; i++) {
            System.out.println(i);
            System.out.println("NN...");
            int[] response1 = new NearestNeighbor(i).build(matrix);
            //int[] response1 = new RandomNeighbor(new Random(0), matrix.dim()).build();
            System.out.println("Opt2...");
            //int[] response2 = new Opt2(matrix).optimize(response1);
            int[] response2 = new Opt2h(matrix).optimize(response1);
            int finalCost = Utility.costOf(response2, matrix);
            double ratio = (((double) finalCost - best) / best) * 100;

            if (ratio < min) {
                min = ratio;
                ind = i;
            }

            if (ratio == 0) break;
        }

        //System.out.println(tspProblem + " " + ratio);
        System.out.println("Min: " + min + " start= " + ind);
        //System.out.println(stopwatch.end());

    }
}
