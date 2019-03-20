package data.grid.impl;

import data.grid.Grid2D;

/**
 *
 * @param <T> The type to store in the grid.
 */
public class Grid2DImpl<T> implements Grid2D<T> {

    private T[][] data;

    public Grid2DImpl(int size) {
        data = (T[][]) new Object[size][size];
    }

    public Grid2DImpl(int rows, int columns) {
        data = (T[][]) new Object[rows][columns];
    }

    @Override
    public T getValue(int rows, int columns) {
        return data[rows][columns];
    }

    @Override
    public void setValue(int rowIndex, int columnIndex, T value) {
        data[rowIndex][columnIndex] = value;
    }

    @Override
    public void setRowValues(int row, T... values) {
        if(values.length > this.getNumColumns()) {
            throw new IllegalArgumentException("Too many values given.");
        }
        for (int i = 0; i < values.length; i++) {
            this.setValue(row, i, values[i]);
        }
    }

    @Override
    public void setColumnValues(int columnIndex, T... values) {
        if (values.length > this.getNumRows()) {
            throw new IllegalArgumentException("Too many values given.");
        }
        for (int i = 0; i < values.length; i++) {
            this.setValue(i, columnIndex, values[i]);
        }
    }

    @Override
    public int getNumRows() {
        return data.length;
    }

    @Override
    public int getNumColumns() {
        return data[0].length;
    }
}
