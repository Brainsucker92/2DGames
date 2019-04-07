package ui.panels;

import control.controllers.game.GameController;
import control.controllers.game.GameState;
import control.controllers.game.impl.GameControllerImpl;
import data.grid.event.EventListener;

import javax.swing.*;

public class ControlPanel extends JPanel {

    private JButton startButton;
    private JButton stopButton;

    private GameController gameController;

    private EventListener eventListener;


    public ControlPanel() {
        startButton = new JButton();
        stopButton = new JButton();

        startButton.setText("Start");
        stopButton.setText("Stop");

        this.add(startButton);
        this.add(stopButton);

        eventListener = event -> {
            if (event instanceof GameControllerImpl.GameStateChangedEvent) {
                GameControllerImpl.GameStateChangedEvent evt = ((GameControllerImpl.GameStateChangedEvent) event);
                GameState gameState = evt.getNewState();
                switch (gameState) {
                    case INITIALIZED:
                        startButton.setText("Start");
                        startButton.setEnabled(true);
                        stopButton.setEnabled(false);
                        break;
                    case PAUSED:
                        startButton.setText("Resume");
                        break;
                    case RUNNING:
                        startButton.setText("Pause");
                        stopButton.setEnabled(true);
                        break;
                    case STOPPED:
                        startButton.setEnabled(false);
                        stopButton.setText("Reset");
                        break;
                }
            }
        };

        startButton.addActionListener(e -> {
            GameState gameState = gameController.getGameState();
            switch (gameState) {
                case INITIALIZED:
                    gameController.start();
                    break;
                case RUNNING:
                    gameController.pause();
                    break;
                case STOPPED:
                    // fall through
                case PAUSED:
                    gameController.resume();
                    break;

            }
        });

        stopButton.addActionListener(e -> {
            GameState gameState = gameController.getGameState();
            switch (gameState) {
                case INITIALIZED:
                    // do nothing
                    break;
                case RUNNING:
                    //fall through
                case PAUSED:
                    gameController.stop();
                    break;
                case STOPPED:
                    gameController.reset();
            }
        });
    }

    public void setGameController(GameController gameController) {
        if (this.gameController != null) {
            gameController.removeEventListener(eventListener);
        }
        this.gameController = gameController;
        this.gameController.addEventListener(eventListener);
    }
}
