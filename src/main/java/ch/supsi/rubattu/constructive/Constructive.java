package ch.supsi.rubattu.constructive;

import ch.supsi.rubattu.model.City;
import ch.supsi.rubattu.model.DistanceMatrix;
import ch.supsi.rubattu.model.Result;

public interface Constructive {

    Result compute(City[] cities, DistanceMatrix distanceMatrix);
}
