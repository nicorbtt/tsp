package ch.supsi.rubattu;

import ch.supsi.rubattu.core.Application;
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
        System.out.println("TSP Tuning started...");
        finalRatioResults = new ArrayList<>();
    }

    //TODO UPDATE NN + 2h-opt always exchange ratios

    @Test(timeout = MAX_TIME_MILLIS)
    public void ch130() {
        long seed = 0L;
        String[] args = {"-o -start1 -seed" + seed + " ch130"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void d198() {
        long seed = 0L;
        String[] args = {"-o -start30 -seed" + seed + " d198"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void eil76() {
        long seed = 0L;
        String[] args = {"-o -start7 -seed" + seed + " eil76"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void fl1577() {
        long seed = 1586959056958L;
        String[] args = {"-o -start461 -seed" + seed + " fl1577"};
        finalRatioResults.add(new Application(args).run());
    } // 0.571

    @Test(timeout = MAX_TIME_MILLIS)
    public void kroA100() {
        long seed = 0L;
        String[] args = {"-o -start17 -seed" + seed + " kroA100"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void lin318() {
        long seed = 0L;
        String[] args = {"-o -start292 -seed" + seed + " lin318"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pcb442() {
        long seed = 1586979372812L;
        String[] args = {"-o -start221 -seed" + seed + " pcb442"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pr439() {
        long seed = 1586023885384L;
        String[] args = {"-o -start301 -seed" + seed + " pr439"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void rat783() {
        long seed = 1586927476425L;
        String[] args = {"-o -start51 -seed" + seed + " rat783"};
        finalRatioResults.add(new Application(args).run());
    } // 0.307

    @Test(timeout = MAX_TIME_MILLIS)
    public void u1060() {
        long seed = 1586927260364L;
        String[] args = {"-o -start12 -seed" + seed + " u1060"};
        finalRatioResults.add(new Application(args).run());
    } // 0.797

    @AfterClass
    public static void total() {
        double finalRatio = finalRatioResults.stream().mapToDouble(a -> a).average().getAsDouble();
        for (int i=0;i<100; i++) System.out.print("-");
        System.out.println();
        System.out.format("%.3f%%\n", finalRatio);
    }
}