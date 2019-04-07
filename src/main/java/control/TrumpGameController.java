package control;

import control.impl.GameControllerImpl;
import control.movement.*;
import control.movement.impl.MovableObjectImpl;
import data.GameData;
import data.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.AnimationObject;
import ui.GameComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class TrumpGameController extends GameControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private Container container;
    private GameData gameData;
    private Timer timer;
    private Timer animationTimer;

    private Trump trump;
    private Coin coin;

    private int coinsCollected;

    private List<GameEntity> gameEntityList;
    private List<AnimationEntity<?>> animationEntityList;
    private List<MovableEntity> movableEntityList;

    public TrumpGameController(ExecutorService executorService, Container container, GameData gameData) {
        this.executorService = executorService;
        this.container = container;
        this.gameData = gameData;
        init();
    }

    private void init() {

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(this.executorService);

        coinsCollected = 0;
        gameEntityList = new ArrayList<>();
        animationEntityList = new ArrayList<>();
        movableEntityList = new ArrayList<>();
        animationTimer = new Timer();
        trump = new Trump();
        coin = new Coin();
        container.setFocusable(true);

        gameEntityList.add(trump);
        gameEntityList.add(coin);

        animationEntityList.add(trump);
        animationEntityList.add(coin);

        movableEntityList.add(trump);

        GameComponent trumpComponent = trump.getGameComponent();
        trumpComponent.setSize(100, 100);

        GameComponent coinGameComponent = coin.getGameComponent();
        coinGameComponent.setSize(20, 20);
        coinGameComponent.setLocation(150, 150);

        MovableObject trumpMovableObject = trump.getMovableObject();
        MovementController movementController = new KeyInputController(trumpMovableObject, KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A);
        MovementController controller = new MouseMotionController(trumpMovableObject);
        MovementController mouseController = new MouseClickController(trumpMovableObject);

        InputTypeController inputTypeController = this.getInputTypeController();
        inputTypeController.setMovableObject(trumpMovableObject);
        inputTypeController.registerInputController(InputType.KEY, movementController);
        inputTypeController.registerInputController(InputType.MOUSE_CLICK, mouseController);
        inputTypeController.registerInputController(InputType.MOUSE_MOTION, controller);


        for (GameEntity entity : gameEntityList) {
            GameComponent gameComponent = entity.getGameComponent();
            container.add(gameComponent);
        }

        TimerTask animationUpdateTask = getAnimationUpdateTask();
        animationTimer.scheduleAtFixedRate(animationUpdateTask, 0, 300);

        this.addEventListener(event -> {
            if (event instanceof GameStateChangedEvent) {
                GameStateChangedEvent evt = ((GameStateChangedEvent) event);
                GameState newState = evt.getNewState();
                LOGGER.debug("Received GameStateChangedEvent: " + newState);
                switch (newState) {
                    case INITIALIZED:
                        break;
                    case RUNNING:
                        timer = new Timer();
                        TimerTask gameTicker = getGameTicker();
                        timer.scheduleAtFixedRate(gameTicker, 0, 10);
                        container.requestFocus();
                        break;

                    case STOPPED:
                        // fall through
                    case PAUSED:
                        timer.cancel();
                        break;
                }
            }
        });
        trumpMovableObject.addEventListener(event -> {
            if (event instanceof MovableObjectImpl.MovementControllerChangedEvent) {
                MovableObjectImpl.MovementControllerChangedEvent evt = ((MovableObjectImpl.MovementControllerChangedEvent) event);
                MovementController oldController = evt.getOldController();
                MovementController newController = evt.getNewController();
                if (oldController != null) {
                    oldController.unregister(container);
                }
                if (newController != null) {
                    newController.register(container);
                }

                if (newController instanceof KeyInputController) {
                    container.requestFocus();
                }
            }
        });
        inputTypeController.useController(InputType.KEY);
    }

    public void shutdown() {
        if (timer != null) {
            timer.cancel();
        }
        if (animationTimer != null) {
            animationTimer.cancel();
        }
    }

    @Override
    public void checkGameEnd() {
        // TODO
    }

    private void gameTick(long delta, TimeUnit timeUnit) {
        for (MovableEntity entity : movableEntityList) {
            MovableObject movableObject = entity.getMovableObject();
            movableObject.move(delta, timeUnit);
            Point2D position = movableObject.getPosition();
            Point pos = new Point();
            pos.setLocation(position);
        }

        long elapsedTime = this.getElapsedTime();
        MovableObject movableObject = trump.getMovableObject();
        BiFunction<Long, Integer, Double> movementFunction = (x, y) -> (((1 / (double) 400) * Math.pow(x * Math.pow(10, -9), 2)) + 0.25 * y + 20.0);
        Double movementSpeed = movementFunction.apply(elapsedTime, coinsCollected);
        movableObject.setMovementSpeed(movementSpeed);

        GameTickEvent event = new GameTickEvent(this, delta, timeUnit);
        this.fireEvent(event);
    }

    private TimerTask getGameTicker() {
        return new TimerTask() {

            private long prevTime = System.nanoTime();

            @Override
            public void run() {
                long currentTime = System.nanoTime();
                long diff = currentTime - prevTime;
                prevTime = currentTime;
                gameTick(diff, TimeUnit.NANOSECONDS);
            }
        };
    }

    private TimerTask getAnimationUpdateTask() {
        return new TimerTask() {
            @Override
            public void run() {
                for (AnimationEntity<?> animationEntity : animationEntityList) {
                    AnimationObject<?> animationObject = animationEntity.getAnimationObject();
                    animationObject.updateAnimation();
                }
            }
        };
    }
}
