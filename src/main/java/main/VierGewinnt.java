package main;

import control.ControllerImpl;
import control.VierGewinntController;
import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.EventGridPanel;
import ui.Token;

import javax.swing.*;

public class VierGewinnt {

    public static void main(String[] args) {
        Grid2D<Token> dataGrid = new Grid2DImpl<>(9);
        EventGridPanel gridPanel = new EventGridPanel(dataGrid);

        ControllerImpl<Token> controller = new VierGewinntController(dataGrid, gridPanel);

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Vier Gewinnt");

        frame.setContentPane(gridPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
