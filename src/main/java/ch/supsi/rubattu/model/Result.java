package ch.supsi.rubattu.model;

import ch.supsi.rubattu.model.City;

import java.util.List;

public class Result {

    private City[] tour;
    private double cost;

    public Result(City[] tour, double cost) {
        this.tour = tour;
        this.cost = cost;
    }

    public void printResult() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("result:\n")
                .append("------- ROUTE -------\n");
        for (int q=0; q<tour.length; ++q) {
            stringBuilder.append(tour[q].id());
            if (q != tour.length) stringBuilder.append("->");
        }
        stringBuilder.append("\n----- COST: ").append(cost).append(" -----");
        System.out.println(stringBuilder.toString());
    }

    public City[] tour() {
        return tour;
    }

    public double cost() {
        return cost;
    }
}
