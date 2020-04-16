package ch.supsi.rubattu.constructive;

import ch.supsi.rubattu.model.DistanceMatrix;

import java.util.Arrays;
import java.util.Random;

// class that perform a build solution according to the
// simple Random Neighbor algorithm (not used by Application)
public class RandomNeighbor implements Constructive {

    private Random random;
    private int numberOfCities;

    public RandomNeighbor(Random random, int numberOfCities) {
        this.random = random;
        this.numberOfCities = numberOfCities;
    }

    public int[] build(DistanceMatrix distanceMatrix) {
        boolean[] visited = new boolean[numberOfCities];
        Arrays.fill(visited, false);
        int current = random.nextInt(numberOfCities);
        int[] tour = new int[numberOfCities+1];
        tour[0] = current;
        for (int q=1; q<numberOfCities; q++) {
            visited[current] = true;
            current = nextRandom(visited);
            tour[q] = current;
        }
        tour[numberOfCities] = tour[0];
        return tour;
    }

    private int nextRandom(boolean[] visited) {
        int next = random.nextInt(numberOfCities);
        while(visited[next])
            if (++next == numberOfCities) next = 0;
        return next;
    }
}
