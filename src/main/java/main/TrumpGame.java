package main;

import control.InputTypeController;
import control.TrumpGameController;
import control.impl.GameControllerImpl;
import data.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.panels.ControlPanel;
import ui.panels.MovementControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrumpGame {

    private final Logger logger = LoggerFactory.getLogger(TrumpGame.class);

    public static void main(String[] args) {
        TrumpGame game = new TrumpGame();
        game.start();
    }

    private void start() {
        logger.info("Initializing application.");
        ExecutorService executorService = Executors.newCachedThreadPool();
        TrumpGameController controller;
        try {
            GameData gameData = new GameData(executorService);
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(300, 300));
            // panel.setSize(300, 300);
            panel.setBackground(Color.WHITE);
            panel.setLayout(null);

            ControlPanel controlPanel = new ControlPanel();
            controlPanel.setSize(150, 50);
            controlPanel.setBackground(Color.GRAY);

            controller = new TrumpGameController(executorService, panel, gameData);
            controlPanel.setGameController(controller);
            InputTypeController inputTypeController = controller.getInputTypeController();

            MovementControlPanel movementControlPanel = new MovementControlPanel();
            movementControlPanel.setSize(150, 50);
            movementControlPanel.setBackground(Color.BLUE);
            movementControlPanel.setInputTypeController(inputTypeController);

            JTextField textField = new JTextField();
            textField.setEditable(false);

            controller.addEventListener(event -> {
                if (event instanceof GameControllerImpl.GameTickEvent) {
                    textField.setText(String.valueOf(controller.getElapsedTime(TimeUnit.NANOSECONDS)));
                }
            });

            JFrame frame = new JFrame();
            frame.setTitle("Trump Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(500, 500));

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    logger.info("Terminating ExecutorService...");
                    try {
                        executorService.awaitTermination(3, TimeUnit.SECONDS);
                    } catch (InterruptedException e1) {
                        logger.error("Interrupted termination of ExecutorService", e1);
                    }
                    executorService.shutdown();
                    controller.shutdown();
                    logger.info("ExecutorService has been shutdown");
                }
            });

            frame.setLayout(new FlowLayout());

            frame.add(panel);
            frame.add(controlPanel);
            frame.add(movementControlPanel);
            frame.add(textField);

            frame.setLocation(500, 500);
            frame.pack();
            frame.setVisible(true);
            logger.info("Finished initializing application");
        } finally {
            executorService.shutdown();
        }
    }
}
