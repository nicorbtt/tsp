package ch.supsi.rubattu.model;

import ch.supsi.rubattu.distance.Distance;
import ch.supsi.rubattu.distance.EuclideanDistance;

import java.util.Arrays;

public class DistanceMatrix {

    private double[][] data;

    public void loadData(City[] cities) {
        data = new double[cities.length][cities.length];
        Distance distance = new EuclideanDistance();

        for (int q=0; q<cities.length; q++) {
            for (int w=0; w<cities.length; w++) {
                data[q][w] = distance.calculate(cities[q], cities[w]);
            }
        }
    }

    public void print2D() {
        for (double[] row : data) System.out.println(Arrays.toString(row));
    }

    public double[][] data() {
        return data;
    }
}
