package ui.animations;

import ui.Drawable;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.function.Supplier;

public class AnimationDrawer implements Drawable {

    private Supplier<Image> imageSupplier;

    public AnimationDrawer(Supplier<Image> imageSupplier) {
        this.imageSupplier = imageSupplier;
    }

    public Supplier<Image> getImageSupplier() {
        return imageSupplier;
    }

    public void setImageSupplier(Supplier<Image> imageSupplier) {
        this.imageSupplier = imageSupplier;
    }

    @Override
    public void draw(Graphics2D g, Point position, Dimension2D size) {
        Image image = imageSupplier.get();
        g.drawImage(image, position.x, position.y, (int) size.getWidth(), (int) size.getHeight(), null);
    }
}
