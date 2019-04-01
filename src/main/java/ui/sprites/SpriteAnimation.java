package ui.sprites;


import java.awt.*;
import java.util.function.Supplier;

public class SpriteAnimation implements Supplier<Image> {

    private Sprite[] sprites;
    private int currentPos = 0;

    public SpriteAnimation(Sprite[] sprites) {
        this.sprites = sprites;

    }

    @Deprecated
    public Image getImage() {
        return this.get();
    }

    public Image next() {
        currentPos++;
        currentPos = currentPos % sprites.length;
        return this.get();
    }

    public void reset() {
        currentPos = 0;
    }

    @Override
    public Image get() {
        return sprites[currentPos].getImage();
    }
}
