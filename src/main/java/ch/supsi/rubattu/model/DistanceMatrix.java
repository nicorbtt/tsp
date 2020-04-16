package ch.supsi.rubattu.model;

import ch.supsi.rubattu.distance.Distance;

import java.util.Arrays;

// class that hold the distances between cities in a matrix structure form
public class DistanceMatrix {

    // N.B. distance are integer values instead coordinate from input where doubles
    // --> euclidean reason
    private int[][] data;

    // method that receive an array of City and a distance matrix funcion and perform
    // the matrix distances calculation
    // N.B. distance from A to B is equal to distance from B to A and both are stored into the matrix
    public void loadData(City[] cities, Distance distance) {
        data = new int[cities.length][cities.length];
        for (int q=0; q<cities.length; q++) {
            for (int w=0; w<cities.length; w++) {
                data[q][w] = distance.calculate(cities[q], cities[w]);
            }
        }
    }

    // method that return the dimension of matrix / number of cities into
    public int dim() {
        return data.length;
    }

    // method that return an array reference of city/distance of a specific city (by id)
    public int[] neighbours(int city) {
        return data[city];
    }

    // method that return the distance between two cities
    public int db(int city1, int city2) {
        return data[city1][city2];
    }

    // method that will write the whole matrix (used with verbose option)
    public void print2D() {
        for (int[] row : data) System.out.println(Arrays.toString(row));
    }
}
