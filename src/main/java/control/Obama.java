package control;

import data.ResourceLoader;
import data.Resources;
import data.grid.MP3SoundResource;
import ui.AnimationDrawer;
import ui.GameComponent;
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

public class Obama implements GameEntity {

    private GameComponent gameComponent;
    private Animations animations;
    private AnimationDrawer animationDrawer;

    private SpriteAnimation currentAnimation;

    public Obama() {
        init();
    }

    private void init() {

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        Future<?> loadResources = resourceLoader.loadResources(List.of(Resources.OBAMA, Resources.EARTH_WIND_FIRE,
                Resources.CELEBRATE));
        List<MP3SoundResource> soundResources = List.of(Resources.EARTH_WIND_FIRE, Resources.CELEBRATE);

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
        currentAnimation = obamaAnimation;
        animationDrawer = new AnimationDrawer(obamaAnimation);

        animations = new Animations(obamaAnimation);
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
    }

    public void updateAnimation() {
        currentAnimation.next();
        gameComponent.repaint();
    }

    public void setCurrentAnimation(SpriteAnimation animation) {
        currentAnimation = animation;
        animationDrawer.setImageSupplier(animation);
    }


    @Override
    public GameComponent getGameComponent() {
        return this.gameComponent;
    }

    public Animations getAnimations() {
        return this.animations;
    }

    public class Animations {
        private SpriteAnimation obamaDanceAnimation;

        Animations(SpriteAnimation obamaDanceAnimation) {
            this.obamaDanceAnimation = obamaDanceAnimation;
        }

        public SpriteAnimation getDanceAnimation() {
            return obamaDanceAnimation;
        }
    }
}
