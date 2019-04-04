package control.movement;

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
}
