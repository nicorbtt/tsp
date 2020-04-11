package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.*;
import ch.supsi.rubattu.persistence.TSPFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    double run() {

        stopwatch.start();

        if (!processArguments(args)) {
            System.out.println("Error parsing command line arguments");
            return 100d;
        }

        TSPFile file = new TSPFile();
        City[] cities;
        try {
            cities = file.parse(tspProblem);
        } catch (IOException e) {
            System.out.println("TSP problem '" + tspProblem + "' not present, sorry...");
            System.out.print("...that's what's available:  ");
            System.out.println("ch130  d198  eil76  fl1577  kroA100  lin318  pcb442  pr439  rat783  u1060");
            return 100d;
        }
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance());
        if (verbose) matrix.print2D();

        Random random = new Random(seed);
        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

        NearestNeighbor algorithm = new NearestNeighbor(startingNode - 1);
        int[] tour1 = algorithm.compute(matrix);

        LocalSearch localSearch = new Opt2h(matrix);
        int[] tour2 = localSearch.optimize(tour1);

        HybridSA hybridSA = new HybridSA(
                matrix,
                best,
                180_000,
                random,
                stopwatch,
                localSearch,
                tspProblem);
        int[] tour3 = new int[0];
        try {
            tour3 = hybridSA.optimize(tour2);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        int finalCost = Utility.costOf(tour3, matrix);

        double ratio = (((double) finalCost-best)/best)*100;

        if (!new Validator().validate(tour3, matrix)) System.out.println("route not valid");

        if (output) file.output(tour3, finalCost, seed, ratio);

        System.out.format("[%7s]\t\tSeed: %20d\t\tRatio: %.2f\t\tTime: %6d\n", tspProblem, seed, ratio,
                stopwatch.end());
        return ratio;
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
