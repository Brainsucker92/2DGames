package ui;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class ErrorDrawing implements Drawable {
    @Override
    public void draw(Graphics g, Point position, Dimension2D size) {
        Color oldColor = g.getColor();
        g.setColor(Color.RED);
        g.drawString("ERROR", position.x, position.y);
        g.setColor(oldColor);
    }
}
