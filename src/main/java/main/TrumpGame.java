package main;

import control.TrumpGameController;
import data.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
            TrumpGameController controller = new TrumpGameController(executorService, panel, gameData);


            panel.setFocusable(true);
            panel.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    logger.debug("Panel gained focus");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    logger.debug("Panel lost focus");
                }
            });

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

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

            frame.setContentPane(panel);
            frame.pack();
            frame.setVisible(true);
            gameData.waitResourcesLoaded();
            logger.info("Finished initializing application");
        } finally {
            executorService.shutdown();
        }
    }
}
