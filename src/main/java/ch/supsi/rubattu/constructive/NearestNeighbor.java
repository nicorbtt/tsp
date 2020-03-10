package ch.supsi.rubattu.constructive;

import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NearestNeighbor implements Constructive {

    private int start;

    public NearestNeighbor(int start) {
        this.start = start;
    }

    @Override
    public Result compute(City[] cities, DistanceMatrix distanceMatrix) {
        int numberOfNodes = distanceMatrix.data().length;
        int currentNode = start;

        int[] route = new int[numberOfNodes+1];
        int cost = 0;
        route[0] = currentNode;

        boolean[] visited = new boolean[numberOfNodes];
        for (boolean b : visited) b = false;

        for (int q=1; q<numberOfNodes; q++) {
            visited[currentNode] = true;
            int nextNodeIndex;
            nextNodeIndex = findMinIdx(route, distanceMatrix.data()[currentNode], visited);
            cost += distanceMatrix.data()[currentNode][nextNodeIndex];
            route[q] = nextNodeIndex;
            currentNode = nextNodeIndex;
        }
        int firstNode = route[0];
        cost += distanceMatrix.data()[currentNode][firstNode];
        route[numberOfNodes] = firstNode;

        City[] citiesTour = new City[numberOfNodes+1];
        IntStream.range(0, route.length).forEach(i -> citiesTour[i] = cities[route[i]]);

        return new Result(
                citiesTour,
                cost);
    }

    private int findMinIdx(int[] route, int[] numbers, boolean[] visited) {
        int minVal = Integer.MAX_VALUE;
        int minIdx = -1;
        for(int idx = 0; idx < numbers.length; idx++) {
            if(numbers[idx] < minVal && numbers[idx] != -1 && !visited[idx]) {
                minVal = numbers[idx];
                minIdx = idx;
            }
        }
        return minIdx;
    }
}
