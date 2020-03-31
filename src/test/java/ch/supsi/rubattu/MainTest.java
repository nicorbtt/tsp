package ch.supsi.rubattu;

import ch.supsi.rubattu.local_search.Opt2h;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Ignore
public class MainTest {

    @Test(timeout = 181000)
    public void ch130() throws IOException {
        long seed = 3504153904398336357L;
        String[] args = {"-o -start43 -seed" + seed + " ch130"};
        new Application(args).run();
        //new NN2opt(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void d198() throws IOException {
        long seed = 3889279557690330797L;
        String[] args = {"-o -start47 -seed" + seed + " d198"};
        //new Application(args).run();
        new NN2opt(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void eil76() throws IOException {
        long seed = -4376467996864245069L;
        String[] args = {"-o -start41 -seed" + seed + " eil76"};
        new Application(args).run();
        //new NN2opt(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void fl1577() throws IOException {
        long seed = -6002974147964378808L;
        String[] args = {"-o -start907 -seed" + seed + " fl1577"};
        new Application(args).run();
        //new NN2opt(args).run();
        // 1.56
    }

    @Test(timeout = 181000)
    public void kroA100() throws IOException {
        long seed = -6942612363320845723L;
        String[] args = {"-o -start38 -seed" + seed + " kroA100"};
        //new Application(args).run();
        new NN2opt(args).run();
        // 0.00
    }

    @Test(timeout = 181000)
    public void lin318() throws IOException {
        long seed = -4073067388901061276L;
        String[] args = {"-o -start86 -seed" + seed + " lin318"};
        //new Application(args).run();
        new NN2opt(args).run();
        // 0.00
    }

    @Test(timeout = 18100000)
    public void pcb442() throws IOException {
        long seed = 118931178804611181L;
        String[] args = {"-o -start99 -seed" + seed + " pcb442"};
        //new Application(args).run();
        new NN2opt(args).run();
        // 0.42
    }

    @Test(timeout = 181000)
    public void pr439() {
        long seed = -2476429649818560355L;
        String[] args = {"-o -start22 -seed" + seed + " pr439"};
        new Application(args).run();
        // 0.17
    }

    @Test(timeout = 181000)
    public void rat783() {
        long seed = 2715173628733022105L;
        String[] args = {"-o -start273 -seed" + seed + " rat783"};
        new Application(args).run();
        // 1.74
    }

    @Test(timeout = 1810000)
    public void u1060() throws IOException {
        long seed = -6379558533234353273L;
        String[] args = {"-o -start940 -seed" + seed + " u1060"};
        new Application(args).run();
        //new NN2opt(args).run();
        // 2.71
    }

    // 0.663
}