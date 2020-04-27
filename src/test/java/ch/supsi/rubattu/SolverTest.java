package ch.supsi.rubattu;

import ch.supsi.rubattu.core.Application;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

//@Ignore
public class SolverTest {

    private static List<Double> finalRatioResults;
    private final static long MAX_TIME_MILLIS = 181_000;

    @BeforeClass
    public static void setUp() {
        System.out.println("TSP Tuning started...");
        finalRatioResults = new ArrayList<>();
    }

    @Test(timeout = MAX_TIME_MILLIS)
    public void ch130() {
        // NN + 2h-opt: 1.47 in 350 ms con start = 18
        String[] args = {"-o -start18 ch130"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void d198() {
        // NN + 2h-opt: 0.71 in 600 ms con start = 31
        String[] args = {"-o -start31 d198"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void eil76() {
        // NN + 2h-opt: 0 in 20 ms con start = 1
        String[] args = {"-o -start1 eil76"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void fl1577() {
        // NN + 2h-opt: 2.57 in 10 minuti con start = 461
        long seed = 1587216352705L;
        String[] args = {"-o -start461 -seed" + seed + " fl1577"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0.409

    @Test(timeout = MAX_TIME_MILLIS)
    public void kroA100() {
        // NN + 2h-opt: 0.18 in 190 ms con start = 17
        String[] args = {"-o -start17 kroA100"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void lin318() {
        // NN + 2h-opt: 1.69 in 1.9 secondi con start = 97
        String[] args = {"-o -start97 lin318"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pcb442() {
        // NN + 2h-opt: 1.22 in 5.7 secondi con start = 221
        long seed = 1586979372812L;
        String[] args = {"-o -start221 -seed" + seed + " pcb442"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void pr439() {
        // NN + 2h-opt: 1.77 in 5.5 secondi con start = 301
        long seed = 1586023885384L;
        String[] args = {"-o -start301 -seed" + seed + " pr439"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0

    @Test(timeout = MAX_TIME_MILLIS)
    public void rat783() {
        // NN + 2h-opt: 3.83 in 38 secondi con start = 51
        long seed = 1586927476425L;
        String[] args = {"-o -start51 -seed" + seed + " rat783"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0.307

    @Test(timeout = MAX_TIME_MILLIS)
    public void u1060() {
        // NN + 2h-opt: 3.78 in 2 minuti con start = 12
        long seed = 1587257994270L;
        String[] args = {"-o -start12 -seed" + seed + " u1060"};
        finalRatioResults.add(new Application(args).run());
    } // current best: 0.586

    @AfterClass
    public static void total() {
        double finalRatio = finalRatioResults.stream().mapToDouble(a -> a).average().getAsDouble();
        for (int i=0;i<100; i++) System.out.print("-");
        System.out.println();
        System.out.format("%.3f%%\n", finalRatio);
    }
}