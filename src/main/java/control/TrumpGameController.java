package control;

import control.movement.*;
import data.GameData;
import data.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.GameComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class TrumpGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private Container container;
    private GameData gameData;
    private Timer timer;
    private Timer animationTimer;

    private Trump trump;
    private Coin coin;

    private long ellapsedTime = 0;

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

        MoveableObject trumpMoveableObject = trump.getMoveableObject();
        MovementController movementController = new KeyInputController(trumpMoveableObject, KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A);
        MovementController controller = new MouseMotionController(trumpMoveableObject);
        MovementController mouseController = new MouseClickController(trumpMoveableObject);
        trumpMoveableObject.setMovementController(movementController);
        trumpMoveableObject.setMovementSpeed(5.0);
        trumpMoveableObject.getMovementController().register(this.container);

        container.add(trumpComponent);
        container.add(coinGameComponent);
        container.setFocusable(true);
        container.requestFocus();

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
        ellapsedTime += timeUnit.convert(delta, TimeUnit.NANOSECONDS);
        MoveableObject moveableObject = trump.getMoveableObject();
        Function<Long, Double> movementFunction = x -> (((1 / (double) 400) * Math.pow(x * Math.pow(10, -9), 2)) + 20.0);
        Double movementSpeed = movementFunction.apply(ellapsedTime);
        moveableObject.setMovementSpeed(movementSpeed);
    }

    public void shutdown() {
        timer.cancel();
        animationTimer.cancel();
    }
}
