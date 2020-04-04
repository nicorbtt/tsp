package ch.supsi.rubattu.constructive;

import java.util.Arrays;
import java.util.Random;

public class RandomNeighbor {

    private Random random;
    private int numberOfCities;

    public RandomNeighbor(Random random, int numberOfCities) {
        this.random = random;
        this.numberOfCities = numberOfCities;
    }

    public int[] compute() {
        boolean[] visited = new boolean[numberOfCities];
        Arrays.fill(visited, false);

        int current = random.nextInt(numberOfCities);

        int[] tour = new int[numberOfCities+1];
        tour[0] = current;

        for (int q=1; q<numberOfCities; q++) {
            visited[current] = true;
            current = nextRandomCity(visited);
            tour[q] = current;
        }

        tour[numberOfCities] = tour[0];
        return tour;
    }

    private int nextRandomCity(boolean[] visited) {
        int next = random.nextInt(numberOfCities);
        while(visited[next]) {
            if (++next == numberOfCities) next = 0;
        }
        return next;
    }
}
