package main;

import control.ControllerImpl;
import control.TicTacToeController;
import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.EventGridPanel;
import ui.Token;

import javax.swing.*;

public class TicTacToe {

    public static void main(String[] args) {
        Grid2D<Token> dataGrid = new Grid2DImpl<>(3);
        EventGridPanel gridPanel = new EventGridPanel(dataGrid);

        ControllerImpl<Token> controller = new TicTacToeController(dataGrid, gridPanel);

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Tic Tac Toe");


        frame.setContentPane(gridPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
