package ui.drawings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Dimension2D;

import ui.Drawable;

public class ErrorDrawing implements Drawable {
    @Override
    public void draw(Graphics2D graphics, Point position, Dimension2D size) {
        Color oldColor = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.drawRect(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
        graphics.drawString("ERROR", ((int) (position.x + (size.getWidth() / 2))), ((int) (position.y + size.getHeight() / 2)));
        graphics.setColor(oldColor);
    }
}
