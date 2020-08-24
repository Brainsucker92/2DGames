package ui.animations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Dimension2D;
import java.util.function.Supplier;

import ui.Drawable;

/**
 * Draws an animation onto a specified graphical display. An animation is a series of images that makes an object appear
 * to be moving/behaving in a certain way.
 */
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
    public void draw(Graphics2D graphics, Point position, Dimension2D size) {
        Image image = imageSupplier.get();
        graphics.drawImage(image, position.x, position.y, (int) size.getWidth(), (int) size.getHeight(), null);
    }
}
