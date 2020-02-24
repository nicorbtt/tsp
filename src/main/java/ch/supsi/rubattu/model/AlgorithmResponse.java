package ch.supsi.rubattu.model;

import java.util.List;

public class AlgorithmResponse {

    private List<Integer> route;
    private int cost;

    public AlgorithmResponse(List<Integer> route, int cost) {
        this.route = route;
        this.cost = cost;
    }

    public void printResult() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Result:\n")
                .append("----- ROUTE -----\n");
        route.subList(0, route.size()-1).forEach(node -> {
            stringBuilder.append(node)
                    .append("->");
        });
        stringBuilder.append(route.get(route.size()-1));
        stringBuilder.append("\n----- COST: ").append(cost).append(" -----");
        System.out.println(stringBuilder.toString());
    }
}
