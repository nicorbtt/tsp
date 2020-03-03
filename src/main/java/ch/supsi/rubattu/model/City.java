package ch.supsi.rubattu.model;

public class City {

    private Integer id;
    private Double x;
    private Double y;

    public City(Integer id, Double x, Double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Integer id() {
        return id;
    }

    public Double x() {
        return x;
    }


    public Double y() {
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
