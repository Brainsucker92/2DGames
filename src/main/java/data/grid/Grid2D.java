package data.grid;

public interface Grid2D<T> {
    T getValue(int rows, int columns);

    void setValue(int rowIndex, int columbIndex, T value);

    void setRowValues(int row, T... values);

    void setColumnValues(int columnIndex, T... values);

    int getNumRows();

    int getNumColumns();
}
