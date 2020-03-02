package ch.supsi.rubattu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NearestNeighbor implements Algorithm {

    @Override
    public Result compute(City[] cities, Data data) {
        int numberOfNodes = data.getData().length;
        int currentNode = 0;

        List<Integer> route = new ArrayList<>();
        int cost = 0;
        route.add(currentNode);

        for (int q=1; q<numberOfNodes; q++) {
            int nextNodeIndex;
            nextNodeIndex = findMinIdx(route, data.getData()[currentNode]);
            cost += data.getData()[currentNode][nextNodeIndex];
            route.add(nextNodeIndex);
            currentNode = nextNodeIndex;
        }
        int firstNode = route.get(0);
        cost += data.getData()[currentNode][firstNode];
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
