package data.grid.event;

import data.event.EventListener;
import data.grid.Grid2D;

public interface EventGrid2D<T> extends Grid2D<T> {

    void addListener(EventListener listener);

    void removeListener(EventListener listener);
}
