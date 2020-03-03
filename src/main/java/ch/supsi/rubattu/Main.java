package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.Constructive;
import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.model.Result;
import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.model.*;
import ch.supsi.rubattu.persistence.TSPFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        boolean verbose = false;
        boolean output = false;
        int startingNode = 0;
        String fileName;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                fileName = args[1];
                List<String> options = List.of(args[0].split(" "));
                if (options.contains("-v")) verbose = true;
                if (options.contains("-o")) output = true;
                for (String option : options) {
                    if (option.matches("-start[0-9]+")) {
                        String numberOnly = option.replaceAll("[^0-9]", "");
                        startingNode = Integer.parseInt(numberOnly);
                    }
                }
                break;
            default: return;
        }

        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();

        TSPFile file = new TSPFile();
        City[] cities = file.parse(fileName);
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities);
        if (verbose) matrix.print2D();

        /*
        Result[] results = new Result[matrix.data().length];

        for (int q=0; q<matrix.data().length; ++q) {
            Constructive algorithm = new NearestNeighbor(q);
            Result response = algorithm.compute(cities, matrix);

            if (verbose) System.out.println();
            if (verbose) response.printResult();

            Result response2 = Opt2.optimize(response, matrix);
            if (verbose) System.out.println();
            if (verbose) response2.printResult();

            long millis = stopwatch.end();
            if (verbose) System.out.println("millis: " + millis);

            int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
            double ratio = (((double)response2.cost()-best)/best)*100;
            if (verbose) System.out.format("Ratio: %.2f", ratio);

            results[q] = response2;
            System.out.println(q);

            //if (output) file.output(response2.tour(), response2.cost());
        }
        int minI = 0;
        Result minResult = results[0];
        for (int i=1;i<results.length; ++i) {
            if (results[i].cost() < minResult.cost()) {
                minResult = results[i];
                minI = i;
            }
        }
        System.out.println("Seed: " + minI);

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        double ratio = (((double)minResult.cost()-best)/best)*100;
        System.out.format("Ratio: %.2f", ratio);
        */



        Constructive algorithm = new NearestNeighbor(startingNode);
        Result response = algorithm.compute(cities, matrix);

        if (verbose) System.out.println();
        if (verbose) response.printResult();

        Result response2 = Opt2.optimize(response, matrix);
        if (verbose) System.out.println();
        response2.printResult();

        long millis = stopwatch.end();
        System.out.println("• Millis:\t" + millis);

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        double ratio = (((double)response2.cost()-best)/best)*100;
        System.out.format("• Ratio:\t%.2f\n", ratio);

        boolean valid = Validator.validate(response2, matrix);
        System.out.println(valid ? "Tour is valid" : "!! Tour is not valid !!");
        System.out.println("=====================");

        List<Integer> integers = new ArrayList<>();
        response2.tour().forEach(city->integers.add(city.id()));
        Collections.sort(integers);
        integers.forEach(System.out::println);


        if (output) file.output(response2.tour(), response2.cost()); //Occhio modifica tour eliminando ultima city
    }

}

