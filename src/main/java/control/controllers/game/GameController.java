package control.controllers.game;

import control.controllers.input.InputTypeController;
import data.grid.event.EventListener;

import java.util.concurrent.TimeUnit;

public interface GameController {

    GameState getGameState();

    InputTypeController getInputTypeController();

    void addEventListener(EventListener eventListener);

    void removeEventListener(EventListener eventListener);

    void start();

    void pause();

    void resume();

    void stop();

    void reset();

    void checkGameEnd();

    void endGame();

    long getElapsedTime();

    long getElapsedTime(TimeUnit timeUnit);
}
