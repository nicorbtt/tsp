package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.*;
import ch.supsi.rubattu.persistence.TSPFile;

import java.util.List;
import java.util.Random;

class Application {

    private String[] args;
    private Stopwatch stopwatch;

    private boolean verbose = false;
    private boolean output = false;
    private int startingNode = 0;
    private long seed = 0;
    private String fileName;

    Application(String[] args) {
        this.args = args.clone();
        this.stopwatch = new Stopwatch();
    }

    void run() {

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        if (!processArguments(args)) return;

        TSPFile file = new TSPFile();
        City[] cities = file.parse(fileName);
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities);
        if (verbose) matrix.print2D();

        Random random = new Random(seed);

        NearestNeighbor algorithm = new NearestNeighbor(startingNode - 1);
        int[] tour1 = algorithm.compute(matrix);

        Opt2 opt2 = new Opt2(matrix);
        int[] tour2 = opt2.optimize(tour1);
        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

        HybridSA hybridSA = new HybridSA(tour2, matrix, best, 180_000, random, stopwatch);
        int[] tour3 = hybridSA.optimize();

        double ratio = (((double) Utility.costOf(tour3, matrix)-best)/best)*100;

        if (!new Validator().validate(tour3, matrix)) System.out.println("route not valid");;

        System.out.format("[%s]\t\tSeed: %d\t\tRatio: %.2f\t\tTime: %d\n", fileName, seed, ratio, stopwatch.end());
    }

    private boolean processArguments(String[] args) {
        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
            case 3:
            case 4:
                fileName = args[args.length-1];
                List<String> options = List.of(args[0].split(" "));
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
