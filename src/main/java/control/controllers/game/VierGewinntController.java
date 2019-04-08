package control.controllers.game;

import control.controllers.game.impl.GameControllerImpl;
import data.event.Event;
import data.event.EventListener;
import data.grid.Grid2D;
import ui.Drawable;
import ui.drawings.BlueToken;
import ui.drawings.RedToken;
import ui.drawings.Token;
import ui.panels.EventGridPanel;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.Objects;

public class VierGewinntController extends GameControllerImpl {

    private static final Token BLUE = new BlueToken();
    private static final Token RED = new RedToken();

    private Token currentPly;
    private Grid2D<Token> dataGrid;
    private EventGridPanel panel;

    public VierGewinntController(Grid2D<Token> dataGrid, EventGridPanel panel) {
        super();

        this.panel = panel;
        this.currentPly = RED;
        this.dataGrid = dataGrid;

        if (panel != null) {
            panel.addEventListener(new EventListener() {
                @Override
                public void onEventFired(Event event) {
                    if (event instanceof EventGridPanel.PanelRepaintEvent) {
                        EventGridPanel.PanelRepaintEvent evnt = (EventGridPanel.PanelRepaintEvent) event;
                        Graphics graphics = evnt.getGraphics();
                        Graphics2D g2d = ((Graphics2D) graphics);
                        Dimension2D tileSize = panel.getTileSize();
                        for (int y = 0; y < dataGrid.getNumRows(); y++) {
                            for (int x = 0; x < dataGrid.getNumColumns(); x++) {
                                if (dataGrid.isEmpty(y, x)) {
                                    continue;
                                }
                                Point position = panel.getTilePosition(y, x);
                                Drawable drawable = dataGrid.getValue(y, x);
                                drawable.draw(g2d, position, tileSize);
                            }

                        }
                    } else if (event instanceof EventGridPanel.TileClickedEvent) {
                        EventGridPanel.TileClickedEvent evnt = (EventGridPanel.TileClickedEvent) event;
                        int tileIndex = evnt.getTileIndex();
                        int[] coordinates = dataGrid.getCoordinates(tileIndex);
                        int x = coordinates[0];
                        for (int y = dataGrid.getNumRows() - 1; y > 0; y--) {
                            if (dataGrid.isEmpty(y, x)) {
                                int index = dataGrid.getTileIndex(y, x);
                                placeToken(index);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    public Token setNextPly() {
        Token currentPly = this.getCurrentPly();
        if (currentPly instanceof RedToken) {
            return BLUE;
        } else if (currentPly instanceof BlueToken) {
            return RED;
        }
        throw new IllegalStateException();
    }

    public void checkGameEnd() {

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

    public Token getCurrentPly() {
        return currentPly;
    }

    private void changeCurrentPly() {
        Token next = setNextPly();
        this.currentPly = Objects.requireNonNull(next);
    }
}
