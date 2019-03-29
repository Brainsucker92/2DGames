package main;

import control.TrumpGameController;
import data.GameData;
import data.ImageResource;
import data.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sprites.Sprite;
import sprites.SpriteAnimation;
import sprites.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrumpGame {

    private final Logger logger = LoggerFactory.getLogger(TrumpGame.class);


    private SpriteAnimation current;

    public static void main(String[] args) {
        TrumpGame game = new TrumpGame();
        game.start();
    }

    private void start() {
        logger.info("Initializing application.");
        ExecutorService executorService = Executors.newCachedThreadPool();

        GameData gameData = new GameData(executorService, Resources.TRUMP, Resources.COIN);
        JPanel panel = new JPanel();
        TrumpGameController controller = new TrumpGameController(executorService, panel, gameData);


        ImageResource bird = Resources.BIRD;
        ImageResource trump = Resources.TRUMP;
        trump.load();
        bird.load();
        SpriteSheet sheet = new SpriteSheet(bird.getData(), 3, 1);
        SpriteSheet trumpSheet = new SpriteSheet(trump.getData(), 6, 4);
        List<Sprite> row0 = trumpSheet.getRow(0);
        List<Sprite> row1 = trumpSheet.getRow(1);
        List<Sprite> row2 = trumpSheet.getRow(2);
        List<Sprite> row3 = trumpSheet.getRow(3);


        SpriteAnimation birdAnimation = new SpriteAnimation(sheet.getSprites().toArray(new Sprite[]{}));
        SpriteAnimation animation0 = new SpriteAnimation(row0.toArray(new Sprite[]{}));
        SpriteAnimation animation1 = new SpriteAnimation(row1.toArray(new Sprite[]{}));
        SpriteAnimation animation2 = new SpriteAnimation(row2.toArray(new Sprite[]{}));
        SpriteAnimation animation3 = new SpriteAnimation(row3.toArray(new Sprite[]{}));
        current = animation1;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                BufferedImage image = current.next();
                Graphics graphics = panel.getGraphics();
                panel.paintComponents(graphics);
                graphics.drawImage(image, 100, 100, 100, 100, null);
                graphics.drawImage(birdAnimation.next(), 200, 200, 100, 100, null);
            }
        };

        Timer timer = new Timer();

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
        panel.requestFocus();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        setAnimation(animation2);
                        current.reset();
                        break;
                    case KeyEvent.VK_A:
                        setAnimation(animation3);
                        current.reset();
                        break;
                    case KeyEvent.VK_S:
                        setAnimation(animation0);
                        current.reset();
                        break;
                    case KeyEvent.VK_D:
                        setAnimation(animation1);
                        current.reset();
                        break;
                }
            }
        });


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Closing window.");
                timerTask.cancel();
                timer.cancel();
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
        frame.setVisible(true);
        timer.schedule(timerTask, 0, 300);
        gameData.waitResourcesLoaded();
        logger.info("Finished initializing application");
    }

    private void setAnimation(SpriteAnimation animation) {
        current = animation;
    }
}
