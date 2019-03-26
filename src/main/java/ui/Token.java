package ui;

import java.awt.*;
import java.awt.geom.Dimension2D;

public interface Token {

    void draw(Graphics g, Point position, Dimension2D size);
}
