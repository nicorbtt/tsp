package ch.supsi.rubattu.distance;

import ch.supsi.rubattu.model.City;

public class EuclideanDistance implements Distance {

    public int calculate(City c1, City c2) {
        int x = (int) Math.abs(c1.x() - c2.x());
        int y = (int) Math.abs(c1.y() - c2.y());
        return (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }
}
