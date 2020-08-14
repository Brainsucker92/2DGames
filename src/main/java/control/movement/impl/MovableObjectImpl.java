package control.movement.impl;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

import control.movement.MovableObject;
import control.movement.MovementController;
import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;
import data.event.impl.EventImpl;
import data.event.impl.EventObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovableObjectImpl implements MovableObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovableObject.class);

    private final Point2D position;
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
        movementController.move();
    }

    @Override
    public void move(long delta, TimeUnit timeUnit) {
        movementController.move(delta, timeUnit);
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
        eventObject.addEventListener(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        eventObject.removeEventListener(eventListener);
    }

    @Override
    public boolean hasEventListener(EventListener listener) {
        return eventObject.hasEventListener(listener);
    }

    protected void fireEvent(Event event) {
        eventObject.fireEvent(event);
    }

    public static class PositionChangedEvent extends EventImpl {

        private final Point2D oldPosition;
        private final Point2D newPosition;

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

    public static class MovementSpeedChangedEvent extends EventImpl {

        private final double oldSpeed;
        private final double newSpeed;

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

    public static class MovementControllerChangedEvent extends EventImpl {

        private final MovementController oldController;
        private final MovementController newController;

        public MovementControllerChangedEvent(Object source, MovementController oldController, MovementController newController) {
            super(source);
            this.oldController = oldController;
            this.newController = newController;
        }

        public MovementController getOldController() {
            return oldController;
        }

        public MovementController getNewController() {
            return newController;
        }
    }
}