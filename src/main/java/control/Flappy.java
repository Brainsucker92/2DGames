package control;

import data.ImageResource;
import data.Resources;
import ui.AnimationDrawer;
import ui.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;
import ui.sprites.SpriteSheet;

import java.awt.*;


public class Flappy implements GameEntity {

    private GameComponent component;

    private SpriteAnimation animation;

    public Flappy() {
        init();
    }

    private void init() {

        ImageResource flappyResource = Resources.BIRD;
        flappyResource.load();

        SpriteSheet spriteSheet = new SpriteSheet(flappyResource.getData(), 3, 1);

        animation = new SpriteAnimation(spriteSheet.getRow(0).toArray(Sprite[]::new));
        AnimationDrawer drawer = new AnimationDrawer(animation);

        component = new GameComponent(drawer);
        component.setPreferredSize(new Dimension(20, 20));
        component.setMinimumSize(new Dimension(10, 10));
        component.setVisible(true);
    }

    public void updateAnimation() {
        animation.next();
        component.repaint();
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
    }
}
