package ch.supsi.rubattu.model;

import ch.supsi.rubattu.distance.Distance;
import ch.supsi.rubattu.distance.EuclideanDistance;

import java.util.Arrays;

public class DistanceMatrix {

    private Integer[][] data;

    public void loadData(City[] cities) {
        data = new Integer[cities.length][cities.length];
        Distance distance = new EuclideanDistance();

        for (int q=0; q<cities.length; q++) {
            for (int w=0; w<cities.length; w++) {
                data[q][w] = distance.calculate(cities[q], cities[w]);
            }
        }
    }

    public void print2D() {
        for (Integer[] row : data) System.out.println(Arrays.toString(row));
    }

    public Integer[][] data() {
        return data;
    }
}
