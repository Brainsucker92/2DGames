package ui.sprites;


import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class SpriteAnimation implements Supplier<BufferedImage> {

    private Sprite[] sprites;
    private int currentPos = 0;

    public SpriteAnimation(Sprite[] sprites) {
        this.sprites = sprites;

    }

    @Deprecated
    public BufferedImage getImage() {
        return this.get();
    }

    public BufferedImage next() {
        currentPos++;
        currentPos = currentPos % sprites.length;
        return this.get();
    }

    public void reset() {
        currentPos = 0;
    }

    @Override
    public BufferedImage get() {
        return sprites[currentPos].getImage();
    }
}
