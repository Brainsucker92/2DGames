package control.impl;

import control.GameController;
import control.GameState;
import data.grid.event.Event;
import data.grid.event.EventListener;
import data.grid.event.EventObject;
import data.grid.event.impl.EventImpl;
import data.grid.event.impl.EventObjectImpl;

import java.util.concurrent.TimeUnit;

public abstract class GameControllerImpl implements GameController {

    private GameState gameState;
    private long elapsedTime;
    private long lastTime;

    private EventObject eventObject;

    public GameControllerImpl() {
        elapsedTime = 0;

        eventObject = new EventObjectImpl();

        gameState = GameState.INITIALIZED;
    }

    @Override
    public final void start() {
        if (this.getGameState() != GameState.INITIALIZED) {
            throw new IllegalStateException("Game has been started already.");
        }
        lastTime = System.nanoTime();

        this.setGameState(GameState.RUNNING);
    }

    @Override
    public final void pause() {
        if (this.getGameState() == GameState.PAUSED) {
            throw new IllegalStateException("Game is already paused");
        } else if (this.getGameState() == GameState.STOPPED) {
            throw new IllegalStateException("Game has been stopped already.");
        } else if (this.getGameState() != GameState.RUNNING) {
            throw new IllegalStateException("Game must be running to pause.");
        }
        updateElapsedTime();

        this.setGameState(GameState.PAUSED);
    }

    @Override
    public final void resume() {
        if (this.getGameState() != GameState.PAUSED) {
            throw new IllegalStateException("Cannot only resume if game is paused");
        }
        lastTime = System.nanoTime();

        this.setGameState(GameState.RUNNING);
    }

    @Override
    public final void endGame() {
        updateElapsedTime();

        this.setGameState(GameState.STOPPED);
    }

    @Override
    public long getElapsedTime() {
        updateElapsedTime();
        return elapsedTime;
    }

    @Override
    public long getElapsedTime(TimeUnit timeUnit) {
        updateElapsedTime();
        return timeUnit.convert(elapsedTime, TimeUnit.NANOSECONDS);
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    private void setGameState(GameState gameState) {
        GameState oldState = this.getGameState();
        this.gameState = gameState;
        GameStateChangedEvent event = new GameStateChangedEvent(this, oldState, gameState);
        eventObject.fireEvent(event);
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        eventObject.addListener(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        eventObject.removeListener(eventListener);
    }

    protected void fireEvent(Event event) {
        eventObject.fireEvent(event);
    }

    private void updateElapsedTime() {
        long currentTime = System.nanoTime();

        long diff = currentTime - lastTime;
        lastTime = System.nanoTime();
        elapsedTime += diff;
    }

    public class GameStateChangedEvent extends EventImpl {

        private GameState oldState;
        private GameState newState;

        GameStateChangedEvent(Object source, GameState oldState, GameState newState) {
            super(source);
            this.oldState = oldState;
            this.newState = newState;
        }

        public GameState getOldState() {
            return oldState;
        }

        public GameState getNewState() {
            return newState;
        }
    }

    public class GameTickEvent extends EventImpl {

        private long timeDelta;
        private TimeUnit timeUnit;

        public GameTickEvent(Object source, long timeDelta, TimeUnit timeUnit) {
            super(source);
            this.timeDelta = timeDelta;
            this.timeUnit = timeUnit;
        }

        public long getTimeDelta() {
            return timeDelta;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }
    }
}
