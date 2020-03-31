package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.constructive.RandomNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.*;
import ch.supsi.rubattu.persistence.TSPFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;

class Application {

    private String[] args;
    private Stopwatch stopwatch;

    private boolean verbose = false;
    private boolean output = false;
    private int startingNode = 0;
    private long seed = 0;
    private String tspProblem;

    Application(String[] args) {
        this.args = args.clone();
        this.stopwatch = new Stopwatch();
    }

    void run() {

        stopwatch.start();

        if (!processArguments(args)) {
            System.out.println("Error parsing command line arguments");
            return;
        }

        TSPFile file = new TSPFile();
        City[] cities;
        try {
            cities = file.parse(tspProblem);
        } catch (IOException e) {
            System.out.println("TSP problem '" + tspProblem + "' not present, sorry...");
            System.out.print("...that's what's available:  ");
            System.out.println("ch130  d198  eil76  fl1577  kroA100  lin318  pcb442  pr439  rat783  u1060");
            return;
        }
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance());
        if (verbose) matrix.print2D();

        Random random = new Random(seed);

        //NearestNeighbor algorithm = new NearestNeighbor(startingNode - 1);
        NearestNeighbor algorithm = new NearestNeighbor(random.nextInt(matrix.dim()));
        int[] tour1 = algorithm.compute(matrix);

        Opt2 opt2 = new Opt2(matrix);
        int[] tour2 = opt2.optimize(tour1);
        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        HybridSA hybridSA = new HybridSA(tour2, matrix, best, 170_000, random, stopwatch, opt2);
        int[] tour3 = hybridSA.optimize();
        int finalCost = Utility.costOf(tour3, matrix);

        double ratio = (((double) finalCost-best)/best)*100;

        if (!new Validator().validate(tour3, matrix)) System.out.println("route not valid");

        if (output) file.output(tour3, finalCost, seed, ratio);

        System.out.format("[%s]\t\tSeed: %d\t\tRatio: %.2f\t\tTime: %d\n", tspProblem, seed, ratio, stopwatch.end());
    }

    private boolean processArguments(String[] args) {
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
            default: return false;
        }
        return true;
    }
}
