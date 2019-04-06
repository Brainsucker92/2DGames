package control.movement;

import data.grid.event.EventListener;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public interface MoveableObject {

    void move();

    void move(long delta, TimeUnit timeUnit);

    Direction getDirection();

    Point2D getPosition();

    MovementController getMovementController();

    void setMovementController(MovementController movementController);

    void setDirection(Direction direction);

    double getMovementSpeed();

    void setMovementSpeed(double movementSpeed);

    void addEventListener(EventListener eventListener);

    void removeEventListener(EventListener eventListener);
}
