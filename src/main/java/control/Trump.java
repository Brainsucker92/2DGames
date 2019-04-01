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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Trump implements GameEntity, KeyListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(Trump.class);

    private GameComponent component;
    private SpriteSheet spriteSheet;
    private Animations animations;
    private AnimationDrawer animationDrawer;

    private SpriteAnimation currentAnimation;

    public Trump() {
        ImageResource resource = Resources.TRUMP;

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        LOGGER.info("Starting to load resources");
        Future<?> future = resourceLoader.loadResources(List.of(resource));

        // Do some other stuff while resources are loading.
        component = new GameComponent();
        component.setPreferredSize(new Dimension(100, 100));
        component.setMinimumSize(new Dimension(10, 10));
        // component.setLocation(200, 50);
        component.setVisible(true);

        // It's time to access the resources.
        try {
            // make sure all resources have been loaded.
            future.get();
            LOGGER.info("Finished loading resources");
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error during resource loading", e);
        }

        spriteSheet = new SpriteSheet((BufferedImage) resource.getData(), 6, 4);
        animations = new Animations();
        currentAnimation = animations.walkEast;
        animationDrawer = new AnimationDrawer(currentAnimation);
        component.setDrawable(animationDrawer);

    }

    public void updateAnimation() {
        currentAnimation.next();
        component.repaint();
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
    }

    public void setCurrentAnimation(SpriteAnimation animation) {
        currentAnimation = animation;
        animationDrawer.setImageSupplier(animation);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
                setCurrentAnimation(animations.walkNorth);
                break;
            case KeyEvent.VK_A:
                setCurrentAnimation(animations.walkWest);
                break;
            case KeyEvent.VK_S:
                setCurrentAnimation(animations.walkSouth);
                break;
            case KeyEvent.VK_D:
                setCurrentAnimation(animations.walkEast);
                break;
        }
    }

    public Animations getAnimations() {
        return this.animations;
    }

    public class Animations {
        private SpriteAnimation walkSouth = new SpriteAnimation(spriteSheet.getRow(0).toArray(Sprite[]::new));
        private SpriteAnimation walkEast = new SpriteAnimation(spriteSheet.getRow(1).toArray(Sprite[]::new));
        private SpriteAnimation walkNorth = new SpriteAnimation(spriteSheet.getRow(2).toArray(Sprite[]::new));
        private SpriteAnimation walkWest = new SpriteAnimation(spriteSheet.getRow(3).toArray(Sprite[]::new));

        public SpriteAnimation getWalkEastAnimation() {
            return walkEast;
        }

        public SpriteAnimation getWalkNorthAnimation() {
            return walkNorth;
        }

        public SpriteAnimation getWalkSouthAnimation() {
            return walkSouth;
        }

        public SpriteAnimation getWalkWestAnimation() {
            return walkWest;
        }
    }
}
