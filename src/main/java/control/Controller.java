package control;

import data.grid.Grid2D;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.EventGridPanel;

public class Controller {

    private Grid2D<Character> dataGrid;
    private EventGridPanel panel;
    private char currentPly = 'X';

    public Controller(Grid2D<Character> dataGrid, EventGridPanel panel) {
        this.dataGrid = dataGrid;
        this.panel = panel;

        this.panel.addListener(new EventListener() {
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

    private void placeToken(int tileIndex) {
        int[] coordinates = dataGrid.getCoordinates(tileIndex);
        int rowIndex = coordinates[0];
        int columnIndex = coordinates[1];
        if (dataGrid.isEmpty(rowIndex, columnIndex)) {
            dataGrid.setValue(rowIndex, columnIndex, currentPly);
            checkGameEnd();
            changeCurrentPly();
            dataGrid.print();
        }

    }

    private void changeCurrentPly() {
        switch (currentPly) {
            case 'X':
                currentPly = 'O';
                break;
            case 'O':
                currentPly = 'X';
                break;
        }
    }

    private void checkGameEnd() {

    }
}
