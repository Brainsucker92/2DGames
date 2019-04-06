package ui;

import java.awt.*;
import java.awt.geom.Dimension2D;

public abstract class ColorToken implements Token {
    @Override
    public void draw(Graphics2D g, Point position, Dimension2D size) {
        Color oldColor = g.getColor();
        g.setColor(getColor());
        g.fillRect(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
        g.setColor(oldColor);
    }

    abstract Color getColor();
}
