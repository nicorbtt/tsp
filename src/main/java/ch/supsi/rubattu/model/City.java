package ch.supsi.rubattu.model;

// class that hold per city data
public class City {

    // city identifier
    private int id;
    // city x coordinate
    private double x;
    // city y coordinate
    private double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int id() {
        return id;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
