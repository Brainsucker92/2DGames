import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GridUnitTest {


    @Test
    public void testConstructor_1() {
        Grid2D<?> grid = new Grid2DImpl<>(3);

        assertEquals(3, grid.getNumColumns());
        assertEquals(3, grid.getNumRows());
    }

    @Test
    public void testConstructor_2() {
        Grid2D<?> grid = new Grid2DImpl<>(7, 13);

        assertEquals(7, grid.getNumRows());
        assertEquals(13, grid.getNumColumns());
    }

    @Test
    public void testSetValue() {
        Grid2D<String> grid2D = new Grid2DImpl<>(4, 5);
        String testValue1 = "This is a value for grid testing";

        grid2D.setValue(2, 3, testValue1);

        String value = grid2D.getValue(2, 3);
        assertEquals(testValue1, value);

        String testValue2 = "This is another value";

        grid2D.setValue(1, 3, testValue2);

        String value2 = grid2D.getValue(1, 3);

        assertEquals(testValue2, value2);
    }

    @Test
    public void testGetTileIndex() {

        Grid2D<?> grid = new Grid2DImpl<>(4, 6);

        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(4, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(0, 6));

        for (int i = 1; i < 25; i++) {
            for (int j = 1; j < 25; j++) {
                Grid2D<?> g = new Grid2DImpl<>(i, j);
                for (int y = 0; y < g.getNumRows() - 1; y++) {
                    for (int x = 0; x < g.getNumColumns() - 1; x++) {
                        int tileIndex = g.getTileIndex(y, x);
                        assertEquals(y * g.getNumColumns() + x, tileIndex, String.format("Value mismatch for coordinates (%s|%s). (%s|%s)", x, y, g.getNumRows(), g.getNumColumns()));
                    }
                }
            }
        }
    }
}
