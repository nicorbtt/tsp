package ch.supsi.rubattu;

import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class MainTest {

    private static List<Double> finalRatioResults;

    @BeforeClass
    public static void setUp() {
        System.out.println("TSP Test started...");
        finalRatioResults = new ArrayList<>();
    }

    @Test(timeout = 181000)
    public void ch130() {
        long seed = 6109324501115747736L;
        String[] args = {"-o -start43 -seed" + seed + " ch130"};
        finalRatioResults.add(new Application(args).run());
        // 0.00 (43)

        // 2.06 con start = 42
    } //0.0

    @Test(timeout = 181000)
    public void d198() {
        long seed = -3929130589026105024L;
        String[] args = {"-o -start137 -seed" + seed + " d198"};
        finalRatioResults.add(new Application(args).run());
        // 0.00 (47)

        // 0.23 con start index = 136
    } //0.0

    @Test(timeout = 181000)
    public void eil76() {
        long seed = -3213749537551225021L;
        String[] args = {"-o -start7 -seed" + seed + " eil76"};
        finalRatioResults.add(new Application(args).run());
        // 0.00 (41)

        // 1.49 con start index = 6
    } //0.0

    @Test(timeout = 181000)
    public void fl1577() {
        long seed = -5633522689363499015L;
        String[] args = {"-o -start905 -seed" + seed + " fl1577"};
        finalRatioResults.add(new Application(args).run());
        // 1.56 (907)

        // 1.32 con start index = 904
    } //1.05

    @Test(timeout = 181000)
    public void kroA100() {
        long seed = 7576460394046165367L;
        String[] args = {"-o -start2 -seed" + seed + " kroA100"};
        finalRatioResults.add(new Application(args).run());
        // 0.00 (38)

        // 0.00 con start index = 1
    } //0.0

    @Test(timeout = 181000)
    public void lin318() {
        long seed = -3962483518170464170L;
        String[] args = {"-o -start292 -seed" + seed + " lin318"};
        finalRatioResults.add(new Application(args).run());
        // 0.00 (86)

        // 1.83 con start index = 291
    } //0.0

    @Test(timeout = 181000)
    public void pcb442() {
        long seed = -2476969357809750925L;
        String[] args = {"-o -start265 -seed" + seed + " pcb442"};
        finalRatioResults.add(new Application(args).run());
        // 0.42 (99)

        // 1.33 con start index = 264
    } //0.43

    @Test(timeout = 181000)
    public void pr439() {
        long seed = 8750352027151713870L;
        String[] args = {"-o -start21 -seed" + seed + " pr439"};
        finalRatioResults.add(new Application(args).run());
        // 0.17 (22)

        // 1.12 con start index = 20
    } //0.24

    @Test(timeout = 181000)
    public void rat783() {
        long seed = 2386762643059289700L;
        String[] args = {"-o -start5 -seed" + seed + " rat783"};
        finalRatioResults.add(new Application(args).run());
        // 1.74 (273)

        // 3.37 con start index = 4
    } //1.81

    @Test(timeout = 181000)
    public void u1060() {
        long seed = 1895078335554216164L; //1585954271810 (2.48)
        String[] args = {"-o -start389 -seed" + seed + " u1060"};
        finalRatioResults.add(new Application(args).run());
        // 2.71 (940)

        // 3.84 con start index = 388
    } //2.74

    // 0.663

    // 0.627

    @AfterClass
    public static void total() {
        double finalRatio = finalRatioResults.stream().mapToDouble(a -> a).average().getAsDouble();
        for (int i=0;i<77; i++) System.out.print("-");
        System.out.println();
        System.out.format("%.3f\n", finalRatio);
    }
}