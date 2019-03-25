package main;

import control.Controller;
import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.EventGridPanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Grid2D<Character> dataGrid = new Grid2DImpl<>(3);
        EventGridPanel gridPanel = new EventGridPanel(dataGrid);

        Controller controller = new Controller(dataGrid, gridPanel);

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Tic Tac Toe");


        frame.setContentPane(gridPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
