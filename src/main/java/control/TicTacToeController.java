package control;

import data.grid.Grid2D;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.Circle;
import ui.Cross;
import ui.EventGridPanel;
import ui.Token;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class TicTacToeController extends Controller<Token> {

    private static Token CROSS = new Cross();
    private static Token CIRCLE = new Circle();

    public TicTacToeController(Grid2D<Token> dataGrid, EventGridPanel panel) {
        super(dataGrid, panel, CROSS);

        panel.addListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof EventGridPanel.PanelRepaintEvent) {
                    dataGrid.print();
                    EventGridPanel.PanelRepaintEvent evnt = (EventGridPanel.PanelRepaintEvent) event;
                    Graphics graphics = evnt.getGraphics();
                    Dimension2D tileSize = panel.getTileSize();
                    for (int y = 0; y < dataGrid.getNumRows(); y++) {
                        for (int x = 0; x < dataGrid.getNumColumns(); x++) {
                            Point position = new Point((int) (x * tileSize.getWidth()), (int) (y * tileSize.getHeight()));
                            Token value = dataGrid.getValue(y, x);
                            if (dataGrid.isEmpty(y, x)) {
                                continue;
                            }
                            value.draw(graphics, position, tileSize);
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

    @Override
    public Token setNextPly() {
        Token token = this.getCurrentPly();
        if (token instanceof Circle) {
            return CROSS;
        } else if (token instanceof Cross) {
            return CIRCLE;
        }
        throw new IllegalStateException();
    }
}
