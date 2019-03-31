package control;

import data.ImageResource;
import data.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.AnimationDrawer;
import ui.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;
import ui.sprites.SpriteSheet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Trump implements GameEntity {

    public static final Logger LOGGER = LoggerFactory.getLogger(Trump.class);

    private GameComponent component;
    private SpriteSheet spriteSheet;
    private Animations animations;
    private AnimationDrawer animationDrawer;

    private SpriteAnimation currentAnimation;

    public Trump() {
        ImageResource resource = Resources.TRUMP;
        resource.load();
        // TODO load resource via ExecutorService

        spriteSheet = new SpriteSheet(resource.getData(), 6, 4);
        animations = new Animations();
        currentAnimation = animations.walkEast;
        animationDrawer = new AnimationDrawer(currentAnimation);
        component = new GameComponent(animationDrawer);

        component.setFocusable(true);
        component.requestFocus();
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                LOGGER.debug("Received key input");
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
        });
        component.setSize(100, 100);
        component.setLocation(200, 50);
        component.setVisible(true);
    }

    public void updateAnimation() {
        currentAnimation.next();
        component.repaint();
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
    }

    private void setCurrentAnimation(SpriteAnimation animation) {
        currentAnimation = animation;
        animationDrawer.setImageSupplier(animation);
    }

    class Animations {
        SpriteAnimation walkSouth = new SpriteAnimation(spriteSheet.getRow(0).toArray(Sprite[]::new));
        SpriteAnimation walkEast = new SpriteAnimation(spriteSheet.getRow(1).toArray(Sprite[]::new));
        SpriteAnimation walkNorth = new SpriteAnimation(spriteSheet.getRow(2).toArray(Sprite[]::new));
        SpriteAnimation walkWest = new SpriteAnimation(spriteSheet.getRow(3).toArray(Sprite[]::new));
    }
}
