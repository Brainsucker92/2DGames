package ui.animations;

import data.event.EventListener;
import data.event.EventSource;
import ui.sprites.SpriteAnimation;

public interface AnimationObject<T> extends EventSource {

    void updateAnimation();

    SpriteAnimation getCurrentAnimation();

    void setCurrentAnimation(SpriteAnimation animation);

    AnimationDrawer getAnimationDrawer();

    T getAnimations();

    void addEventListener(EventListener eventListener);

    void removeEventListener(EventListener eventListener);

}
