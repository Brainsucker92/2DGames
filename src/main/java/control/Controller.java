package control;

public interface Controller<T> {

    boolean placeToken(int tileIndex);

    T getCurrentPly();

    T setNextPly();

    void checkGameEnd();

    void endGame();
}
