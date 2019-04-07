package data.grid.event.impl;

import data.event.EventListener;
import data.event.EventObject;
import data.event.impl.EventImpl;
import data.event.impl.EventObjectImpl;
import data.grid.Grid2D;
import data.grid.event.EventGrid2D;
import data.grid.impl.Grid2DImpl;

@SuppressWarnings("unused")
public class EventGrid2DImpl<T> extends Grid2DImpl<T> implements EventGrid2D<T> {

    private EventObject eventObject = new EventObjectImpl();

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
        GridValueChangedEvent event = new GridValueChangedEvent(this, rowIndex, columnIndex, oldValue, value);
        eventObject.fireEvent(event);
    }

    @Override
    public void addListener(EventListener listener) {
        eventObject.addListener(listener);
    }

    @Override
    public void removeListener(EventListener listener) {
        eventObject.removeListener(listener);
    }


    public class GridValueChangedEvent extends EventImpl {
        private T oldValue;
        private T newValue;
        private int rowIndex;
        private int columnIndex;

        GridValueChangedEvent(Grid2D source, int rowIndex, int columnIndex, T oldValue, T newValue) {
            super(source);
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public T getOldValue() {
            return oldValue;
        }

        public T getNewValue() {
            return newValue;
        }
    }
}
