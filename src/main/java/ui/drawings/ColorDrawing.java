package ui.drawings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Dimension2D;

import ui.Drawable;

public abstract class ColorDrawing implements Drawable {

    private Color color = Color.BLACK;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D graphics, Point position, Dimension2D size) {
        // Make sure the drawing color will be reset after drawing stuff.
        Color oldColor = graphics.getColor();
        graphics.setColor(getColor());
        drawAdditional(graphics, position, size);
        graphics.setColor(oldColor);
    }

    protected abstract void drawAdditional(Graphics2D g, Point position, Dimension2D size);
}
