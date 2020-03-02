package ch.supsi.rubattu;

import org.junit.Test;

public class MainTest {

    @Test(timeout = 181000)
    public void easy() {
        String[] args = {"-o", "-v", "src/main/resources/files/easy.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void ch130() {
        String[] args = {"-o", "-v", "src/main/resources/files/ch130.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void d198() {
        String[] args = {"-v", "src/main/resources/files/d198.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void eil76() {
        String[] args = {"-o","-v", "src/main/resources/files/eil76.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void kroA100() {
        String[] args = {"-o","-v", "src/main/resources/files/kroA100.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void fl1577() {
        String[] args = {"-o", "-v", "src/main/resources/files/fl1577.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void lin318() {
        String[] args = {"-v", "src/main/resources/files/lin318.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void pcb442() {
        String[] args = {"-v", "src/main/resources/files/pcb442.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void pr439() {
        String[] args = {"-v", "src/main/resources/files/pr439.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void rat783() {
        String[] args = {"-v", "src/main/resources/files/rat783.tsp"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void u1060() {
        String[] args = {"-v", "src/main/resources/files/u1060.tsp"};
        Main.main(args);
    }

}