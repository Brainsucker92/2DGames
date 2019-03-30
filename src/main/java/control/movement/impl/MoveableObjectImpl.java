package control.movement.impl;

import control.movement.Direction;
import control.movement.MoveableObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class MoveableObjectImpl implements MoveableObject {

    private Direction direction;
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
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Point getPosition() {
        Point point = new Point();
        point.setLocation(position.getX(), position.getY());
        return point;
    }

    public void setPosition(Point2D position) {
        this.position = position;
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