package control;

import control.movement.Controllable;
import control.movement.Direction;
import control.movement.MoveableObject;
import control.movement.impl.MoveableObjectImpl;
import data.ImageResource;
import data.Resource;
import data.ResourceLoader;
import data.Resources;
import data.grid.MP3SoundResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.AnimationDrawer;
import ui.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;
import ui.sprites.SpriteSheet;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Trump implements GameEntity, Controllable {

    public static final Logger LOGGER = LoggerFactory.getLogger(Trump.class);

    private GameComponent component;
    private Animations animations;
    private AnimationDrawer animationDrawer;
    private SpriteAnimation currentAnimation;
    private MoveableObject moveableObject;

    public Trump() {
        ImageResource resource = Resources.TRUMP;

        List<Resource<?>> soundResources = List.of(Resources.TRUMP_THATS_FINE, Resources.TRUMP_BETTER_THAN_EVER_BEFORE,
                Resources.TRUMP_PEOPLE_ARE_TIRED_OF_INCOMPETENCE, Resources.TRUMP_I_CAN_MAKE_A_BIG_DIFFERENCE,
                Resources.TRUMP_UNITED_STATES_IS_RUN_BY_STUPID_PEOPLE, Resources.TRUMP_BELIEVE_IT_OR_NOT,
                Resources.TRUMP_CAN_YOU_BELIEVE_THIS, Resources.TRUMP_DOING_THE_RIGHT_THING,
                Resources.TRUMP_FIFTEEN_MILLION_DOLLARS, Resources.TRUMP_HEY, Resources.TRUMP_I_ACTUALLY_ENJOY_THAT,
                Resources.TRUMP_I_WAS_EXHILARATED, Resources.TRUMP_IM_REALLY_RICH, Resources.TRUMP_INCOMPETENT_POLITICIANS,
                Resources.TRUMP_MAYBE_WINNING, Resources.TRUMP_PEOPLE_ARE_TIRED_OF_INCOMPETENCE, Resources.TRUMP_IM_ENJOYING_IT,
                Resources.TRUMP_PHENOMENAL, Resources.TRUMP_RICH, Resources.TRUMP_SO_AMAZING, Resources.TRUMP_TREMENDOUS_AMOUNTS_OF_DOLLARS,
                Resources.TRUMP_IM_REALLY_RICH, Resources.TRUMP_WELL_OVER_TEN_BILLION_DOLLARS, Resources.TRUMP_WELL,
                Resources.TRUMP_WE_HAVE_TO_KEEP_IT_GOING, Resources.TRUMP_WERE_GOING_TO_DO_A_FANTASTIC_JOB,
                Resources.TRUMP_WE_HAVE_LOSERS, Resources.TRUMP_WE_STILL_HAVE_A_LONG_WAY_TO_GO);

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> futureImages = resourceLoader.loadResources(List.of(resource));
        Future<?> futureSounds = resourceLoader.loadResources(soundResources);

        // Do some other stuff while resources are loading.
        component = new GameComponent();
        component.setPreferredSize(new Dimension(100, 100));
        component.setMinimumSize(new Dimension(10, 10));
        // component.setLocation(200, 50);
        component.setVisible(true);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Random random = new Random();
                int i = random.nextInt(soundResources.size());
                MP3SoundResource mP3SoundResource = (MP3SoundResource) soundResources.get(i);
                Clip clip = mP3SoundResource.getData();
                clip.setMicrosecondPosition(0);
                clip.start();
            }
        });

        moveableObject = new MoveableObjectImpl();

        component.setFocusable(true);
        component.requestFocus();

        // It's time to access the resources.
        try {
            // make sure all resources have been loaded.
            futureImages.get();
            futureSounds.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error during resource loading", e);
        }

        SpriteSheet spriteSheet = new SpriteSheet((BufferedImage) resource.getData(), 6, 4);
        animations = new Animations(spriteSheet);
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

    public Animations getAnimations() {
        return this.animations;
    }

    public MoveableObject getMoveableObject() {
        return this.moveableObject;
    }

    @Override
    public Point2D getPosition() {
        return component.getLocation();
    }

    @Override
    public void setPosition(Point2D position) {
        Point pos = new Point();
        pos.setLocation(position.getX(), position.getY());
        component.setLocation(pos);
    }

    public void gameTick(long delta, TimeUnit timeUnit) {
        moveableObject.move(delta, timeUnit);
        Point2D position = moveableObject.getPosition();
        Point pos = new Point();
        pos.setLocation(position);
        component.setLocation(pos);

        Direction direction = moveableObject.getDirection();
        switch (direction) {
            case NORTH:
                setCurrentAnimation(animations.getWalkNorthAnimation());
                break;
            case EAST:
                setCurrentAnimation(animations.getWalkEastAnimation());
                break;
            case SOUTH:
                setCurrentAnimation(animations.getWalkSouthAnimation());
                break;
            case WEST:
                setCurrentAnimation(animations.getWalkWestAnimation());
                break;
        }
    }

    public class Animations {
        private SpriteAnimation walkSouth;
        private SpriteAnimation walkEast;
        private SpriteAnimation walkNorth;
        private SpriteAnimation walkWest;

        Animations(SpriteSheet spriteSheet) {
            walkSouth = new SpriteAnimation(spriteSheet.getRow(0).toArray(Sprite[]::new));
            walkEast = new SpriteAnimation(spriteSheet.getRow(1).toArray(Sprite[]::new));
            walkNorth = new SpriteAnimation(spriteSheet.getRow(2).toArray(Sprite[]::new));
            walkWest = new SpriteAnimation(spriteSheet.getRow(3).toArray(Sprite[]::new));
        }

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
