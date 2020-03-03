package ch.supsi.rubattu.model;

import ch.supsi.rubattu.model.City;

import java.util.List;

public class Result {

    private List<City> tour;
    private int cost;

    public Result(List<City> tour, int cost) {
        this.tour = tour;
        this.cost = cost;
    }

    public void printResult() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("result:\n")
                .append("------- ROUTE -------\n");
        tour.subList(0, tour.size()-1).forEach(node -> {
            stringBuilder.append(node.id())
                    .append(" -> ");
        });
        stringBuilder.append(tour.get(tour.size()-1).id());
        stringBuilder.append("\n----- COST: ").append(cost).append(" -----");
        System.out.println(stringBuilder.toString());
    }

    public List<City> tour() {
        return tour;
    }

    public int cost() {
        return cost;
    }
}
