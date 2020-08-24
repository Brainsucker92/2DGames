package control.movement;

import java.awt.geom.Point2D;

/**
 * Moves an object towards a specified destination.
 */
public interface DestinationMovementController extends MovementController {

    Point2D getDestination();

    void setDestination(Point2D destination);
}
