package control.movement;

import java.awt.geom.Point2D;

public interface Controllable {

    Point2D getPosition();

    void setPosition(Point2D position);
}
