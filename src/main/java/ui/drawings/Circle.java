package ui.drawings;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class Circle implements Token {

    @Override
    public void draw(Graphics2D g, Point position, Dimension2D size) {
        g.drawOval(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
    }

    @Override
    public String toString() {
        return "O";
    }
}
