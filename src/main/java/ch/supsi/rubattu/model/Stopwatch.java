package ch.supsi.rubattu.model;

// a simple stopwatch with internal state and counter
public class Stopwatch {

    private enum STATE {
        BUSY,       // means that the stopwatch is running
        AVAILABLE   // means that the stopwatch is not recording anything
    }
    // N.B. state not used by the application by the way

    // record the time in which the stopwatch started keep time
    private Long start;
    // internal state
    private STATE state = STATE.AVAILABLE;

    // method to start the stopwatch
    public void start() {
        if (state != STATE.AVAILABLE)
            return;

        start = System.currentTimeMillis();
        state = STATE.BUSY;
    }

    // method to read the stopwatch time
    public long time() {
        return System.currentTimeMillis() - start;
    }

    // method to stop the stopwatch. it also return stop time minus previous start
    public long end() {
        if (state != STATE.BUSY) return 0L;
        Long end = System.currentTimeMillis();
        state = STATE.AVAILABLE;
        return end - start;
    }
}
// N.B. System.currentTimeMillis() was used to perform time acquisition
