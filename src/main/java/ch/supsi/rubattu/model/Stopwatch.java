package ch.supsi.rubattu.model;

public class Stopwatch {

    private static Stopwatch ourInstance = new Stopwatch();

    public static Stopwatch getInstance() {
        return ourInstance;
    }

    private Stopwatch() {
    }

    private enum STATE {
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

    public long time() {
        return System.currentTimeMillis() - start;
    }

    public long end() {
        if (state != STATE.BUSY) return 0L;
        end = System.currentTimeMillis();
        state = STATE.AVAILABLE;
        return end - start;
    }
}
