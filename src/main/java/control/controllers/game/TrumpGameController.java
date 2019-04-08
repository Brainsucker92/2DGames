package control.controllers.game;

import control.Coin;
import control.Obama;
import control.Trump;
import control.controllers.game.impl.GameControllerImpl;
import control.controllers.input.InputType;
import control.controllers.input.InputTypeController;
import control.entities.AnimationEntity;
import control.entities.GameEntity;
import control.entities.MovableEntity;
import control.movement.*;
import control.movement.impl.MovableObjectImpl;
import data.event.impl.EventImpl;
import data.resources.MP3SoundResource;
import data.resources.ResourceLoader;
import data.resources.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.animations.AnimationObject;
import ui.components.GameComponent;
import ui.sprites.SpriteAnimation;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class TrumpGameController extends GameControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private Container container;
    private Timer timer;
    private Timer animationTimer;
    private Random random;

    private Trump trump;
    private Obama obama;
    private Coin coin;

    private int coinsCollected;

    private List<GameEntity> gameEntityList;
    private List<AnimationEntity<?>> animationEntityList;
    private List<MovableEntity> movableEntityList;

    public TrumpGameController(ExecutorService executorService, Container container) {
        this.executorService = executorService;
        this.container = container;
        init();
    }

    private void init() {

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(this.executorService);

        Future<?> future = resourceLoader.loadResources(List.of(Resources.CASH, Resources.CELEBRATE, Resources.EARTH_WIND_FIRE));

        coinsCollected = 0;
        gameEntityList = new ArrayList<>();
        animationEntityList = new ArrayList<>();
        movableEntityList = new ArrayList<>();
        animationTimer = new Timer();
        random = new Random();
        trump = new Trump();
        coin = new Coin();
        obama = new Obama();
        container.setFocusable(true);

        gameEntityList.add(trump);
        gameEntityList.add(coin);
        gameEntityList.add(obama);

        animationEntityList.add(trump);
        animationEntityList.add(coin);
        animationEntityList.add(obama);

        movableEntityList.add(trump);

        GameComponent trumpComponent = trump.getGameComponent();
        trumpComponent.setSize(100, 100);

        GameComponent coinGameComponent = coin.getGameComponent();
        coinGameComponent.setSize(30, 30);
        coinGameComponent.setVisible(false);

        GameComponent obamaGameComponent = obama.getGameComponent();
        obamaGameComponent.setVisible(false);

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
                        trumpComponent.setVisible(true);
                        obamaGameComponent.setVisible(false);
                        trumpMovableObject.setPosition(0, 0);
                        coinsCollected = 0;
                        container.requestFocus();
                        break;
                    case RUNNING:
                        timer = new Timer();
                        TimerTask gameTicker = getGameTicker();
                        timer.scheduleAtFixedRate(gameTicker, 0, 10);
                        container.requestFocus();
                        coinGameComponent.setVisible(true);
                        repositionCoin();
                        break;

                    case STOPPED:
                        trumpComponent.setVisible(false);
                        coinGameComponent.setVisible(false);
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

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (timer != null) {
            timer.cancel();
        }
        if (animationTimer != null) {
            animationTimer.cancel();
        }
    }

    public int getAmountCoinsCollected() {
        return coinsCollected;
    }

    public Trump getTrump() {
        return trump;
    }

    @Override
    public void checkGameEnd() {
        GameComponent trumpGameComponent = this.trump.getGameComponent();
        Point trumpTopLeftPoint = trumpGameComponent.getLocation();
        Dimension trumpGameComponentSize = trumpGameComponent.getSize();
        Point trumpBottomRightPoint = trumpGameComponent.getLocation();
        trumpBottomRightPoint.translate(trumpGameComponentSize.width, trumpGameComponentSize.height);

        Rectangle containerBounds = this.container.getBounds();
        containerBounds.setLocation(0, 0);

        if (!containerBounds.contains(trumpTopLeftPoint) || !containerBounds.contains(trumpBottomRightPoint)) {
            // end game.
            this.endGame();
            displayObama();
        }
    }

    private void gameTick(long delta, TimeUnit timeUnit) {
        updateMovableObjectLocations(delta, timeUnit);
        checkCoinCollected();
        calculateTrumpMovementSpeed();
        checkGameEnd();

        GameTickEvent event = new GameTickEvent(this, delta, timeUnit);
        this.fireEvent(event);
    }

    private void checkCoinCollected() {
        GameComponent coinGameComponent = coin.getGameComponent();
        GameComponent trumpGameComponent = trump.getGameComponent();

        Rectangle coinGameComponentBounds = coinGameComponent.getBounds();
        Rectangle trumpGameComponentBounds = trumpGameComponent.getBounds();

        if (trumpGameComponentBounds.intersects(coinGameComponentBounds)) {
            coinsCollected++;
            if (coinsCollected % 10 == 0) {
                Clip clip = Resources.CASH.getData();
                clip.setMicrosecondPosition(0);
                clip.start();
            }
            repositionCoin();

            AnimationObject<Coin.Animations> coinAnimationObject = coin.getAnimationObject();
            Coin.Animations coinAnimations = coinAnimationObject.getAnimations();
            SpriteAnimation animation = coinAnimations.getCoinRotateAnimation();
            if ((coinsCollected + 1) % 10 == 0) {
                animation = coinAnimations.getCoinBlinkAnimation();

            } else if ((coinsCollected + 1) % 5 == 0) {
                animation = coinAnimations.getCoinShineAnimation();
            }
            coinAnimationObject.setCurrentAnimation(animation);

            CoinCollectedEvent event = new CoinCollectedEvent(this);
            this.fireEvent(event);
        }

    }

    private void repositionCoin() {
        GameComponent coinGameComponent = coin.getGameComponent();
        MovableObject coinMovableObject = coin.getMovableObject();

        double randX = random.nextInt((int) (container.getWidth() * 0.9) - coinGameComponent.getWidth()) + coinGameComponent.getWidth();
        double randY = random.nextInt((int) (container.getHeight() * 0.9) - coinGameComponent.getHeight()) + coinGameComponent.getHeight();
        coinMovableObject.setPosition(randX, randY);
    }

    private void updateMovableObjectLocations(long delta, TimeUnit timeUnit) {
        for (MovableEntity entity : movableEntityList) {
            MovableObject movableObject = entity.getMovableObject();
            movableObject.move(delta, timeUnit);
            Point2D position = movableObject.getPosition();
            Point pos = new Point();
            pos.setLocation(position);
        }
    }

    private void calculateTrumpMovementSpeed() {
        long elapsedNanoSeconds = this.getElapsedTime(TimeUnit.NANOSECONDS);
        MovableObject movableObject = trump.getMovableObject();
        BiFunction<Long, Integer, Double> movementFunction = (x, y) -> (((1 / (double) 400) * Math.pow(x * Math.pow(10, -9), 2)) + 0.25 * y + 50.0);
        Double movementSpeed = movementFunction.apply(elapsedNanoSeconds, coinsCollected);
        movableObject.setMovementSpeed(movementSpeed);
    }

    private void displayObama() {
        GameComponent obamaGameComponent = obama.getGameComponent();
        obamaGameComponent.setVisible(true);
        MovableObject obamaMovableObject = obama.getMovableObject();
        obamaMovableObject.setPosition(container.getWidth() / 2, 0);
        obamaMovableObject.setDirection(Direction.SOUTH);
        obamaMovableObject.setMovementSpeed(75);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                obamaMovableObject.move(10, TimeUnit.MILLISECONDS);
            }
        };

        TimerTask cancelTask = new TimerTask() {
            @Override
            public void run() {
                timerTask.cancel();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 10);
        timer.schedule(cancelTask, 4000);

        List<MP3SoundResource> soundResources = List.of(Resources.CELEBRATE, Resources.EARTH_WIND_FIRE);
        int i = random.nextInt(soundResources.size());
        Clip clip = soundResources.get(i).getData();
        clip.setMicrosecondPosition(0);
        clip.start();
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

    public class CoinCollectedEvent extends EventImpl {

        CoinCollectedEvent(Object source) {
            super(source);
        }
    }
}
