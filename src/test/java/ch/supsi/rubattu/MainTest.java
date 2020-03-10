package ch.supsi.rubattu;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class MainTest {

    Double[] ratios = new Double[10];

    /*
    @Test(timeout = 181000)
    public void easy() {
        System.out.print("ch130 ");
        String[] args = {"-o -start0", "src/main/resources/files/easy.tsp"};
        Main.main(args);
        // NN + Opt2: 0.59, 95ms
    }
    */

    @Test(timeout = 181000)
    public void ch130() {
        System.out.print("ch130 ");
        String[] args = {"-o -start42", "src/main/resources/files/ch130.tsp"};
        Main.main(args);
        // NN + Opt2: 0.0
        // 0.0
    }

    @Test(timeout = 181000)
    public void d198() {
        System.out.print("d198 ");
        String[] args = {"-o -start46", "src/main/resources/files/d198.tsp"};
        Main.main(args);
        // NN + Opt2: 0.03
        // 0.03
    }

    @Test(timeout = 181000)
    public void eil76() {
        System.out.print("eil76 ");
        String[] args = {"-o -start40", "src/main/resources/files/eil76.tsp"};
        Main.main(args);
        // NN + Opt2: 0.0
        // 0.0
    }

    @Test(timeout = 181000)
    public void kroA100() {
        System.out.print("kroA100 ");
        String[] args = {"-o -start37", "src/main/resources/files/kroA100.tsp"};
        Main.main(args);
        // NN + Opt2: 0.0
        // 0.0
    }

    @Test(timeout = 181000)
    public void fl1577() {
        System.out.print("fl1577 ");
        String[] args = {"-o -start906", "src/main/resources/files/fl1577.tsp"};
        Main.main(args);
        // NN + Opt2: 1.69
        // 1.51
    }

    @Test(timeout = 181000)
    public void lin318() {
        System.out.print("lin318 ");
        String[] args = {"-o -start85", "src/main/resources/files/lin318.tsp"};
        Main.main(args);
        // NN + Opt2: 0.27
        // 0.36
    }

    @Test(timeout = 18100000)
    public void pcb442() {
        System.out.print("pcb442 ");
        String[] args = {"-o -start98", "src/main/resources/files/pcb442.tsp"};
        Main.main(args);
        // NN + Opt2: 1.10
        // 1.40
    }

    @Test(timeout = 181000)
    public void pr439() {
        System.out.print("pr439 ");
        String[] args = {"-o -start21", "src/main/resources/files/pr439.tsp"};
        Main.main(args);
        // NN + Opt2: 0.19
        // 0.69
    }

    @Test(timeout = 181000)
    public void rat783() {
        System.out.print("rat738 ");
        String[] args = {"-o -start272", "src/main/resources/files/rat783.tsp"};
        Main.main(args);
        // NN + Opt2: 2.82
        // 2.41
    }

    @Test(timeout = 1810000)
    public void u1060() {
        System.out.print("u1060 ");
        String[] args = {"-o -start939", "src/main/resources/files/u1060.tsp"};
        Main.main(args);
        // NN + Opt2: 3.72
        // 3.48
    }

}