package control;

import java.util.concurrent.TimeUnit;

public class TimeCounter {

    private long elapsedTime;
    private long startTime;
    private State state = State.INITIALIZED;

    public void start() {
        startTime = System.nanoTime();
        state = State.STARTED;
    }

    public void stop() {
        if (state != State.STARTED) {
            throw new IllegalStateException("TimeCounter has not been started yet.");
        }
        updateElapsedTime();
        state = State.STOPPED;
    }

    public void reset() {
        elapsedTime = 0;
        state = State.INITIALIZED;
    }

    public long getElapsedTime(TimeUnit timeUnit) {
        updateElapsedTime();
        return timeUnit.convert(elapsedTime, TimeUnit.NANOSECONDS);
    }

    private void updateElapsedTime() {
        long currentTime = System.nanoTime();
        long diff = currentTime - startTime;
        elapsedTime += diff;
        startTime = currentTime;
    }

    private enum State {INITIALIZED, STARTED, STOPPED}
}
