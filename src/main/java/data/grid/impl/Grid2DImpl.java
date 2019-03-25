package data.grid.impl;

import data.grid.Grid2D;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @param <T> The type to store in the grid.
 */
public class Grid2DImpl<T> implements Grid2D<T> {

    private T[][] data;
    private int totalSize;

    public Grid2DImpl(int size) {
        data = (T[][]) new Object[size][size];
        totalSize = size * size;
    }

    public Grid2DImpl(int rows, int columns) {
        data = (T[][]) new Object[rows][columns];
        totalSize = rows * columns;
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
        if (values.length > this.getNumColumns()) {
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

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    @Override
    public int getTileIndex(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= this.getNumRows()) {
            throw new IndexOutOfBoundsException(String.format("Expected rowIndex between 0 and %s. Got %s", this.getNumRows() - 1, rowIndex));
        }

        if (columnIndex < 0 || columnIndex >= this.getNumColumns()) {
            throw new IndexOutOfBoundsException(String.format("Expected columnIndex between 0 and %s. Got %s", this.getNumColumns() - 1, columnIndex));
        }

        return rowIndex * getNumColumns() + columnIndex;
    }

    @Override
    public int[] getCoordinates(int tileIndex) {
        if (tileIndex > this.getTotalSize() - 1) {
            throw new IndexOutOfBoundsException();
        }
        int x = tileIndex % getNumColumns();
        int y = tileIndex / getNumColumns();

        return new int[]{x, y};
    }

    @Override
    public boolean isEmpty(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex] == null;
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(data).flatMap(Arrays::stream).iterator();

    }
}
