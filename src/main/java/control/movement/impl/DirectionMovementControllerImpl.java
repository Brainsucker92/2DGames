package control.movement.impl;

import control.movement.Direction;
import control.movement.DirectionMovementController;
import control.movement.MovableObject;
import data.event.impl.EventImpl;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class DirectionMovementControllerImpl extends MovementControllerImpl implements DirectionMovementController {


    private Direction direction = Direction.EAST;

    public DirectionMovementControllerImpl(MovableObject movableObject) {
        super(movableObject);
    }

    @Override
    public void move(long delta, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(delta);
        MovableObject movableObject = this.getMovableObject();
        double movementSpeed = movableObject.getMovementSpeed();
        Point2D position = movableObject.getPosition();
        double diff = movementSpeed * (nanos * Math.pow(10, -9));
        double x = position.getX();
        double y = position.getY();
        switch (this.getDirection()) {
            case NORTH:
                movableObject.setPosition(x, y - diff);
                break;
            case EAST:
                movableObject.setPosition(x + diff, y);
                break;
            case SOUTH:
                movableObject.setPosition(x, y + diff);
                break;
            case WEST:
                movableObject.setPosition(x - diff, y);
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        Direction oldDirection = this.direction;
        this.direction = direction;

        if (oldDirection != direction) {
            DirectionChangedEvent event = new DirectionChangedEvent(this, oldDirection, direction);
            fireEvent(event);
        }
    }

    public class DirectionChangedEvent extends EventImpl {

        private Direction oldDirection;
        private Direction newDirection;

        DirectionChangedEvent(Object source, Direction oldDirection, Direction newDirection) {
            super(source);
            this.oldDirection = oldDirection;
            this.newDirection = newDirection;
        }

        public Direction getOldDirection() {
            return oldDirection;
        }

        public Direction getNewDirection() {
            return newDirection;
        }
    }
}
