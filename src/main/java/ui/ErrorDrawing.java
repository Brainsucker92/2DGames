package ui;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class ErrorDrawing implements Drawable {
    @Override
    public void draw(Graphics2D g, Point position, Dimension2D size) {
        Color oldColor = g.getColor();
        g.setColor(Color.RED);
        g.drawRect(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
        g.drawString("ERROR", ((int) (position.x + (size.getWidth() / 2))), ((int) (position.y + size.getHeight() / 2)));
        g.setColor(oldColor);
    }
}
