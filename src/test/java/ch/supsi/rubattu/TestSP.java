package ch.supsi.rubattu;

import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

//@Ignore
public class TestSP {

    private static List<Double> finalRatioResults;
    private final static long MAX_TIME_MILLIS = 181_000;

    @BeforeClass
    public static void setUp() {
        System.out.println("TSP Test started...");
        //System.out.println();
        finalRatioResults = new ArrayList<>();
    }

    @Test(timeout = MAX_TIME_MILLIS)
    public void ch130() {
        // NN + 2hopt: 2.06 con start = 42 (76ms)
        long seed = 1586013051794L;
        String[] args = {"-o -start43 -seed" + seed + " ch130"};
        finalRatioResults.add(new Application(args).run());
    } //0.0

    @Test(timeout = MAX_TIME_MILLIS)
    public void d198() {
        // NN + 2hopt: 0.23 con start index = 136 (109ms)
        long seed = 1586013353304L;
        String[] args = {"-o -start137 -seed" + seed + " d198"};
        finalRatioResults.add(new Application(args).run());
    } //0.0

    @Test(timeout = MAX_TIME_MILLIS)
    public void eil76() {
        // NN + 2hopt: 1.49 con start index = 6 (47ms)
        long seed = 1586013194541L;
        String[] args = {"-o -start7 -seed" + seed + " eil76"};
        finalRatioResults.add(new Application(args).run());
    } //0.0

    @Test(timeout = MAX_TIME_MILLIS)
    public void fl1577() {
        // NN + 2hopt: 1.28 con start index = 902/772 (7000ms)
        long seed = 1586562844037L;
        String[] args = {"-o -start903 -seed" + seed + " fl1577"};
        finalRatioResults.add(new Application(args).run());
    } // 0.82

    @Test(timeout = MAX_TIME_MILLIS)
    public void kroA100() {
        // NN + 2hopt: 0.00 con start index = 1 (50ms)
        long seed = 0L;
        String[] args = {"-o -start2 -seed" + seed + " kroA100"};
        finalRatioResults.add(new Application(args).run());
    } //0.0

    @Test(timeout = MAX_TIME_MILLIS)
    public void lin318() {
        // NN + 2hopt: 1.83 con start index = 291 (193ms)
        long seed = 1586013696890L;
        String[] args = {"-o -start292 -seed" + seed + " lin318"};
        finalRatioResults.add(new Application(args).run());
    } //0.0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pcb442() {
        // NN + 2hopt: 1.23 con start index = 191 (260ms)
        long seed = 1586347639507L;
        String[] args = {"-o -start192 -seed" + seed + " pcb442"};
        finalRatioResults.add(new Application(args).run());
    } // 0.26

    @Test(timeout = MAX_TIME_MILLIS)
    public void pr439() {
        // NN + 2hopt: 1.12 con start index = 20 (270ms)
        long seed = 1586023885384L;
        String[] args = {"-o -start21 -seed" + seed + " pr439"};
        finalRatioResults.add(new Application(args).run());
    } // 0.00(2)

    @Test(timeout = MAX_TIME_MILLIS)
    public void rat783() {
        // NN + 2hopt: 3.37 con start index = 4 (1036ms)
        long seed = 1586497700242L;
        String[] args = {"-o -start5 -seed" + seed + " rat783"};
        finalRatioResults.add(new Application(args).run());
    } // 1.53

    @Test(timeout = MAX_TIME_MILLIS)
    public void u1060() {
        // NN + 2hopt: 3.84 con start index = 388 (2701ms)
        long seed = 1586059798720L;
        String[] args = {"-o -start389 -seed" + seed + " u1060"};
        finalRatioResults.add(new Application(args).run());
    } // 1.63

    @AfterClass
    public static void total() {
        double finalRatio = finalRatioResults.stream().mapToDouble(a -> a).average().getAsDouble();
        for (int i=0;i<77; i++) System.out.print("-");
        System.out.println();
        System.out.format("%.3f\n", finalRatio);
    }
}