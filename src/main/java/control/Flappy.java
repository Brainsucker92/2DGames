package control;

import control.movement.impl.MovableGameEntityImpl;
import data.ImageResource;
import data.ResourceLoader;
import data.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.animations.AnimationDrawer;
import ui.animations.AnimationObject;
import ui.animations.impl.AnimationObjectImpl;
import ui.components.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;
import ui.sprites.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Flappy extends MovableGameEntityImpl implements AnimationEntity<Flappy.Animations> {

    public static final Logger LOGGER = LoggerFactory.getLogger(Flappy.class);

    private AnimationObject<Animations> animationObject;

    public Flappy() {
        init();
    }

    private void init() {

        ImageResource flappyResource = Resources.BIRD;

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> futureResources = resourceLoader.loadResources(List.of(flappyResource));

        GameComponent component = this.getGameComponent();
        component.setPreferredSize(new Dimension(150, 150));
        component.setMinimumSize(new Dimension(10, 10));
        try {
            futureResources.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error during resource loading", e);
        }

        SpriteSheet spriteSheet = new SpriteSheet((BufferedImage) flappyResource.getData(), 3, 1);

        Animations animations = new Animations(spriteSheet);
        animationObject = new AnimationObjectImpl<>(animations.getFlyAnimation(), animations);
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
        return animationObject;
    }

    public class Animations implements AnimationObjectImpl.Animations {

        private SpriteAnimation flyAnimation;

        Animations(SpriteSheet spriteSheet) {
            flyAnimation = new SpriteAnimation(spriteSheet.getRow(0).toArray(Sprite[]::new));
        }

        public SpriteAnimation getFlyAnimation() {
            return flyAnimation;
        }
    }

}
