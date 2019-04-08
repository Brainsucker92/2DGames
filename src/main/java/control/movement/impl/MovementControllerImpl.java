package control.movement.impl;

import control.movement.MovableObject;
import control.movement.MovementController;
import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;
import data.event.impl.EventObjectImpl;

import java.util.concurrent.TimeUnit;

public abstract class MovementControllerImpl implements MovementController {

    private MovableObject movableObject;
    private EventObject eventObject;

    public MovementControllerImpl(MovableObject movableObject) {
        this.movableObject = movableObject;
        this.eventObject = new EventObjectImpl();
    }

    @Override
    public final void move() {
        this.move(1L, TimeUnit.SECONDS);
    }

    @Override
    public MovableObject getMovableObject() {
        return this.movableObject;
    }

    @Override
    public void setMovableObject(MovableObject movableObject) {
        this.movableObject = movableObject;
    }

    protected void fireEvent(Event event) {
        eventObject.fireEvent(event);
    }

    @Override
    public void addEventListener(EventListener listener) {
        eventObject.addEventListener(listener);
    }

    @Override
    public void removeEventListener(EventListener listener) {
        eventObject.addEventListener(listener);
    }
}
