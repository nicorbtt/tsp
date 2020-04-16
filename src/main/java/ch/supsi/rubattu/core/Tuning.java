package ch.supsi.rubattu.core;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Stopwatch;
import ch.supsi.rubattu.model.Utility;
import ch.supsi.rubattu.model.TSPFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Tuning {

    private String[] args;

    public Tuning(String[] args) {
        this.args = args.clone();
    }

    public void run() {

        boolean verbose = false;
        boolean output = false;
        int startingNode = 0;
        long seed = 1;
        String fileName;

        args = args[0].split(" ");
        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
            case 3:
            case 4:
                fileName = args[args.length-1];
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
            default: return;
        }

        TSPFile file = new TSPFile();
        System.out.println(fileName);
        City[] cities = file.parse(fileName);
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance());
        if (verbose) matrix.print2D();

        Random seedGenerator = new Random();


        List<String> lines = Collections.emptyList();
        try {
            String path = file.getProperties(TSPFile.Header.NAME) + ".opt.tour";
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double min = Double.valueOf(lines.get(1).split(":")[1].trim());
        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

        System.out.println("Ratio to beat: " + min);
        System.out.println("Let's go! Tuning...");

        LocalSearch opt2h = new Opt2h(matrix);

        NearestNeighbor nearestNeighbor = new NearestNeighbor(startingNode-1);

        long s = 0;

        while (true) {
            Random random = new Random(s);
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();

            int[] response1 = nearestNeighbor.build(matrix);

            int[] response2 = opt2h.optimize(response1);
            AtomicInteger iterationOfBest = new AtomicInteger(0);
            int[] response3 = new HybridSA(
                        matrix,
                        best,
                        170_000,
                        random,
                        stopwatch,
                        opt2h).optimize(response2, iterationOfBest, new AtomicInteger());

            int finalCost = Utility.costOf(response3, matrix);
            double ratio = (((double) finalCost - best) / best) * 100;
            if (ratio < min) {
                System.out.println("Find a better one! :) " + ratio + " " + s);
                file.output(response3, finalCost, s, ratio);
                min = ratio;
            }
            if (ratio == 0) {
                System.out.println("Find optimum!!! YOO");
                break;
            }
            s = System.currentTimeMillis();
        }

    }
}