package ui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Dimension2D;

import data.event.EventListener;
import data.event.EventObject;
import data.event.EventSource;
import data.event.impl.EventImpl;
import data.event.impl.EventObjectImpl;
import data.grid.Grid2D;

public class EventGridPanel extends GridPanel implements EventSource {

    private final EventObject eventObject = new EventObjectImpl();

    public EventGridPanel(Grid2D<?> grid) {
        super(grid);
    }

    @Override
    protected void tileClicked(int tileIndex) {
        TileClickedEvent event = new TileClickedEvent(this, tileIndex);
        eventObject.fireEvent(event);
    }

    @Override
    protected void tileSizeUpdated(Dimension2D tileSize) {
        Dimension2D oldSize = this.getTileSize();
        super.tileSizeUpdated(tileSize);
        TileSizeUpdatedEvent event = new TileSizeUpdatedEvent(this, oldSize, tileSize);
        eventObject.fireEvent(event);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        PanelRepaintEvent event = new PanelRepaintEvent(this, g);
        eventObject.fireEvent(event);
    }

    @Override
    public void setGridLineColor(Color gridColor) {
        Color oldColor = this.getGridColor();
        super.setGridLineColor(gridColor);
        if (!gridColor.equals(oldColor)) {
            GridColorChangedEvent event = new GridColorChangedEvent(this, oldColor, gridColor);
            eventObject.fireEvent(event);
        }
    }

    public void addEventListener(EventListener listener) {
        eventObject.addEventListener(listener);
    }

    public void removeEventListener(EventListener listener) {
        eventObject.removeEventListener(listener);
    }

    @Override
    public boolean hasEventListener(EventListener listener) {
        return eventObject.hasEventListener(listener);
    }

    public static class PanelRepaintEvent extends EventImpl {

        private final Graphics graphics;

        public PanelRepaintEvent(Object source, Graphics graphics) {
            super(source);
            this.graphics = graphics;
        }

        public Graphics getGraphics() {
            return graphics;
        }
    }

    public static class TileSizeUpdatedEvent extends EventImpl {

        private final Dimension2D oldSize;
        private final Dimension2D newSize;

        TileSizeUpdatedEvent(Object source, Dimension2D oldSize, Dimension2D newSize) {
            super(source);
            this.oldSize = oldSize;
            this.newSize = newSize;
        }

        public Dimension2D getOldSize() {
            return this.oldSize;
        }

        public Dimension2D getNewSize() {
            return this.newSize;
        }
    }

    public static class TileClickedEvent extends EventImpl {

        private final int tileIndex;

        TileClickedEvent(Object source, int tileIndex) {
            super(source);
            this.tileIndex = tileIndex;
        }

        public int getTileIndex() {
            return tileIndex;
        }
    }

    public static class GridColorChangedEvent extends EventImpl {

        private final Color oldColor;
        private final Color newColor;

        GridColorChangedEvent(Object source, Color oldColor, Color newColor) {
            super(source);
            this.oldColor = oldColor;
            this.newColor = newColor;
        }

        public Color getOldColor() {
            return oldColor;
        }

        public Color getNewColor() {
            return newColor;
        }
    }
}
