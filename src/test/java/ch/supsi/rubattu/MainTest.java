package ch.supsi.rubattu;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class MainTest {

    @Test(timeout = 181000)
    public void ch130() {
        //System.out.print("ch130 ");
        long seed = 3504153904398336357L;
        String[] args = {"-o -start43 -seed" + seed, "src/main/resources/files/ch130.tsp"};
        new Application(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void d198() {
        //System.out.print("d198 ");
        long seed = 3889279557690330797L;
        String[] args = {"-o -start47 -seed" + seed, "src/main/resources/files/d198.tsp"};
        new Application(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void eil76() {
        //System.out.print("eil76 ");
        long seed = -4376467996864245069L;
        String[] args = {"-o -start41 -seed" + seed, "src/main/resources/files/eil76.tsp"};
        new Application(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void fl1577() {
        //System.out.print("fl1577 ");
        long seed = -3885963329649321493L;
        String[] args = {"-o -start907 -seed" + seed, "src/main/resources/files/fl1577.tsp"};
        new Application(args).run();
        // 1.59
    }

    @Test(timeout = 181000)
    public void kroA100() {
        //System.out.print("kroA100 ");
        long seed = -6942612363320845723L;
        String[] args = {"-o -start38 -seed" + seed, "src/main/resources/files/kroA100.tsp"};
        new Application(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void lin318() {
        //System.out.print("lin318 ");
        long seed = -4073067388901061276L;
        String[] args = {"-o -start86 -seed" + seed, "src/main/resources/files/lin318.tsp"};
        new Application(args).run();
        // 0.00
    }

    @Test(timeout = 18100000)
    public void pcb442() {
        //System.out.print("pcb442 ");
        long seed = -8007212416927809231L;
        String[] args = {"-o -start99 -seed" + seed, "src/main/resources/files/pcb442.tsp"};
        new Application(args).run();
        // 0.45
    }

    @Test(timeout = 181000)
    public void pr439() {
        //System.out.print("pr439 ");
        long seed = 4545270770759811194L;
        String[] args = {"-o -start22 -seed" + seed, "src/main/resources/files/pr439.tsp"};
        new Application(args).run();
        // 0.21
    }

    @Test(timeout = 181000)
    public void rat783() {
        //System.out.print("rat738 ");
        long seed = -4807687745892878130L;
        String[] args = {"-o -start273 -seed" + seed, "src/main/resources/files/rat783.tsp"};
        new Application(args).run();
        // 1.83
    }

    @Test(timeout = 1810000)
    public void u1060() {
        //System.out.print("u1060 ");
        long seed = 5591943253952769003L;
        String[] args = {"-o -start940 -seed" + seed, "src/main/resources/files/u1060.tsp"};
        new Application(args).run();
        // 2.81
    }

    // 0.689
}