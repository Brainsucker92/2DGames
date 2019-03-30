package control.movement;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public interface MoveableObject {

    void move();

    void move(long delta, TimeUnit timeUnit);

    Direction getDirection();

    void setDirection(Direction direction);

    Point getPosition();

    double getMovementSpeed();

    void setMovementSpeed(double movementSpeed);
}
