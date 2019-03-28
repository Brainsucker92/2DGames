package control;

import data.grid.Grid2D;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.BlueToken;
import ui.EventGridPanel;
import ui.RedToken;
import ui.Token;

public class VierGewinntController extends ControllerImpl<Token> {

    private static final Token BLUE = new BlueToken();
    private static final Token RED = new RedToken();

    public VierGewinntController(Grid2D<Token> dataGrid, EventGridPanel panel) {
        super(dataGrid, panel, BLUE);

        panel.addListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof EventGridPanel.TileClickedEvent) {
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

    @Override
    public Token setNextPly() {
        Token currentPly = this.getCurrentPly();
        if (currentPly instanceof RedToken) {
            return BLUE;
        } else if (currentPly instanceof BlueToken) {
            return RED;
        }
        throw new IllegalStateException();
    }

    @Override
    public void checkGameEnd() {

    }

    @Override
    public void endGame() {

    }
}
