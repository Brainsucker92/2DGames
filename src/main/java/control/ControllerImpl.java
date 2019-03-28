package control;

import data.grid.Grid2D;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.Drawable;
import ui.EventGridPanel;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.Objects;

public abstract class ControllerImpl<T extends Drawable> implements Controller<T> {

    protected Grid2D<T> dataGrid;
    protected EventGridPanel panel;
    private T currentPly;

    public ControllerImpl(Grid2D<T> dataGrid, EventGridPanel panel, T startToken) {
        this.dataGrid = dataGrid;
        this.panel = panel;
        this.currentPly = startToken;

        this.panel.addListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof EventGridPanel.PanelRepaintEvent) {
                    EventGridPanel.PanelRepaintEvent evnt = (EventGridPanel.PanelRepaintEvent) event;
                    Graphics graphics = evnt.getGraphics();
                    Dimension2D tileSize = panel.getTileSize();
                    for (int y = 0; y < dataGrid.getNumRows(); y++) {
                        for (int x = 0; x < dataGrid.getNumColumns(); x++) {
                            if (dataGrid.isEmpty(y, x)) {
                                continue;
                            }
                            Point position = panel.getTilePosition(y, x);
                            Drawable value = dataGrid.getValue(y, x);
                            value.draw(graphics, position, tileSize);
                        }

                    }
                }
            }
        });
    }

    public boolean placeToken(int tileIndex) {
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
}
