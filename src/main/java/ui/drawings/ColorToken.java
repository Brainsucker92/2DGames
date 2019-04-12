package ui.drawings;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class ColorToken extends ColorDrawing {
    @Override
    protected void drawAdditional(Graphics2D g, Point position, Dimension2D size) {
        g.fillRect(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
    }
}
