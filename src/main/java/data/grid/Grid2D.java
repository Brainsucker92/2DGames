package data.grid;

public interface Grid2D<T> extends Iterable<T> {
    T getValue(int rows, int columns);

    void setValue(int rowIndex, int columnIndex, T value);

    void setRowValues(int row, T... values);

    void setColumnValues(int columnIndex, T... values);

    int getNumRows();

    int getNumColumns();

    int getTotalSize();

    int getTileIndex(int rowIndex, int columnIndex);

    int[] getCoordinates(int tileIndex);
}
