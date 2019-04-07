package main;

import control.controllers.game.TrumpGameController;
import control.controllers.game.impl.GameControllerImpl;
import control.controllers.input.InputTypeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.panels.*;

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
            JPanel gamePanel = new JPanel();
            gamePanel.setPreferredSize(new Dimension(500, 500));
            gamePanel.setBackground(Color.WHITE);
            gamePanel.setLayout(null);

            JPanel configPanel = new JPanel();

            JPanel statisticsPanel = new JPanel();

            MovementSpeedDisplayPanel movementSpeedDisplayPanel = new MovementSpeedDisplayPanel();
            ElapsedTimeDisplayPanel elapsedTimeDisplayPanel = new ElapsedTimeDisplayPanel();
            AmountCoinsCollectedDisplayPanel amountCoinsCollectedDisplayPanel = new AmountCoinsCollectedDisplayPanel();

            // TODO
            //movementSpeedDisplayPanel.displayEntityValue();

            statisticsPanel.add(movementSpeedDisplayPanel);
            statisticsPanel.add(elapsedTimeDisplayPanel);
            statisticsPanel.add(amountCoinsCollectedDisplayPanel);

            ControlPanel controlPanel = new ControlPanel();
            controlPanel.setSize(150, 50);

            configPanel.add(controlPanel);

            controller = new TrumpGameController(executorService, gamePanel);
            controlPanel.setGameController(controller);
            InputTypeController inputTypeController = controller.getInputTypeController();

            MovementControlPanel movementControlPanel = new MovementControlPanel();
            movementControlPanel.setSize(150, 50);
            movementControlPanel.setInputTypeController(inputTypeController);

            configPanel.add(movementControlPanel);

            controller.addEventListener(event -> {
                if (event instanceof GameControllerImpl.GameTickEvent) {
                    elapsedTimeDisplayPanel.displayEntityValue(controller);
                } else if (event instanceof TrumpGameController.CoinCollectedEvent) {
                    amountCoinsCollectedDisplayPanel.displayEntityValue(controller);
                }
            });

            JFrame frame = new JFrame();
            frame.setTitle("Trump on the hunt");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setPreferredSize(new Dimension(500, 500));

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

            frame.add(gamePanel);
            frame.add(configPanel);
            frame.add(statisticsPanel);

            frame.setLocation(500, 500);
            frame.pack();
            frame.setVisible(true);
            logger.info("Finished initializing application");
        } finally {
            executorService.shutdown();
        }
    }
}
