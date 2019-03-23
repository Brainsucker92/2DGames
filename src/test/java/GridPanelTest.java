import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.GridPanel;

import javax.swing.*;

public class GridPanelTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        Grid2D<String> grid2D = new Grid2DImpl<>(8, 8);

        JPanel panel = new GridPanel(grid2D);

        frame.setContentPane(panel);

        frame.setVisible(true);
    }
}
