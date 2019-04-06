package control;

import control.impl.GameControllerImpl;
import control.movement.*;
import data.GameData;
import data.ResourceLoader;
import data.grid.event.Event;
import data.grid.event.EventListener;
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
import java.util.function.Function;

public class TrumpGameController extends GameControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private Container container;
    private GameData gameData;
    private Timer timer;
    private Timer animationTimer;

    private Trump trump;
    private Coin coin;

    private List<GameEntity> gameEntityList;
    private List<AnimationEntity<?>> animationEntityList;
    private List<MoveableEntity> moveableEntityList;

    public TrumpGameController(ExecutorService executorService, Container container, GameData gameData) {
        this.executorService = executorService;
        this.container = container;
        this.gameData = gameData;
        init();
    }

    private void init() {

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(this.executorService);

        gameEntityList = new ArrayList<>();
        animationEntityList = new ArrayList<>();
        moveableEntityList = new ArrayList<>();
        animationTimer = new Timer();
        trump = new Trump();
        coin = new Coin();
        timer = new Timer();

        gameEntityList.add(trump);
        gameEntityList.add(coin);

        animationEntityList.add(trump);
        animationEntityList.add(coin);

        moveableEntityList.add(trump);

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

        for (GameEntity entity : gameEntityList) {
            GameComponent gameComponent = entity.getGameComponent();
            container.add(gameComponent);
        }
        container.setFocusable(true);
        container.requestFocus();


        TimerTask animationUpdateTask = new TimerTask() {
            @Override
            public void run() {
                for (AnimationEntity<?> animationEntity : animationEntityList) {
                    AnimationObject<?> animationObject = animationEntity.getAnimationObject();
                    animationObject.updateAnimation();
                }
            }
        };
        animationTimer.scheduleAtFixedRate(animationUpdateTask, 0, 300);
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

        this.addEventListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof GameStateChangedEvent) {
                    GameStateChangedEvent evt = ((GameStateChangedEvent) event);
                    GameState newState = evt.getNewState();
                    LOGGER.debug("Received GameStateChangedEvent: " + newState);
                    switch (newState) {
                        case RUNNING:
                            timer = new Timer();
                            timer.scheduleAtFixedRate(gameTicker, 0, 10);
                            break;

                        case STOPPED:
                            // fall through
                        case PAUSED:
                            timer.cancel();
                            break;
                    }
                }
            }
        });

    }

    private void gameTick(long delta, TimeUnit timeUnit) {
        for (MoveableEntity entity : moveableEntityList) {
            MoveableObject moveableObject = entity.getMoveableObject();
            moveableObject.move(delta, timeUnit);
            Point2D position = moveableObject.getPosition();
            Point pos = new Point();
            pos.setLocation(position);
            if (gameEntityList.contains(entity)) {
                GameEntity gameEntity = ((GameEntity) entity);
                GameComponent component = gameEntity.getGameComponent();
                component.setLocation(pos);
            }

        }
        long elapsedTime = this.getElapsedTime();
        MoveableObject moveableObject = trump.getMoveableObject();
        Function<Long, Double> movementFunction = x -> (((1 / (double) 400) * Math.pow(x * Math.pow(10, -9), 2)) + 20.0);
        Double movementSpeed = movementFunction.apply(elapsedTime);
        moveableObject.setMovementSpeed(movementSpeed);

        GameTickEvent event = new GameTickEvent(this, delta, timeUnit);
        this.fireEvent(event);
    }

    public void shutdown() {
        timer.cancel();
        animationTimer.cancel();
    }

    @Override
    public void checkGameEnd() {
    }
}
