package ch.supsi.rubattu;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.local_search.Opt2;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Stopwatch;
import ch.supsi.rubattu.model.Utility;
import ch.supsi.rubattu.persistence.TSPFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Testt {

    private String[] args;

    public Testt(String[] args) {
        this.args = args.clone();
    }

    public void run() {

        boolean verbose = false;
        boolean output = false;
        int startingNode = 0;
        long seed = 1;
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
        City[] cities = file.parse(fileName);

        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities);

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));

        double min = Double.MAX_VALUE;
        int ind = 0;
        System.out.println(startingNode);

        //for (int i=0; i<cities.length; i++) {
            int[] response1 = new NearestNeighbor(startingNode - 1).compute(matrix);
            int[] response2 = new Opt2(matrix).optimize(response1);
            int finalCost = Utility.costOf(response2, matrix);
            double ratio = (((double) finalCost - best) / best) * 100;
            System.out.println(ratio);
        //}

    }
}
