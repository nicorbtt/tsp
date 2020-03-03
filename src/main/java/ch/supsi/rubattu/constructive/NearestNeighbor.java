package ch.supsi.rubattu.constructive;

import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NearestNeighbor implements Constructive {

    private int start;

    public NearestNeighbor(int start) {
        this.start = start;
    }

    @Override
    public Result compute(City[] cities, DistanceMatrix distanceMatrix) {
        int numberOfNodes = distanceMatrix.data().length;
        int currentNode = start;

        List<Integer> route = new ArrayList<>();
        int cost = 0;
        route.add(currentNode);

        for (int q=1; q<numberOfNodes; q++) {
            int nextNodeIndex;
            nextNodeIndex = findMinIdx(route, distanceMatrix.data()[currentNode]);
            cost += distanceMatrix.data()[currentNode][nextNodeIndex];
            route.add(nextNodeIndex);
            currentNode = nextNodeIndex;
        }
        int firstNode = route.get(0);
        cost += distanceMatrix.data()[currentNode][firstNode];
        route.add(firstNode);

        return new Result(
                route.stream().map(integer -> cities[integer]).collect(Collectors.toList()),
                cost);
    }

    private int findMinIdx(List<Integer> route, Integer[] numbers) {
        Integer minVal = Integer.MAX_VALUE;
        int minIdx = -1;
        for(int idx = 0; idx < numbers.length; idx++) {
            if(numbers[idx] < minVal && numbers[idx] != -1 && !route.contains(idx)) {
                minVal = numbers[idx];
                minIdx = idx;
            }
        }
        return minIdx;
    }
}
