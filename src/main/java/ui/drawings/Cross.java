package ui.drawings;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class Cross implements Token {
    @Override
    public void draw(Graphics2D g, Point position, Dimension2D size) {
        g.drawLine(position.x, position.y, (int) (position.x + size.getWidth()), (int) (position.y + size.getHeight()));
        g.drawLine(position.x + (int) (size.getWidth()), position.y, position.x, position.y + (int) size.getHeight());
    }

    @Override
    public String toString() {
        return "X";
    }
}
