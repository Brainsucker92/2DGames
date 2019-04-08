package control.movement;

import data.event.EventSource;

import java.util.concurrent.TimeUnit;

public interface MovementController extends EventSource {

    void move();

    void move(long delta, TimeUnit timeUnit);

    MovableObject getMovableObject();

    void setMovableObject(MovableObject movableObject);
}
