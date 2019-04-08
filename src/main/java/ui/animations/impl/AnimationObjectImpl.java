package ui.animations.impl;

import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;
import data.event.impl.EventImpl;
import data.event.impl.EventObjectImpl;
import ui.animations.AnimationDrawer;
import ui.animations.AnimationObject;
import ui.sprites.SpriteAnimation;

public class AnimationObjectImpl<T extends AnimationObjectImpl.Animations> implements AnimationObject<T> {

    private SpriteAnimation currentAnimation;
    private AnimationDrawer animationDrawer;
    private EventObject eventObject;

    private T animations;

    public AnimationObjectImpl(SpriteAnimation startAnimation, T animations) {
        this.currentAnimation = startAnimation;
        this.animations = animations;
        animationDrawer = new AnimationDrawer(startAnimation);
        eventObject = new EventObjectImpl();
    }

    @Override
    public final void updateAnimation() {
        this.currentAnimation.next();

        AnimationUpdatedEvent event = new AnimationUpdatedEvent(this);
        fireEvent(event);
    }

    @Override
    public final SpriteAnimation getCurrentAnimation() {
        return this.currentAnimation;
    }

    @Override
    public final void setCurrentAnimation(SpriteAnimation animation) {
        SpriteAnimation oldAnimation = this.currentAnimation;
        this.currentAnimation = animation;
        this.animationDrawer.setImageSupplier(animation);

        if (oldAnimation != this.currentAnimation) {
            AnimationChangedEvent event = new AnimationChangedEvent(this, oldAnimation, animation);
            fireEvent(event);
        }
    }

    @Override
    public AnimationDrawer getAnimationDrawer() {
        return animationDrawer;
    }

    @Override
    public final T getAnimations() {
        return this.animations;
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        eventObject.addEventListener(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        eventObject.removeEventListener(eventListener);
    }

    protected void fireEvent(Event event) {
        eventObject.fireEvent(event);
    }

    public interface Animations {

    }

    public class AnimationChangedEvent extends EventImpl {

        private SpriteAnimation oldAnimation;
        private SpriteAnimation newAnimation;

        AnimationChangedEvent(Object source, SpriteAnimation oldAnimation, SpriteAnimation newAnimation) {
            super(source);
            this.oldAnimation = oldAnimation;
            this.newAnimation = newAnimation;
        }

        public SpriteAnimation getOldAnimation() {
            return oldAnimation;
        }

        public SpriteAnimation getNewAnimation() {
            return newAnimation;
        }
    }

    public class AnimationUpdatedEvent extends EventImpl {

        AnimationUpdatedEvent(Object source) {
            super(source);
        }
    }
}
