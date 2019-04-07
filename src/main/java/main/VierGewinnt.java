package main;

import control.GameController;
import control.VierGewinntController;
import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.drawings.Token;
import ui.panels.EventGridPanel;

import javax.swing.*;

public class VierGewinnt {

    public static void main(String[] args) {
        VierGewinnt vierGewinnt = new VierGewinnt();
        vierGewinnt.start();
    }

    private void start() {
        Grid2D<Token> dataGrid = new Grid2DImpl<>(9);
        EventGridPanel gridPanel = new EventGridPanel(dataGrid);

        GameController controller = new VierGewinntController(dataGrid, gridPanel);

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Vier Gewinnt");

        frame.setContentPane(gridPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
