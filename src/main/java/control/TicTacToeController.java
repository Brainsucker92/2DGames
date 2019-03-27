package control;

import data.grid.Grid2D;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.Circle;
import ui.Cross;
import ui.EventGridPanel;
import ui.Token;

public class TicTacToeController extends Controller<Token> {

    private static Token CROSS = new Cross();
    private static Token CIRCLE = new Circle();

    public TicTacToeController(Grid2D<Token> dataGrid, EventGridPanel panel) {
        super(dataGrid, panel, CROSS);

        panel.addListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof EventGridPanel.TileClickedEvent) {
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
