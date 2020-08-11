package control.controllers.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Dimension2D;
import java.util.Objects;

import control.controllers.game.impl.GameControllerImpl;
import data.grid.Grid2D;
import ui.Drawable;
import ui.drawings.Circle;
import ui.drawings.Cross;
import ui.panels.EventGridPanel;

public class TicTacToeController extends GameControllerImpl {

    private static final Drawable CROSS = new Cross();
    private static final Drawable CIRCLE = new Circle();

    private Drawable currentPly;
    private final Grid2D<Drawable> dataGrid;
    private final EventGridPanel panel;

    public TicTacToeController(Grid2D<Drawable> dataGrid, EventGridPanel panel) {
        super();

        this.dataGrid = dataGrid;
        this.panel = panel;
        this.currentPly = CROSS;

        if (panel != null) {
            panel.addEventListener(event -> {
                if (event instanceof EventGridPanel.PanelRepaintEvent) {
                    EventGridPanel.PanelRepaintEvent repaintEvent = (EventGridPanel.PanelRepaintEvent) event;
                    Graphics graphics = repaintEvent.getGraphics();
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
            });
        }
    }

    public Drawable setNextPly() {
        Drawable token = this.getCurrentPly();
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

    public Drawable getCurrentPly() {
        return currentPly;
    }

    private void changeCurrentPly() {
        Drawable next = setNextPly();
        this.currentPly = Objects.requireNonNull(next);
    }
}
