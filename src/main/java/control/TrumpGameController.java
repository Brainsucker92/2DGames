package control;

import data.GameData;
import data.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.GameComponent;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class TrumpGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private Container container;
    private GameData gameData;
    private Timer timer;

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
        trump = new Trump();
        coin = new Coin();

        GameComponent trumpComponent = trump.getGameComponent();
        trumpComponent.setSize(100, 100);
        GameComponent coinGameComponent = coin.getGameComponent();
        coinGameComponent.setSize(100, 1000);

        container.add(trumpComponent);
        container.add(coinGameComponent);
        container.addKeyListener(trump);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                trump.updateAnimation();
                coin.updateAnimation();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 300);

    }
}
