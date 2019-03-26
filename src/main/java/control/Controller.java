package control;

import data.grid.Grid2D;
import ui.EventGridPanel;

import java.util.Objects;

public abstract class Controller<T> {

    protected Grid2D<T> dataGrid;
    protected EventGridPanel panel;
    private T currentPly;

    public Controller(Grid2D<T> dataGrid, EventGridPanel panel, T startToken) {
        this.dataGrid = dataGrid;
        this.panel = panel;
        this.currentPly = startToken;

    }

    protected boolean placeToken(int tileIndex) {
        int[] coordinates = dataGrid.getCoordinates(tileIndex);
        int columnIndex = coordinates[0];
        int rowIndex = coordinates[1];
        if (dataGrid.isEmpty(rowIndex, columnIndex)) {
            dataGrid.setValue(rowIndex, columnIndex, currentPly);
            checkGameEnd();
            changeCurrentPly();
            this.panel.repaint();
            return true;
        }
        return false;
    }

    public T getCurrentPly() {
        return currentPly;
    }

    private void changeCurrentPly() {
        T next = setNextPly();
        this.currentPly = Objects.requireNonNull(next);
    }

    public abstract T setNextPly();

    private void checkGameEnd() {

    }
}
