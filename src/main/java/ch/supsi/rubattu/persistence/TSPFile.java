package ch.supsi.rubattu.persistence;

import ch.supsi.rubattu.model.City;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class TSPFile {

    public enum Header {
        BEST_KNOWN,
        COMMENT,
        DIMENSION,
        EDGE_WEIGHT_TYPE,
        NAME,
        TYPE
    }

    private Map<Header, String> properties = new HashMap<>();
    private City[] cities;

    public City[] parse(String path) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean headers = true;
        for (String row : lines) {
            if (headers) {
                String[] line = row.split(":");
                if (line[0].trim().equals("NODE_COORD_SECTION")) {
                    headers = false;
                    cities = new City[Integer.parseInt(properties.get(Header.DIMENSION))];
                    continue;
                }
                Header header = Header.valueOf(line[0].trim());
                properties.put(header, line[1].trim());
            } else {
                String[] line = row.trim().split(" ");
                if (line[0].trim().equals("EOF")) {
                    break;
                }
                City city = new City(Integer.parseInt(line[0]), Double.parseDouble(line[1]),
                        Double.parseDouble(line[2]));
                cities[city.id()-1] = city;
            }
        }
        return cities;
    }

    public void printProperties() {
        properties.keySet().forEach(key -> {
            System.out.print(key + ": ");
            System.out.println(properties.get(key));
        });
    }

    public void printCities() {
        IntStream.range(0, cities.length).forEach(i -> System.out.println(cities[i]));
    }

    public String getProperties(Header h) {
        return properties.get(h);
    }

    public void output(int[] tour, double cost, long seed, double ratio) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SEED : ").append(seed).append(System.lineSeparator());
        stringBuilder.append("RATIO : ").append(ratio).append(System.lineSeparator());
        stringBuilder.append("NAME : ").append(properties.get(Header.NAME)).append(".opt.tour");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("COMMENT : ").append("Tour result for ").append(properties.get(Header.NAME)).append(
                ".tsp").append(" (").append(cost).append(")");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("TYPE : TOUR");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("DIMENSION : ").append(properties.get(Header.DIMENSION));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("TOUR_SECTION");
        stringBuilder.append(System.lineSeparator());
        for (int q=0; q<tour.length-1; ++q) stringBuilder.append(tour[q]+1).append(System.lineSeparator());
        stringBuilder.append("-1").append(System.lineSeparator());
        stringBuilder.append("EOF");
        try {
            String path = properties.get(Header.NAME) + ".opt.tour";
            Files.write(Paths.get(path), stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
