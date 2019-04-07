package control;

import control.entities.AnimationEntity;
import control.movement.Direction;
import control.movement.MovableObject;
import control.movement.impl.MovableGameEntityImpl;
import control.movement.impl.MovableObjectImpl;
import data.resources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.animations.AnimationDrawer;
import ui.animations.AnimationObject;
import ui.animations.impl.AnimationObjectImpl;
import ui.components.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;
import ui.sprites.SpriteSheet;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Trump extends MovableGameEntityImpl implements AnimationEntity<Trump.Animations> {

    public static final Logger LOGGER = LoggerFactory.getLogger(Trump.class);

    private Animations animations;
    private AnimationObject<Animations> animationObject;

    private Random random;

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
        random = new Random();
        GameComponent component = this.getGameComponent();
        component.setPreferredSize(new Dimension(100, 100));
        component.setMinimumSize(new Dimension(10, 10));
        component.setVisible(true);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int i = random.nextInt(soundResources.size());
                MP3SoundResource mP3SoundResource = (MP3SoundResource) soundResources.get(i);
                Clip clip = mP3SoundResource.getData();
                clip.setMicrosecondPosition(0);
                clip.start();
            }
        });

        MovableObject movableObject = this.getMovableObject();

        movableObject.addEventListener(event -> {
            if (event instanceof MovableObjectImpl.DirectionChangedEvent) {
                MovableObjectImpl.DirectionChangedEvent evt = ((MovableObjectImpl.DirectionChangedEvent) event);
                Direction newDirection = evt.getNewDirection();
                switch (newDirection) {
                    case NORTH:
                        animationObject.setCurrentAnimation(animations.getWalkNorthAnimation());
                        break;
                    case EAST:
                        animationObject.setCurrentAnimation(animations.getWalkEastAnimation());
                        break;
                    case SOUTH:
                        animationObject.setCurrentAnimation(animations.getWalkSouthAnimation());
                        break;
                    case WEST:
                        animationObject.setCurrentAnimation(animations.getWalkWestAnimation());
                        break;
                }
            }
        });

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
        animationObject = new AnimationObjectImpl<>(animations.getWalkEastAnimation(), animations);
        AnimationDrawer animationDrawer = animationObject.getAnimationDrawer();
        component.setDrawable(animationDrawer);

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

    public class Animations implements AnimationObjectImpl.Animations {
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
