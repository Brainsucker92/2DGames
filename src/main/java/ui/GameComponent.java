package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;

public class GameComponent extends JComponent {

    private Drawable drawable;

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
        drawable.draw(g, new Point(), dimension2D);
    }
}
