package control.movement.impl;

import control.movement.Direction;
import control.movement.MoveableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class MoveableObjectImpl implements MoveableObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveableObject.class);

    private Direction direction = Direction.EAST;
    private Point2D position;
    private double movementSpeed;

    public MoveableObjectImpl() {
        position = new Point2D.Double();
    }

    @Override
    public void move() {
        move(1L, TimeUnit.SECONDS);
    }

    @Override
    public void move(long delta, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(delta);
        double diff = movementSpeed * (nanos * Math.pow(10, -9));
        double x = position.getX();
        double y = position.getY();
        switch (this.getDirection()) {
            case NORTH:
                position.setLocation(x, y - diff);
                break;
            case EAST:
                position.setLocation(x + diff, y);
                break;
            case SOUTH:
                position.setLocation(x, y + diff);
                break;
            case WEST:
                position.setLocation(x - diff, y);
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public double getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

}