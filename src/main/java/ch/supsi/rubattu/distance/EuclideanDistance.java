package ch.supsi.rubattu.distance;

import ch.supsi.rubattu.model.City;

// euclidean distance function
public class EuclideanDistance implements Distance {

    public int calculate(City c1, City c2) {
        double x = c1.x() - c2.x();
        double y = c1.y() - c2.y();
        // N.B. round: double --> int
        return (int) Math.round(Math.sqrt(x*x + y*y));
    }
}
