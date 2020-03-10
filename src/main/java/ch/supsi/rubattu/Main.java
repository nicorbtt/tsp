package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.Constructive;
import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.Result;
import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.model.*;
import ch.supsi.rubattu.persistence.TSPFile;

import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.getInstance();

        boolean verbose = false;
        boolean output = false;
        int startingNode = 0;
        long seed = 0;
        String fileName;

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
                    }
                    if (option.matches("-seed[0-9]+")) {
                        String numberOnly = option.replaceAll("[^0-9]", "");
                        seed = Integer.parseInt(numberOnly);
                    }
                }
                break;
            default: return;
        }

        TSPFile file = new TSPFile();
        City[] cities = file.parse(fileName);
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities);
        if (verbose) matrix.print2D();

        Random rd = new Random();

        System.out.println();
        for (long q=0; q<10; ++q) {
            long s = rd.nextLong();
            stopwatch.start();
            System.out.print("Seed: " + s + " -> ");
            Constructive algorithm = new NearestNeighbor(startingNode);
            Result response1 = algorithm.compute(cities, matrix);


            int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

            Result response2 = Opt2.optimize(response1, matrix);
            Result response3 = HybridSA.optimize(response2, matrix, best, 180_000, s);

            double ratio = (((double)response3.cost()-best)/best)*100;
            System.out.format("Ratio: %.2f", ratio);

            stopwatch.end();
            System.out.println();
        }
        /*
        System.out.println("Seed: " + minI);

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        double ratio = (((double)minResult.cost()-best)/best)*100;
        System.out.format("Ratio: %.2f", ratio);
        */

        /*
        Constructive algorithm = new NearestNeighbor(startingNode);
        Result response = algorithm.compute(cities, matrix);

        if (verbose) System.out.println();
        if (verbose) response.printResult();

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

        Result r = Opt2.optimize(response, matrix);
        Result response2 = HybridSA.optimize(r, matrix,
                best, 180000);
        //Result response2 = Opt2.optimize(response, matrix);
        if (verbose) System.out.println();
        response2.printResult();

        long millis = stopwatch.end();
        System.out.println("• Millis:\t" + millis);

        double ratio = (((double)response2.cost()-best)/best)*100;
        System.out.format("• Ratio:\t%.2f\n", ratio);

        boolean valid = Validator.validate(response2, matrix);
        System.out.println(valid ? "Tour is valid" : "!! Tour is not valid !!");
        System.out.println("=====================");
        */
        //if (output) file.output(response2.tour(), response2.cost());

    }

}

