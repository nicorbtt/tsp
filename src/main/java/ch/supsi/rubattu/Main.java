package ch.supsi.rubattu;

import ch.supsi.rubattu.model.*;


public class Main {

    public static void main(String[] args) {

        boolean verbose = false;
        boolean output = false;
        String fileName;
        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                if (args[0].equals("-v")) verbose = true;
                if (args[0].equals("-o")) output = true;
                fileName = args[1];
                break;
            case 3:
                if (args[0].equals("-v") || args[1].equals("-v")) verbose = true;
                if (args[0].equals("-o") || args[1].equals("-o")) output = true;
                fileName = args[2];
                break;
            default: return;
        }

        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();

        TSPFile file = new TSPFile();
        City[] cities = file.parse(fileName);
        if (verbose) file.printProperties();
        if (verbose) file.printCities();

        Data matrix = new Data();
        matrix.loadData(cities);
        if (verbose) matrix.print2D();

        Algorithm algorithm = new NearestNeighbor();
        Result response = algorithm.compute(cities, matrix);

        if (verbose) System.out.println();
        if (verbose) response.printResult();

        Result response2 = TwoOpt.optimize(response, matrix);
        if (verbose) System.out.println();
        if (verbose) response2.printResult();

        long millis = stopwatch.end();
        if (verbose) System.out.println("millis: " + millis);

        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        double ratio = (((double)response2.cost()-best)/best)*100;
        if (verbose) System.out.format("Ratio: %.2f", ratio);

        if (output) file.output(response2.tour(), response2.cost());
    }

}

