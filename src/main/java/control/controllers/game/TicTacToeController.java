package control.controllers.game;

import control.controllers.game.impl.GameControllerImpl;
import data.grid.Grid2D;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.Drawable;
import ui.drawings.Circle;
import ui.drawings.Cross;
import ui.drawings.Token;
import ui.panels.EventGridPanel;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.Objects;

public class TicTacToeController extends GameControllerImpl {

    private static Token CROSS = new Cross();
    private static Token CIRCLE = new Circle();

    private Token currentPly;
    private Grid2D<Token> dataGrid;
    private EventGridPanel panel;

    public TicTacToeController(Grid2D<Token> dataGrid, EventGridPanel panel) {
        super();

        this.dataGrid = dataGrid;
        this.panel = panel;
        this.currentPly = CROSS;

        if (panel != null) {
            panel.addListener(new EventListener() {
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
                        placeToken(tileIndex);
                    }
                }
            });
        }
    }

    public Token setNextPly() {
        Token token = this.getCurrentPly();
        if (token instanceof Circle) {
            return CROSS;
        } else if (token instanceof Cross) {
            return CIRCLE;
        }
        throw new IllegalStateException();
    }

    @Override
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
