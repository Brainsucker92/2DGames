package control;

import data.ImageResource;
import data.ResourceLoader;
import data.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.AnimationDrawer;
import ui.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;
import ui.sprites.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Flappy implements GameEntity {

    public static final Logger LOGGER = LoggerFactory.getLogger(Flappy.class);

    private GameComponent component;
    private SpriteAnimation currentAnimation;
    private Animations animations;
    private AnimationDrawer drawer;

    public Flappy() {
        init();
    }

    private void init() {

        ImageResource flappyResource = Resources.BIRD;

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> futureResources = resourceLoader.loadResources(List.of(flappyResource));

        component = new GameComponent();
        component.setPreferredSize(new Dimension(150, 150));
        component.setMinimumSize(new Dimension(10, 10));
        component.setVisible(true);
        try {
            futureResources.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error during resource loading", e);
        }

        SpriteSheet spriteSheet = new SpriteSheet((BufferedImage) flappyResource.getData(), 3, 1);

        animations = new Animations(spriteSheet);
        currentAnimation = animations.getFlyAnimation();
        drawer = new AnimationDrawer(currentAnimation);
        component.setDrawable(drawer);
    }

    public void updateAnimation() {
        currentAnimation.next();
        component.repaint();
    }

    public void setCurrentAnimation(SpriteAnimation animation) {
        currentAnimation = animation;
        drawer.setImageSupplier(animation);
    }

    public Animations getAnimations() {
        return animations;
    }

    public class Animations {

        private SpriteAnimation flyAnimation;

        Animations(SpriteSheet spriteSheet) {
            flyAnimation = new SpriteAnimation(spriteSheet.getRow(0).toArray(Sprite[]::new));
        }

        public SpriteAnimation getFlyAnimation() {
            return flyAnimation;
        }
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
    }
}
