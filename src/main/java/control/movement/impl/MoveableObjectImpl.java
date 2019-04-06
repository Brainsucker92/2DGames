package control.movement.impl;

import control.movement.Direction;
import control.movement.MoveableObject;
import control.movement.MovementController;
import data.grid.event.Event;
import data.grid.event.EventListener;
import data.grid.event.EventObject;
import data.grid.event.impl.EventImpl;
import data.grid.event.impl.EventObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class MoveableObjectImpl implements MoveableObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveableObject.class);

    private Direction direction = Direction.EAST;
    private Point2D position;
    private double movementSpeed;
    private MovementController movementController;

    private EventObject eventObject;

    public MoveableObjectImpl() {
        position = new Point2D.Double();
        movementSpeed = 0.0;

        eventObject = new EventObjectImpl();
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
    public MovementController getMovementController() {
        return this.movementController;
    }

    @Override
    public void setMovementController(MovementController movementController) {
        this.movementController = movementController;
        movementController.setMoveableObject(this);
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

    @Override
    public double getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public void setMovementSpeed(double movementSpeed) {
        double oldSpeed = this.movementSpeed;
        this.movementSpeed = movementSpeed;

        if (oldSpeed != movementSpeed) {
            MovementSpeedChangedEvent event = new MovementSpeedChangedEvent(this, oldSpeed, movementSpeed);
            fireEvent(event);
        }
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        eventObject.addListener(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        eventObject.removeListener(eventListener);
    }

    protected void fireEvent(Event event) {
        eventObject.fireEvent(event);
    }


    public class DirectionChangedEvent extends EventImpl {

        private Direction oldDirection;
        private Direction newDirection;

        public DirectionChangedEvent(Object source, Direction oldDirection, Direction newDirection) {
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

    public class MovementSpeedChangedEvent extends EventImpl {

        private double oldSpeed;
        private double newSpeed;

        public MovementSpeedChangedEvent(Object source, double oldSpeed, double newSpeed) {
            super(source);
            this.oldSpeed = oldSpeed;
            this.newSpeed = newSpeed;
        }

        public double getOldSpeed() {
            return oldSpeed;
        }

        public double getNewSpeed() {
            return newSpeed;
        }
    }
}