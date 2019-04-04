package control.movement;

import java.awt.geom.Point2D;

public interface DestinationMovementController extends MovementController {

    Point2D getDestination();

    void setDestination(Point2D destination);
}
