package control.movement.impl;

import control.movement.DestinationMovementController;
import control.movement.Direction;
import control.movement.MovableObject;

import java.awt.geom.Point2D;

public abstract class DestinationMovementControllerImpl extends MovementControllerImpl implements DestinationMovementController {

    private Point2D destination;

    public DestinationMovementControllerImpl(MovableObject movableObject) {
        super(movableObject);
        destination = new Point2D.Double();
    }

    @Override
    public void requestMovementInput() {
        MovableObject movableObject = this.getMovableObject();
        Point2D currentPosition = movableObject.getPosition();

        double dx = currentPosition.getX() - destination.getX();
        double dy = currentPosition.getY() - destination.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            // Keep walking in x direction
            if (dx > 0) {
                movableObject.setDirection(Direction.WEST);
            } else {
                movableObject.setDirection(Direction.EAST);
            }
        } else {
            // Keep walking in y direction
            if (dy > 0) {
                movableObject.setDirection(Direction.NORTH);
            } else {
                movableObject.setDirection(Direction.SOUTH);
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
