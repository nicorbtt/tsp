package ch.supsi.rubattu.distance;

import ch.supsi.rubattu.model.City;

public class EuclideanDistance implements Distance {

    public double calculate(City c1, City c2) {
        double x = c1.x() - c2.x();
        double y = c1.y() - c2.y();
        return Math.round(Math.sqrt(x*x + y*y));
    }
}
