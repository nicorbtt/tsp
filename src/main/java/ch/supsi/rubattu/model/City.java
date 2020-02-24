package ch.supsi.rubattu.model;

public class City {

    private Integer id;
    private Double coordX;
    private Double coordY;

    public City(Integer id, Double coordX, Double coordY) {
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Integer getId() {
        return id;
    }

    public Double getCoordX() {
        return coordX;
    }


    public Double getCoordY() {
        return coordY;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}
