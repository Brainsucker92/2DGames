package sprites;


import java.awt.image.BufferedImage;

public class SpriteAnimation {

    private Sprite[] sprites;
    private int currentPos = 0;

    public SpriteAnimation(Sprite[] sprites) {
        this.sprites = sprites;

    }

    public BufferedImage getImage() {
        return sprites[currentPos].getImage();
    }

    public BufferedImage next() {
        currentPos++;
        currentPos = currentPos % sprites.length;
        return sprites[currentPos].getImage();
    }

    public void reset() {
        currentPos = 0;
    }
}
