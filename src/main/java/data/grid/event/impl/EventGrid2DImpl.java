package data.grid.event.impl;

import data.grid.Grid2D;
import data.grid.event.EventGrid2D;
import data.grid.impl.Grid2DImpl;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

@SuppressWarnings("unused")
public class EventGrid2DImpl<T> extends Grid2DImpl<T> implements EventGrid2D<T> {

    private List<GridListener> listeners = new ArrayList<>();

    public EventGrid2DImpl(int size) {
        super(size);
    }

    public EventGrid2DImpl(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public void setValue(int rowIndex, int columnIndex, T value) {
        T oldValue = this.getValue(rowIndex, columnIndex);
        super.setValue(rowIndex, columnIndex, value);
        GridValueChangedEvent event = new GridValueChangedEvent(this, oldValue, value);
        fireEvent(event);
    }

    @Override
    public void addListener(GridListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(GridListener listener) {
        listeners.remove(listener);
    }

    protected void fireEvent(GridEvent event) {
        for (GridListener listener : this.listeners) {
            listener.onEventFired(event);
        }
    }

    public interface GridEvent {
        Grid2D getEventSource();
    }

    class GridEventImpl implements GridEvent {

        private Grid2D source;

        GridEventImpl(Grid2D source) {
            this.source = source;
        }

        @Override
        public Grid2D getEventSource() {
            return source;
        }
    }

    public class GridValueChangedEvent extends GridEventImpl {
        private T oldValue;
        private T newValue;

        GridValueChangedEvent(Grid2D source, T oldValue, T newValue) {
            super(source);
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public T getOldValue() {
            return oldValue;
        }

        public T getNewValue() {
            return newValue;
        }
    }

    public interface GridListener extends EventListener {
        void onEventFired(GridEvent event);

    }
}
