package control.movement.impl;

import java.awt.geom.Point2D;

import control.movement.DestinationMovementController;
import control.movement.Direction;
import control.movement.MovableObject;

/**
 * This movement controller calculates it's direction based on a predefined destination point relative to the current
 * position of the {@link MovableObject}. The movement direction is calculate by the absolute values of delta x (dx) and
 * delta y (dy) coordinates. This controller will always prioritize the direction with the higher absolute delta value.
 * <p>
 * Known issues: Once dx = dy, the movement direction will be updated at a very high frequency. This causes the object
 * to change direction very often in a very shot amount of time.
 */
public abstract class DestinationMovementControllerImpl extends DirectionMovementControllerImpl implements DestinationMovementController {

    private Point2D destination;

    public DestinationMovementControllerImpl(MovableObject movableObject) {
        super(movableObject);
        destination = new Point2D.Double();
    }

    protected void updateMovementDirection() {
        MovableObject movableObject = this.getMovableObject();
        Point2D currentPosition = movableObject.getPosition();

        double dx = currentPosition.getX() - destination.getX();
        double dy = currentPosition.getY() - destination.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            // Keep walking in x direction
            if (dx > 0) {
                setDirection(Direction.WEST);
            } else {
                setDirection(Direction.EAST);
            }
        } else {
            // Keep walking in y direction
            if (dy > 0) {
                setDirection(Direction.NORTH);
            } else {
                setDirection(Direction.SOUTH);
            }
        }
    }

    @Override
    public Point2D getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(Point2D destination) {
        this.destination = destination;
    }
}
