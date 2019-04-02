package control;

import data.GameData;
import data.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.GameComponent;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TrumpGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private Container container;
    private GameData gameData;
    private Timer timer;
    private Timer animationTimer;

    private Trump trump;
    private Coin coin;

    public TrumpGameController(ExecutorService executorService, Container container, GameData gameData) {
        this.executorService = executorService;
        this.container = container;
        this.gameData = gameData;
        init();
    }

    private void init() {

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(this.executorService);

        timer = new Timer();
        animationTimer = new Timer();
        trump = new Trump();
        coin = new Coin();

        GameComponent trumpComponent = trump.getGameComponent();
        trumpComponent.setSize(100, 100);
        GameComponent coinGameComponent = coin.getGameComponent();
        coinGameComponent.setSize(20, 20);
        coinGameComponent.setLocation(150, 150);

        trump.getMoveableObject().setMovementSpeed(45.0);

        container.add(trumpComponent);
        container.add(coinGameComponent);
        KeyListener trumpKeyListener = trump.getKeyListener();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                trump.updateAnimation();
                coin.updateAnimation();
            }
        };
        animationTimer.scheduleAtFixedRate(task, 0, 300);
        TimerTask gameTicker = new TimerTask() {
            @Override
            public void run() {
                long prevTime = System.nanoTime();
                while (true) {
                    long currentTime = System.nanoTime();
                    long diff = currentTime - prevTime;
                    prevTime = currentTime;
                    gameTick(diff, TimeUnit.NANOSECONDS);
                }
            }
        };
        timer.scheduleAtFixedRate(gameTicker, 0, 10);
    }

    private void gameTick(long delta, TimeUnit timeUnit) {
        trump.gameTick(delta, timeUnit);
    }

    public void shutdown() {
        timer.cancel();
    }
}
