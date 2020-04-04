package ch.supsi.rubattu.constructive;

import ch.supsi.rubattu.model.DistanceMatrix;

import java.util.Arrays;

public class NearestNeighbor {

    private int start;

    public NearestNeighbor(int start) {
        this.start = start;
    }

    public int[] compute(DistanceMatrix distanceMatrix) {
        int numberOfCities = distanceMatrix.dim();
        int current = start, next;

        int[] tour = new int[numberOfCities+1];
        tour[0] = current;

        boolean[] visited = new boolean[numberOfCities];
        Arrays.fill(visited, false);

        for (int q=1; q<numberOfCities; q++) {
            visited[current] = true;
            next = closestNeighbour(distanceMatrix.neighbours(current), visited);
            tour[q] = next;
            current = next;
        }

        tour[numberOfCities] = tour[0];
        return tour;
    }

    private int closestNeighbour(int[] numbers, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int closestNeighbour = -1;
        for(int i = 0; i < numbers.length; i++) {
            if(numbers[i] < minDistance && numbers[i] != -1 && !visited[i]) {
                minDistance = numbers[i];
                closestNeighbour = i;
            }
        }
        return closestNeighbour;
    }
}
