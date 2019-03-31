package control;

import data.ImageResource;
import data.Resource;
import data.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.AnimationDrawer;
import ui.GameComponent;
import ui.sprites.Sprite;
import ui.sprites.SpriteAnimation;

import java.awt.*;
import java.util.List;

public class Coin implements GameEntity {

    public static final Logger LOGGER = LoggerFactory.getLogger(Coin.class);

    private GameComponent component;
    private AnimationDrawer drawer;

    private Animations animations;
    private SpriteAnimation currentAnimation;

    private Sprite[] coinRotateSprites;
    private Sprite[] coinShineSprites;

    public Coin() {
        init();
    }

    private void init() {

        List<ImageResource> coinRotateResources = List.of(Resources.STAR_COIN_ROTATE1, Resources.STAR_COIN_ROTATE2,
                Resources.STAR_COIN_ROTATE3, Resources.STAR_COIN_ROTATE4,
                Resources.STAR_COIN_ROTATE5, Resources.STAR_COIN_ROTATE6);
        List<ImageResource> coinShineResources = List.of(Resources.STAR_COIN_SHINE1, Resources.STAR_COIN_SHINE2,
                Resources.STAR_COIN_SHINE3, Resources.STAR_COIN_SHINE4,
                Resources.STAR_COIN_SHINE5, Resources.STAR_COIN_SHINE6);

        coinRotateResources.forEach(Resource::load);
        coinShineResources.forEach(Resource::load);
        // TODO load resources!!

        coinRotateSprites = coinRotateResources.stream().map(x -> new Sprite(x.getData())).toArray(Sprite[]::new);
        coinShineSprites = coinShineResources.stream().map(x -> new Sprite(x.getData())).toArray(Sprite[]::new);

        animations = new Animations();
        drawer = new AnimationDrawer(animations.coinRotateAnimation);
        setCurrentAnimation(animations.coinRotateAnimation);
        component = new GameComponent(drawer);
        // component.setLocation(200, 50);
        component.setPreferredSize(new Dimension(100, 100));
        component.setMinimumSize(new Dimension(10, 10));
        component.setVisible(true);
    }

    @Override
    public GameComponent getGameComponent() {
        return this.component;
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
        return this.animations;
    }

    public class Animations {
        private SpriteAnimation coinRotateAnimation = new SpriteAnimation(coinRotateSprites);
        private SpriteAnimation coinShineAnimation = new SpriteAnimation(coinShineSprites);

        public SpriteAnimation getCoinRotateAnimation() {
            return coinRotateAnimation;
        }

        public SpriteAnimation getCoinShineAnimation() {
            return coinShineAnimation;
        }
    }
}
