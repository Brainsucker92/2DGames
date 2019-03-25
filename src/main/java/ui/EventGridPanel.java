package ui;

import data.grid.Grid2D;
import data.grid.event.EventListener;
import data.grid.event.EventObject;
import data.grid.event.impl.EventImpl;
import data.grid.event.impl.EventObjectImpl;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class EventGridPanel extends GridPanel {

    private EventObject eventObject = new EventObjectImpl();

    public EventGridPanel(Grid2D<?> grid) {
        super(grid);
    }

    @Override
    protected void tileClicked(Point point) {
        super.tileClicked(point);
        TileClickedEvent event = new TileClickedEvent(this, point);
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
    public void setGridColor(Color gridColor) {
        Color oldColor = this.getGridColor();
        super.setGridColor(gridColor);
        if (!gridColor.equals(oldColor)) {
            GridColorChangedEvent event = new GridColorChangedEvent(this, oldColor, gridColor);
            eventObject.fireEvent(event);
        }
    }

    public void addListener(EventListener listener) {
        eventObject.addListener(listener);
    }

    public void removeListener(EventListener listener) {
        eventObject.removeListener(listener);
    }

    public class TileSizeUpdatedEvent extends EventImpl {

        private Dimension2D oldSize;
        private Dimension2D newSize;

        TileSizeUpdatedEvent(Object source, Dimension2D oldSize, Dimension2D newSize) {
            super(source);
            this.newSize = newSize;
        }

        public Dimension2D getOldSize() {
            return this.oldSize;
        }

        public Dimension2D getNewSize() {
            return this.newSize;
        }
    }

    public class TileClickedEvent extends EventImpl {

        private Point point;

        TileClickedEvent(Object source, Point point) {
            super(source);
            this.point = point;
        }

        public Point getPoint() {
            return this.point;
        }
    }

    public class GridColorChangedEvent extends EventImpl {

        private Color oldColor;
        private Color newColor;

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
