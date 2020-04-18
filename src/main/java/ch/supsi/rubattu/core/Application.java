package ch.supsi.rubattu.core;

import ch.supsi.rubattu.constructive.NearestNeighbor;
import ch.supsi.rubattu.distance.EuclideanDistance;
import ch.supsi.rubattu.local_search.LocalSearch;
import ch.supsi.rubattu.local_search.Opt2h;
import ch.supsi.rubattu.metaheuristic.HybridSA;
import ch.supsi.rubattu.metaheuristic.Metaheuristic;
import ch.supsi.rubattu.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Application {

    // fields described during the run method code
    private String[] args;
    private Stopwatch stopwatch;
    private boolean verbose = false;
    private boolean output = false;
    private int startingNode = 0;
    private long seed = 0;
    private String tspProblem = "";

    public Application(String[] args) {
        // arguments received for tsp problem contains also execution parameters
        this.args = args.clone();
        // stopwatch instance used for hold the time during the run
        this.stopwatch = new Stopwatch();
    }

    // core tsp run
    public double run() {
        // start the chrono
        stopwatch.start();
        // process arguments
        if (!processArguments(args)) {
            // if something went wrong parsing parameters notify
            System.out.println("Error parsing command line arguments");
            return 100d; // (100% is the ratio result represent no work as be done)
        }
        // TSPFile instance that will manage input (tsp problem) and final output (tour)
        // from/to file
        TSPFile file = new TSPFile();
        // array that will holds parsed cities (with id, x and y) from file
        City[] cities;
        cities = file.parse(tspProblem); // get that cities
        // print some shit if requested (used for debug times ago)
        if (verbose) file.printProperties();
        if (verbose) file.printCities();
        // DistanceMatrix that will holds distances for all city-to-city pair
        // and viceversa with a matrix structure insed of it
        DistanceMatrix matrix = new DistanceMatrix();
        matrix.loadData(cities, new EuclideanDistance()); // load that distances N.B. euclidean distance used
        // print some shit if requested (used for debug times ago)
        if (verbose) matrix.print2D();
        // let's instantiate a Random object with our seed as constructor parameter
        // to be able to replicate the best achieved result
        Random random = new Random(seed);
        if (verbose) System.out.println("Using seed: " + seed);
        // take the best (optimum tour cost) in memory
        int best = Integer.parseInt(file.getProperties(TSPFile.Header.BEST_KNOWN));
        if (verbose) System.out.println("Algotithm running for 3 minute...");
        // first tour using NearestNeighbor algorithm (constructive)
        int[] tour = new NearestNeighbor(startingNode - 1).build(matrix);
        // let's instantiate our local search algorithm
        // (a 2h-opt one) used for first local search run and afterwards into the
        // metaheuristic algorithm
        LocalSearch localSearch = new Opt2h(matrix);
        // second tour optimized by the first local search run
        // this tour represent the initial solution as a parameter
        // for metaheuristic algorithm
        localSearch.optimize(tour);
        // let's instantiate our local search algorithm
        // params:
        // . distance matrix
        // . optimum (to stop in case)
        // . time allowed (millis)
        //      N.B  180_000 millisecond == 3 minute
        //      +300 millis is the mean time used for input and setup
        // . random instance (with our seed)
        // . stopwatch
        // . local search algorithm to be used inside
        Metaheuristic hybridSA = new HybridSA(matrix, best, 180_300, random, stopwatch, localSearch);
        // this execution info object will store some useful information about
        // the result of metaheuristic execution: number of iterations done, number of iteration in
        // which the best result will be found, final cost of the solution
        // N.B. iteration values are used for alignment when the run is called from different system.
        //      Our search is time limited (3 min) and the hardware is relevant for reach a
        //      best result within them. Know at which iterations the best solution is found could be useful.
        ExecutionInfo executionInfo = new ExecutionInfo();
        // run the metaheuristic process on our tour and wait with folded arms :)
        hybridSA.optimize(tour, executionInfo);
        // calculate the error
        double error100 = (((double) executionInfo.getCost() - best) / best) * 100;
        // Validator check used for debug not called in release
        //if (!Utility.validate(tour3, matrix)) System.out.println("route not valid");
        // write the result to .opt.tour if requested
        if (output) file.output(tour, executionInfo.getCost(), seed, error100);
        if (output && verbose) System.out.println(tspProblem + ".opt.tour written");
        // print the result resume
        System.out.format("[%7s]\tStart: %4d\tSeed: %13d\tError: %.3f%%\tBest at: %5d\tTime: %6d\n",
                tspProblem,
                startingNode,
                seed,
                error100,
                executionInfo.getBestIterationAt(),
                stopwatch.end());
        // return error for collecting all them when run all tsp problem together (mvn test)
        // and calculate a mean result of them
        return error100;
    }

    // function that parse arguments and returns true if alright else false
    // params are:
    // “[-o] [-v] [-start<starting city>] [-seed<seed>] <tsp problem name>”
    // . [-o] for write tour result to file .opt.tour
    // . [-v] for verbose
    // . [-start<starting city>] for the starting city to which generate the tour DEFAULT=1
    // . [-seed<seed>] for seed DEFAULT=0
    // es: "-o -start7 -seed1586013194541 eil76" will run tsp problem for eil76 with
    // starting city=7, seed=... and the result will be written to file
    // required: <tsp problem name> ... ch130 d198 eil76 fl1577 kroA100 lin318 pcb442, pr439 rat783 u1060
    private boolean processArguments(String[] args) {
        args = args[0].split(" ");
        switch (args.length) {
            case 1:
                tspProblem = args[0];
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                tspProblem = args[args.length-1];
                List<String> options = Arrays.asList(args);
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
        String[] tspProblemValidArray = {"ch130", "d198", "eil76", "fl1577", "kroA100", "lin318", "pcb442",
                "pr439", "rat783", "u1060"};
        List<String> tspProblemValid = Arrays.asList(tspProblemValidArray);
        if (tspProblemValid.contains(tspProblem)) return true;
        else {
            System.out.println("TSP problem '" + tspProblem + "' not present, sorry...");
            System.out.print("...that's what's available:  ");
            System.out.println("ch130  d198  eil76  fl1577  kroA100  lin318  pcb442  pr439  rat783  u1060");
            return false;
        }
    }
}
