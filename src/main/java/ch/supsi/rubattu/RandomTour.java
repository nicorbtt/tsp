package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.constructive.RandomNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.model.*;
import ch.supsi.rubattu.persistence.TSPFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RandomTour {

    private String[] args;

    public RandomTour(String[] args) {
        this.args = args.clone();
    }

    public void run() throws IOException {

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

        stopwatch.start();

        int[] tour = new RandomNeighbor(new Random(0), matrix.dim()).compute();
        int[] tour2 = new Opt2(matrix).optimize(tour);
        int myCost = Utility.costOf(tour2, matrix);
        double ratio = (((double) myCost - best) / best) * 100;

        System.out.println(ratio);
        System.out.println(stopwatch.end());
        System.out.println(new Validator().validate(tour2, matrix));

    }
}
