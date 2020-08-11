package ui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Dimension2D;

import javax.swing.JComponent;

import ui.Drawable;
import ui.drawings.ErrorDrawing;

public class GameComponent extends JComponent {

    private Drawable drawable;

    public GameComponent() {
        drawable = new ErrorDrawing();
    }

    public GameComponent(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension2D dimension2D = this.getSize();
        Graphics2D g2d = ((Graphics2D) g);
        drawable.draw(g2d, new Point(), dimension2D);
    }
}
