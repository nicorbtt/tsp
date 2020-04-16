package ch.supsi.rubattu.metaheuristic;

import java.util.concurrent.atomic.AtomicInteger;

public interface Metaheuristic {

    int[] optimize(int[] tour, AtomicInteger iterationOfBest, AtomicInteger finalCost);
}
