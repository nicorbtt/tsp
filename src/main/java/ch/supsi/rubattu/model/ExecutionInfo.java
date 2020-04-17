package ch.supsi.rubattu.model;

// class that will store some metaheuristc useful information
public class ExecutionInfo {

    private int bestIterationAt;    // number of iterations done
    private int numberOfIterations; // number of iteration in which the best result will be found
    private int cost;               // final cost of the solution

    public ExecutionInfo() {
    }

    public int getBestIterationAt() {
        return bestIterationAt;
    }

    public void setBestIterationAt(int bestIterationAt) {
        this.bestIterationAt = bestIterationAt;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
