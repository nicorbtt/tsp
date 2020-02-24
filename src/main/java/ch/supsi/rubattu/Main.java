package ch.supsi.rubattu;

import ch.supsi.rubattu.model.*;


public class Main {

    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();

        TSPFile file = new TSPFile();
        City[] cities = file.parse("src/main/resources/files/eil76.tsp");
        //file.printProperties();
        //file.printCities();

        Data matrix = new Data();
        matrix.loadData(cities);
        //matrix.print2D();

        Algorithm algorithm = new NearestNeighbor();
        AlgorithmResponse response = algorithm.compute(matrix);

        //System.out.println();
        //response.printResult();

        stopwatch.end();

    }

}

