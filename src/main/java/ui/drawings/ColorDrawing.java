package ui.drawings;

import ui.Drawable;

import java.awt.*;
import java.awt.geom.Dimension2D;

public abstract class ColorDrawing implements Drawable {

    private Color color = Color.BLACK;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g, Point position, Dimension2D size) {
        Color oldColor = g.getColor();
        g.setColor(getColor());
        drawAdditional(g, position, size);
        g.setColor(oldColor);
    }

    protected abstract void drawAdditional(Graphics2D g, Point position, Dimension2D size);
}
