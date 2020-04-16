package ch.supsi.rubattu.constructive;

import ch.supsi.rubattu.model.DistanceMatrix;

import java.util.Arrays;

// class that perform a build solution starting from raw distance matrix
// according to the simple Nearest Neighbor algorithm
public class NearestNeighbor implements Constructive {

    // my NN accept a starting city DEFAULT = 0 (first one)
    private int start;

    public NearestNeighbor() {
        this.start = 0;
    }

    public NearestNeighbor(int start) {
        this.start = start;
    }

    public int[] build(DistanceMatrix distanceMatrix) {
        // get in stack the number of cities
        int numberOfCities = distanceMatrix.dim();
        int current = start, next;
        // allocate tour space
        int[] tour = new int[numberOfCities+1];
        // this boolean array represent for each city (by index) if it has been visited (true) or not (false)
        boolean[] visited = new boolean[numberOfCities];
        Arrays.fill(visited, false); // init
        // let's move to the first city and start building the tour
        tour[0] = current;
        for (int q=1; q<numberOfCities; q++) {
            visited[current] = true; // set current city visited flag
            // chose the nearest city from neighborhood not visited yet
            next = nearestOf(distanceMatrix.neighbours(current), visited);
            // register city into tour
            tour[q] = next;
            // move to city
            current = next;
        }
        // end the tour by return to starting city
        tour[numberOfCities] = tour[0];
        return tour;
    }

    // this method receive a city and returns for it the nearest city from neighborhood not visited yet
    private int nearestOf(int[] numbers, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int closestNeighbour = -1;
        for(int i = 0; i < numbers.length; i++) {
            if(numbers[i] < minDistance && !visited[i]) {
                minDistance = numbers[i];
                closestNeighbour = i;
            }
        }
        return closestNeighbour;
    }
}
