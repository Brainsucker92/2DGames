package control;

import control.movement.MoveableObject;
import control.movement.impl.MoveableObjectImpl;
import data.ImageResource;
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
import ui.sprites.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Flappy implements GameEntity, MoveableEntity, AnimationEntity<Flappy.Animations> {

    public static final Logger LOGGER = LoggerFactory.getLogger(Flappy.class);

    private GameComponent component;
    private MoveableObject moveableObject;
    private AnimationObject<Animations> animationObject;

    public Flappy() {
        init();
    }

    private void init() {

        ImageResource flappyResource = Resources.BIRD;

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> futureResources = resourceLoader.loadResources(List.of(flappyResource));

        moveableObject = new MoveableObjectImpl();

        component = new GameComponent();
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
    public AnimationObject<Animations> getAnimationObject() {
        return animationObject;
    }

    @Override
    public MoveableObject getMoveableObject() {
        return this.moveableObject;
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
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
