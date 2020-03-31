package ch.supsi.rubattu.distance;

import ch.supsi.rubattu.model.City;

public class GeoDistance implements Distance {

    @Override
    public int calculate(City c1, City c2) {
        double lon1, lon2, lat1, lat2;
        lat1 = Math.toRadians(c1.x());
        lat2 = Math.toRadians(c2.x());
        lon1 = Math.toRadians(c1.y());
        lon2 = Math.toRadians(c2.y());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        return (int) Math.round(c * r);
    }
}
