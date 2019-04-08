package control.movement;

import data.event.EventSource;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public interface MovableObject extends EventSource {

    void move();

    void move(long delta, TimeUnit timeUnit);

    Point2D getPosition();

    void setPosition(Point2D position);

    void setPosition(double x, double y);

    MovementController getMovementController();

    void setMovementController(MovementController movementController);

    double getMovementSpeed();

    void setMovementSpeed(double movementSpeed);
}
