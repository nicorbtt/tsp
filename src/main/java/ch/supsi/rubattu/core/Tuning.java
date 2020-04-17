package ch.supsi.rubattu.core;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tuning {

    private String[] args;

    public Tuning(String[] args) {
        this.args = args.clone();
    }

    public void run() {
        int startingNode = 0;
        String tspProblem;
        args = args[0].split(" ");
        switch (args.length) {
            case 1:
                tspProblem = args[0];
                break;
            case 2:
                tspProblem = args[args.length-1];
                List<String> options = List.of(args);
                for (String option : options) {
                    if (option.matches("-start[0-9]+")) {
                        String numberOnly = option.replaceAll("[^0-9]", "");
                        startingNode = Integer.parseInt(numberOnly);
                        break;
                    }
                }
                break;
            default: return;
        }
        TSPFile file = new TSPFile();
        System.out.println(tspProblem);
        City[] cities = file.parse(tspProblem);
        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance());
        List<String> lines = Collections.emptyList();
        try {
            String path = file.getProperties(TSPFile.Header.NAME) + ".opt.tour";
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException ignored) { }
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
            int[] tour = nearestNeighbor.build(matrix);
            opt2h.optimize(tour);
            ExecutionInfo executionInfo = new ExecutionInfo();
            new HybridSA(
                    matrix,
                    best,
                    170_000,
                    random,
                    stopwatch,
                    opt2h).optimize(tour, executionInfo);
            double error100 = (((double) executionInfo.getCost() - best) / best) * 100;
            if (error100 < min) {
                System.out.println("Find a better one! :) " + error100 + " " + s);
                file.output(tour, executionInfo.getCost(), s, error100);
                min = error100;
            }
            if (error100 == 0) {
                System.out.println("Find optimum!!! YOO");
                break;
            }
            s = System.currentTimeMillis();
        }

    }
}
