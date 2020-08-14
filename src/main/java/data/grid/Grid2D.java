package data.grid;

import util.Printable;

public interface Grid2D<T> extends Iterable<T>, Printable {
    T getValue(int rowIndex, int columnIndex);

    void setValue(int rowIndex, int columnIndex, T value);

    void setRowValues(int rowIndex, T... values);

    void setColumnValues(int columnIndex, T... values);

    int getNumRows();

    int getNumColumns();

    int getTotalSize();

    int getTileIndex(int rowIndex, int columnIndex);

    int[] getCoordinates(int tileIndex);

    boolean isEmpty(int rowIndex, int columnIndex);
}
