package ui.animations;

import data.event.EventListener;
import ui.sprites.SpriteAnimation;

public interface AnimationObject<T> {

    void updateAnimation();

    SpriteAnimation getCurrentAnimation();

    void setCurrentAnimation(SpriteAnimation animation);

    AnimationDrawer getAnimationDrawer();

    T getAnimations();

    void addEventListener(EventListener eventListener);

    void removeEventListener(EventListener eventListener);

}
