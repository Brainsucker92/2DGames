package ui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Dimension2D;

/**
 * Marks an object as drawable on a two-dimensional graphical display.
 */
public interface Drawable {

    /**
     * Draws the object on the graphical display.
     *
     * @param graphics The canvas for the object to be drawn on.
     * @param position The position of top-left corner of the object to draw
     * @param size     The size (in pixels) of the object to draw.
     */
    void draw(Graphics2D graphics, Point position, Dimension2D size);
}
