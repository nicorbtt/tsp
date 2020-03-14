package ch.supsi.rubattu.model;

import ch.supsi.rubattu.distance.Distance;
import ch.supsi.rubattu.distance.EuclideanDistance;

import java.util.Arrays;

public class DistanceMatrix {

    private int[][] data;

    public void loadData(City[] cities) {
        data = new int[cities.length][cities.length];
        Distance distance = new EuclideanDistance();

        for (int q=0; q<cities.length; q++) {
            for (int w=0; w<cities.length; w++) {
                data[q][w] = distance.calculate(cities[q], cities[w]);
            }
        }
    }

    public int dim() {
        return data.length;
    }

    public int[] neighbours(int city) {
        return data[city];
    }

    public int db(int city1, int city2) {
        return data[city1][city2];
    }

    public void print2D() {
        for (int[] row : data) System.out.println(Arrays.toString(row));
    }
}
