package control;

import data.grid.event.EventListener;

import java.util.concurrent.TimeUnit;

public interface GameController {

    GameState getGameState();

    void addEventListener(EventListener eventListener);

    void removeEventListener(EventListener eventListener);

    void start();

    void pause();

    void resume();

    void checkGameEnd();

    void endGame();

    long getElapsedTime();

    long getElapsedTime(TimeUnit timeUnit);
}
