package control;

public interface GameController<T> {

    boolean placeToken(int tileIndex);

    T getCurrentPly();

    T setNextPly();

    void checkGameEnd();

    void endGame();
}
