package control.movement.impl;

import control.movement.DestinationMovementController;
import control.movement.Direction;
import control.movement.MoveableObject;

import java.awt.geom.Point2D;

public abstract class DestinationMovementControllerImpl extends MovementControllerImpl implements DestinationMovementController {

    private Point2D destination;

    public DestinationMovementControllerImpl(MoveableObject moveableObject) {
        super(moveableObject);
        destination = new Point2D.Double();
    }

    @Override
    public void requestMovementInput() {
        MoveableObject moveableObject = this.getMoveableObject();
        Point2D currentPosition = moveableObject.getPosition();

        double dx = currentPosition.getX() - destination.getX();
        double dy = currentPosition.getY() - destination.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            // Keep walking in x direction
            if (dx > 0) {
                moveableObject.setDirection(Direction.WEST);
            } else {
                moveableObject.setDirection(Direction.EAST);
            }
        } else {
            // Keep walking in y direction
            if (dy > 0) {
                moveableObject.setDirection(Direction.NORTH);
            } else {
                moveableObject.setDirection(Direction.SOUTH);
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
