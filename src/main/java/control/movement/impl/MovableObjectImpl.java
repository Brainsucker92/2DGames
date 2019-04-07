package control.movement.impl;

import control.movement.Direction;
import control.movement.MovableObject;
import control.movement.MovementController;
import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;
import data.event.impl.EventImpl;
import data.event.impl.EventObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class MovableObjectImpl implements MovableObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovableObject.class);

    private Direction direction = Direction.EAST;
    private Point2D position;
    private double movementSpeed;
    private MovementController movementController;

    private EventObject eventObject;

    public MovableObjectImpl() {
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
                setPosition(x, y - diff);
                break;
            case EAST:
                setPosition(x + diff, y);
                break;
            case SOUTH:
                setPosition(x, y + diff);
                break;
            case WEST:
                setPosition(x - diff, y);
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
    public void setPosition(Point2D position) {
        Point2D oldPosition = (Point2D) this.position.clone();
        this.position.setLocation(position);
        // LOGGER.debug("Position: " + position.toString());
        if (!oldPosition.equals(position)) {
            PositionChangedEvent event = new PositionChangedEvent(this, oldPosition, position);
            fireEvent(event);
        }
    }

    @Override
    public void setPosition(double x, double y) {
        setPosition(new Point2D.Double(x, y));
    }

    @Override
    public MovementController getMovementController() {
        return this.movementController;
    }

    @Override
    public void setMovementController(MovementController movementController) {
        MovementController oldController = this.movementController;
        this.movementController = movementController;
        movementController.setMovableObject(this);
        if (oldController != movementController) {
            MovementControllerChangedEvent event = new MovementControllerChangedEvent(this, oldController, movementController);
            fireEvent(event);
        }
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

    public class PositionChangedEvent extends EventImpl {

        private Point2D oldPosition;
        private Point2D newPosition;

        public PositionChangedEvent(Object source, Point2D oldPosition, Point2D newPosition) {
            super(source);
            this.oldPosition = oldPosition;
            this.newPosition = newPosition;
        }

        public Point2D getOldPosition() {
            return oldPosition;
        }

        public Point2D getNewPosition() {
            return newPosition;
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

    public class MovementSpeedChangedEvent extends EventImpl {

        private double oldSpeed;
        private double newSpeed;

        MovementSpeedChangedEvent(Object source, double oldSpeed, double newSpeed) {
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

    public class MovementControllerChangedEvent extends EventImpl {

        private MovementController oldController;
        private MovementController newControlller;

        public MovementControllerChangedEvent(Object source, MovementController oldController, MovementController newControlller) {
            super(source);
            this.oldController = oldController;
            this.newControlller = newControlller;
        }

        public MovementController getOldController() {
            return oldController;
        }

        public MovementController getNewController() {
            return newControlller;
        }
    }
}