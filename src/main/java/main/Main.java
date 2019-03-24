package main;

import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.GridPanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Grid2D<?> dataGrid = new Grid2DImpl<>(3);

        JFrame frame = new JFrame();
        frame.setTitle("Tic Tac Toe");

        GridPanel gridPanel = new GridPanel(dataGrid);

        frame.setContentPane(gridPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
