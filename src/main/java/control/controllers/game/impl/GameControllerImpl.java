package control.controllers.game.impl;

import control.controllers.game.GameController;
import control.controllers.game.GameState;
import control.controllers.input.InputTypeController;
import control.controllers.input.impl.InputTypeControllerImpl;
import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;
import data.event.impl.EventImpl;
import data.event.impl.EventObjectImpl;
import data.time.TimeCounter;

import java.util.concurrent.TimeUnit;

public abstract class GameControllerImpl implements GameController {

    private GameState gameState;
    private InputTypeController inputTypeController;
    private TimeCounter timeCounter;
    private EventObject eventObject;

    public GameControllerImpl() {
        timeCounter = new TimeCounter();
        eventObject = new EventObjectImpl();
        inputTypeController = new InputTypeControllerImpl();
        gameState = GameState.INITIALIZED;
    }

    @Override
    public final void start() {
        if (this.getGameState() != GameState.INITIALIZED) {
            throw new IllegalStateException("Game has been started already.");
        }
        timeCounter.start();

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
        timeCounter.stop();

        this.setGameState(GameState.PAUSED);
    }

    @Override
    public final void resume() {
        if (this.getGameState() != GameState.PAUSED) {
            throw new IllegalStateException("Cannot only resume if game is paused");
        }
        timeCounter.start();
        this.setGameState(GameState.RUNNING);
    }

    @Override
    public final void stop() {
        if (this.getGameState() == GameState.STOPPED) {
            throw new IllegalStateException("Game has been stopped already.");
        } else if (this.getGameState() == GameState.INITIALIZED) {
            throw new IllegalStateException("Game has not been started yet.");
        }
        this.setGameState(GameState.STOPPED);
    }

    @Override
    public void reset() {
        if (this.getGameState() != GameState.STOPPED) {
            throw new IllegalStateException("Game must be stopped first to reset.");
        }
        timeCounter.reset();
        this.setGameState(GameState.INITIALIZED);
    }

    @Override
    public final void endGame() {
        this.setGameState(GameState.STOPPED);
        timeCounter.stop();
    }

    @Override
    public long getElapsedTime() {
        return getElapsedTime(TimeUnit.SECONDS);
    }

    @Override
    public long getElapsedTime(TimeUnit timeUnit) {
        return timeCounter.getElapsedTime(timeUnit);
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    private void setGameState(GameState gameState) {
        GameState oldState = this.getGameState();
        this.gameState = gameState;
        if (oldState != gameState) {
            GameStateChangedEvent event = new GameStateChangedEvent(this, oldState, gameState);
            eventObject.fireEvent(event);
        }
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        eventObject.addListener(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        eventObject.removeListener(eventListener);
    }

    @Override
    public InputTypeController getInputTypeController() {
        return inputTypeController;
    }

    protected void fireEvent(Event event) {
        eventObject.fireEvent(event);
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
