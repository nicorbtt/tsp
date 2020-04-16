package ch.supsi.rubattu.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class TSPFile {

    // headers of the tsp input files
    public enum Header {
        BEST_KNOWN,
        COMMENT,
        DIMENSION,
        EDGE_WEIGHT_TYPE,
        NAME,
        TYPE
    }

    // map the will storing the properties in a key(Header)-value(String)
    private Map<Header, String> properties = new HashMap<>();
    // array of City that will contain parsed cities
    private City[] cities;

    public City[] parse(String tspProblem) {
        // get input stream from file from resource
        InputStream is = getClass().getResourceAsStream("/" + tspProblem + ".tsp");
        // build an input stream reader
        InputStreamReader isr = new InputStreamReader(is);
        // build a buffer reader
        BufferedReader br = new BufferedReader(isr);
        // boolean that will be used lambda internally to check city staring point and headers end
        final AtomicBoolean headers = new AtomicBoolean(true);
        // parse row by row form buffer reader
        br.lines().forEach(row -> {
            if (headers.get()) { // an header is being parsed
                String[] line = row.split(":");
                if (line[0].trim().equals("NODE_COORD_SECTION")) {
                    // cities will start, update boolean
                    headers.set(false);
                    // allocate memory for upcoming cities
                    cities = new City[Integer.parseInt(properties.get(Header.DIMENSION))];
                    return;
                }
                // save header properties into map
                Header header = Header.valueOf(line[0].trim());
                properties.put(header, line[1].trim());
            } else { // a city is being parsed
                String[] line = row.trim().split(" ");
                if (line[0].trim().equals("EOF")) return; // end of file reached
                // create city instance
                City city = new City(
                        Integer.parseInt(line[0]),      // ID
                        Double.parseDouble(line[1]),    // X coord
                        Double.parseDouble(line[2]));   // Y coord
                // save cities in order
                cities[city.id()-1] = city;
            }
        });
        return cities;
    }

    // method that outputs all the header properties (used with verbose option)
    public void printProperties() {
        properties.keySet().forEach(key -> {
            System.out.print(key + ": ");
            System.out.println(properties.get(key));
        });
    }

    // method that outputs all the cities (used with verbose option)
    public void printCities() {
        IntStream.range(0, cities.length).forEach(i -> System.out.println(cities[i]));
    }

    // method that return a single property by Header key
    public String getProperties(Header h) {
        return properties.get(h);
    }

    // method that will write tour final result (used with output option)
    public void output(int[] tour, double cost, long seed, double error100) {
        // Build the string content with a string builder...
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("SEED : ").append(seed).append(System.lineSeparator());
        //stringBuilder.append("ERROR100 : ").append(ratio).append(System.lineSeparator());
        stringBuilder.append("NAME : ").append(properties.get(Header.NAME)).append(".opt.tour");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("COMMENT : ").append("Tour result for ").append(properties.get(Header.NAME)).append(
                ".tsp").append(" (cost = ").append(cost).append(")");
        // N.B. here is also write the result cost of the final tour inside brackets (..)
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
            // ... and finally write it to file
            Files.write(Paths.get(path), stringBuilder.toString().getBytes());
        } catch (IOException ignore) { }
    }

}
