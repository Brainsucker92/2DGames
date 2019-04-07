package main;

import control.GameController;
import control.TicTacToeController;
import data.grid.Grid2D;
import data.grid.impl.Grid2DImpl;
import ui.drawings.Token;
import ui.panels.EventGridPanel;

import javax.swing.*;

public class TicTacToe {

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.start();
    }

    private void start() {
        Grid2D<Token> dataGrid = new Grid2DImpl<>(3);
        EventGridPanel gridPanel = new EventGridPanel(dataGrid);

        GameController controller = new TicTacToeController(dataGrid, gridPanel);

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Tic Tac Toe");


        frame.setContentPane(gridPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
