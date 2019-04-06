package control;

import control.movement.MoveableObject;
import control.movement.impl.MoveableObjectImpl;
import data.Resource;
import data.ResourceLoader;
import data.Resources;
import data.grid.event.Event;
import data.grid.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.AnimationDrawer;
import ui.AnimationObject;
import ui.GameComponent;
import ui.impl.AnimationObjectImpl;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Coin implements GameEntity, MoveableEntity, AnimationEntity<Coin.Animations> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Coin.class);

    private GameComponent component;
    private MoveableObject moveableObject;
    private AnimationObject<Animations> animationObject;

    public Coin() {
        init();
    }

    private void init() {

        List<Resource<?>> coinRotateResources = List.of(Resources.STAR_COIN_ROTATE1, Resources.STAR_COIN_ROTATE2,
                Resources.STAR_COIN_ROTATE3, Resources.STAR_COIN_ROTATE4,
                Resources.STAR_COIN_ROTATE5, Resources.STAR_COIN_ROTATE6);
        List<Resource<?>> coinShineResources = List.of(Resources.STAR_COIN_SHINE1, Resources.STAR_COIN_SHINE2,
                Resources.STAR_COIN_SHINE3, Resources.STAR_COIN_SHINE4,
                Resources.STAR_COIN_SHINE5, Resources.STAR_COIN_SHINE6);
        List<Resource<?>> coinBlinkResources = List.of(Resources.STAR_COIN_BLINK1, Resources.STAR_COIN_BLINK2);

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> futureRotateResources = resourceLoader.loadResources(coinRotateResources);
        Future<?> futureShineResources = resourceLoader.loadResources(coinShineResources);
        Future<?> futureBlinkResources = resourceLoader.loadResources(coinBlinkResources);

        moveableObject = new MoveableObjectImpl();

        component = new GameComponent();
        component.setPreferredSize(new Dimension(100, 100));
        component.setMinimumSize(new Dimension(10, 10));

        try {
            futureRotateResources.get();
            futureShineResources.get();
            futureBlinkResources.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error during resource loading", e);
        }

        Sprite[] coinRotateSprites = coinRotateResources.stream().map(x -> new Sprite((BufferedImage) x.getData())).toArray(Sprite[]::new);
        Sprite[] coinShineSprites = coinShineResources.stream().map(x -> new Sprite((BufferedImage) x.getData())).toArray(Sprite[]::new);
        Sprite[] coinBlinkSprites = coinBlinkResources.stream().map(x -> new Sprite((BufferedImage) x.getData())).toArray(Sprite[]::new);

        Animations animations = new Animations(coinRotateSprites, coinShineSprites, coinBlinkSprites);

        SpriteAnimation startAnimation = animations.getCoinRotateAnimation();
        animationObject = new AnimationObjectImpl<>(startAnimation, animations);
        AnimationDrawer animationDrawer = animationObject.getAnimationDrawer();
        component.setDrawable(animationDrawer);
        component.setVisible(true);

        animationObject.addEventListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof AnimationObjectImpl.AnimationUpdatedEvent) {
                    component.repaint();
                }
            }
        });
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
    }

    @Override
    public MoveableObject getMoveableObject() {
        return moveableObject;
    }

    @Override
    public AnimationObject<Animations> getAnimationObject() {
        return this.animationObject;
    }

    public class Animations implements AnimationObjectImpl.Animations {
        private SpriteAnimation coinRotateAnimation;
        private SpriteAnimation coinShineAnimation;
        private SpriteAnimation coinBlinkAnimation;

        Animations(Sprite[] coinRotateSprites, Sprite[] coinShineSprites, Sprite[] coinBlinkSprites) {
            coinRotateAnimation = new SpriteAnimation(coinRotateSprites);
            coinShineAnimation = new SpriteAnimation(coinShineSprites);
            coinBlinkAnimation = new SpriteAnimation(coinBlinkSprites);
        }

        public SpriteAnimation getCoinRotateAnimation() {
            return coinRotateAnimation;
        }

        public SpriteAnimation getCoinShineAnimation() {
            return coinShineAnimation;
        }

        public SpriteAnimation getCoinBlinkAnimation() {
            return coinBlinkAnimation;
        }
    }
}
