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
    public void testGetCoordinates() {

        Grid2D<?> grid = new Grid2DImpl<>(4, 6);

        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(4, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getTileIndex(0, 6));

        assertEquals(0, grid.getTileIndex(0, 0));
        assertEquals(3, grid.getTileIndex(0, 3));
        assertEquals(7, grid.getTileIndex(1, 1));
        assertEquals(12, grid.getTileIndex(2, 0));
        assertEquals(23, grid.getTileIndex(3, 5));
    }
}
