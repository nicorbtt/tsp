package ch.supsi.rubattu.model;

public class Stopwatch {

    enum STATE {
        BUSY,
        AVAILABLE
    }

    private Long start;
    private Long end;

    private STATE state = STATE.AVAILABLE;


    public void start() {
        if (state != STATE.AVAILABLE)
            return;

        start = System.currentTimeMillis();
        state = STATE.BUSY;
    }

    public void end() {
        if (state != STATE.BUSY) System.out.println("millis: 0");
        end = System.currentTimeMillis();
        state = STATE.AVAILABLE;
        long millis = end - start;
        System.out.println("millis: " + millis);
    }
}
