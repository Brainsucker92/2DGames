package ui;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class AnimationDrawer implements Drawable {

    private Supplier<BufferedImage> imageSupplier;

    public AnimationDrawer(Supplier<BufferedImage> imageSupplier) {
        this.imageSupplier = imageSupplier;
    }

    public Supplier<BufferedImage> getImageSupplier() {
        return imageSupplier;
    }

    public void setImageSupplier(Supplier<BufferedImage> imageSupplier) {
        this.imageSupplier = imageSupplier;
    }

    @Override
    public void draw(Graphics g, Point position, Dimension2D size) {
        BufferedImage image = imageSupplier.get();
        g.drawImage(image, position.x, position.y, (int) size.getWidth(), (int) size.getHeight(), null);
    }
}
