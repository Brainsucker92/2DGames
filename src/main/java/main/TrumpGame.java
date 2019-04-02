package main;

import control.TrumpGameController;
import data.GameData;
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

        try {
            GameData gameData = new GameData(executorService);
            JPanel panel = new JPanel();
            panel.setLayout(null);
            TrumpGameController controller = new TrumpGameController(executorService, panel, gameData);

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
                    logger.info("ExecutorService has been shutdown");
                }
            });

            frame.add(panel);
            frame.setLocation(500, 500);
            frame.pack();
            frame.setVisible(true);
            logger.info("Finished initializing application");
        } finally {
            executorService.shutdown();
        }
    }
}
