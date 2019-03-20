package data.grid.event;

import data.grid.Grid2D;
import data.grid.event.impl.EventGrid2DImpl;

public interface EventGrid2D<T> extends Grid2D<T> {

    void addListener(EventGrid2DImpl.GridListener listener);

    void removeListener(EventGrid2DImpl.GridListener listener);
}
