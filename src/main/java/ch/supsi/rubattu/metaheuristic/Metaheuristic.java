package ch.supsi.rubattu.metaheuristic;

import ch.supsi.rubattu.model.ExecutionInfo;

public interface Metaheuristic {

    void optimize(int[] tour, ExecutionInfo executionInfo);
}
