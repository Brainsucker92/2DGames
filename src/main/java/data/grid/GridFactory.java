package data.grid;

import data.grid.event.EventGrid2D;
import data.grid.event.impl.EventGrid2DImpl;
import data.grid.impl.Grid2DImpl;

public class GridFactory<T> {

    private Class<T> type;

    GridFactory(Class<T> type) {
        this.type = type;
    }

    public static <R> GridFactory<R> getFactory(Class<R> cls) {
        return new GridFactory<R>(cls);
    }

    public Grid2D<T> newGrid(int size) {

        return new Grid2DImpl<T>(size);
    }

    public Grid2D<T> newGrid(int rows, int columns) {
        return new Grid2DImpl<T>(rows, columns);
    }

    public EventGrid2D<T> newEventGrid(int size) {
        return new EventGrid2DImpl<T>(size);
    }

    public EventGrid2D<T> newEventGrid(int rows, int columns) {
        return new EventGrid2DImpl<>(rows, columns);
    }
}
