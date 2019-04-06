package control;

import control.movement.MoveableObject;
import control.movement.impl.MoveableObjectImpl;
import data.ResourceLoader;
import data.Resources;
import data.grid.MP3SoundResource;
import data.grid.event.Event;
import data.grid.event.EventListener;
import ui.AnimationDrawer;
import ui.AnimationObject;
import ui.GameComponent;
import ui.impl.AnimationObjectImpl;
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

public class Obama implements GameEntity, MoveableEntity, AnimationEntity<Obama.Animations> {

    private GameComponent gameComponent;
    private MoveableObject moveableObject;
    private AnimationObject<Animations> animationObject;

    public Obama() {
        init();
    }

    private void init() {

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> loadResources = resourceLoader.loadResources(List.of(Resources.OBAMA, Resources.EARTH_WIND_FIRE,
                Resources.CELEBRATE));
        List<MP3SoundResource> soundResources = List.of(Resources.EARTH_WIND_FIRE, Resources.CELEBRATE);

        moveableObject = new MoveableObjectImpl();

        gameComponent = new GameComponent();
        gameComponent.setPreferredSize(new Dimension(128, 128));
        gameComponent.setSize(128, 128);
        try {
            loadResources.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Image obamaImage = Resources.OBAMA.getData();
        SpriteSheet obamaSheet = new SpriteSheet((BufferedImage) obamaImage, 8, 1);
        SpriteAnimation obamaAnimation = new SpriteAnimation(obamaSheet.getRow(0).toArray(Sprite[]::new));
        Animations animations = new Animations(obamaAnimation);
        animationObject = new AnimationObjectImpl<>(animations.getDanceAnimation(), animations);
        AnimationDrawer animationDrawer = animationObject.getAnimationDrawer();
        gameComponent.setDrawable(animationDrawer);
        gameComponent.setVisible(true);

        gameComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Random random = new Random();
                int i = random.nextInt(soundResources.size());
                MP3SoundResource mp3SoundResource = soundResources.get(i);
                Clip clip = mp3SoundResource.getData();
                clip.setMicrosecondPosition(0);
                clip.start();
            }
        });

        animationObject.addEventListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof AnimationObjectImpl.AnimationUpdatedEvent) {
                    gameComponent.repaint();
                } else if (event instanceof AnimationObjectImpl.AnimationChangedEvent) {
                    AnimationObjectImpl.AnimationChangedEvent evt = ((AnimationObjectImpl.AnimationChangedEvent) event);
                    SpriteAnimation newAnimation = evt.getNewAnimation();
                    animationDrawer.setImageSupplier(newAnimation);
                }
            }
        });
    }

    @Override
    public GameComponent getGameComponent() {
        return this.gameComponent;
    }

    @Override
    public MoveableObject getMoveableObject() {
        return moveableObject;
    }

    @Override
    public AnimationObject<Animations> getAnimationObject() {
        return animationObject;
    }

    public class Animations implements AnimationObjectImpl.Animations {
        private SpriteAnimation obamaDanceAnimation;

        Animations(SpriteAnimation obamaDanceAnimation) {
            this.obamaDanceAnimation = obamaDanceAnimation;
        }

        public SpriteAnimation getDanceAnimation() {
            return obamaDanceAnimation;
        }
    }
}
