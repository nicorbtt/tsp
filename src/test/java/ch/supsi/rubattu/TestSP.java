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

    @Test(timeout = MAX_TIME_MILLIS)
    public void ch130() {
        // NN + 2h-opt: 1.47
        String[] args = {"-o -start17 ch130"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void d198() {
        // NN + 2h-opt: 0.71
        String[] args = {"-o -start30 d198"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void eil76() {
        // NN + 2h-opt: 0
        String[] args = {"-o -start1 eil76"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void fl1577() {
        // NN + 2h-opt: 2.57
        long seed = 1586959056958L;
        String[] args = {"-o -start461 -seed" + seed + " fl1577"};
        finalRatioResults.add(new Application(args).run());
    } // 0.571

    @Test(timeout = MAX_TIME_MILLIS)
    public void kroA100() {
        // NN + 2h-opt: 0.18
        String[] args = {"-o -start17 kroA100"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void lin318() {
        // NN + 2h-opt: 1.69
        String[] args = {"-o -start97 lin318"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pcb442() {
        // NN + 2h-opt: 1.22
        long seed = 1586979372812L;
        String[] args = {"-o -start221 -seed" + seed + " pcb442"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pr439() {
        // NN + 2h-opt: 1.77
        long seed = 1586023885384L;
        String[] args = {"-o -start301 -seed" + seed + " pr439"};
        finalRatioResults.add(new Application(args).run());
    } // 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void rat783() {
        // NN + 2h-opt: 3.83
        long seed = 1586927476425L;
        String[] args = {"-o -start51 -seed" + seed + " rat783"};
        finalRatioResults.add(new Application(args).run());
    } // 0.307

    @Test(timeout = MAX_TIME_MILLIS)
    public void u1060() {
        // NN + 2h-opt: 3.78
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