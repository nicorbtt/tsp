package ch.supsi.rubattu.model;

public class EuclideanDistance implements Distance {

    public int calculate(City c1, City c2) {
        int x = (int) Math.abs(c1.getCoordX() - c2.getCoordX());
        int y = (int) Math.abs(c1.getCoordY() - c2.getCoordY());
        return (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }
}
