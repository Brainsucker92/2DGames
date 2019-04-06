package main;

import control.TrumpGameController;
import control.impl.GameControllerImpl;
import data.GameData;
import data.grid.event.Event;
import data.grid.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            panel.setLayout(null);

            controller = new TrumpGameController(executorService, panel, gameData);

            JTextField textField = new JTextField();
            textField.setEditable(false);

            controller.addEventListener(new EventListener() {
                @Override
                public void onEventFired(Event event) {
                    if (event instanceof GameControllerImpl.GameTickEvent) {
                        textField.setText(String.valueOf(TimeUnit.NANOSECONDS.toSeconds(controller.getElapsedTime())));
                    }
                }
            });

            JFrame frame = new JFrame();
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

            frame.add(panel);
            // frame.add(textField);
            frame.setLocation(500, 500);
            frame.pack();
            frame.setVisible(true);
            controller.start();
            logger.info("Finished initializing application");
        } finally {
            executorService.shutdown();
        }
    }
}
