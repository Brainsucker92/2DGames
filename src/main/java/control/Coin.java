package control;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import control.entities.AnimationEntity;
import control.movement.impl.MovableGameEntityImpl;
import data.resources.Resource;
import data.resources.ResourceLoader;
import data.resources.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.animations.AnimationDrawer;
import ui.animations.AnimationObject;
import ui.animations.impl.AnimationObjectImpl;
import ui.components.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;

public class Coin extends MovableGameEntityImpl implements AnimationEntity<Coin.Animations> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Coin.class);

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

        GameComponent component = this.getGameComponent();
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

        animationObject.addEventListener(event -> {
            if (event instanceof AnimationObjectImpl.AnimationUpdatedEvent) {
                component.repaint();
            }
        });
    }

    @Override
    public AnimationObject<Animations> getAnimationObject() {
        return this.animationObject;
    }

    public static class Animations implements AnimationObjectImpl.Animations {
        private final SpriteAnimation coinRotateAnimation;
        private final SpriteAnimation coinShineAnimation;
        private final SpriteAnimation coinBlinkAnimation;

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
