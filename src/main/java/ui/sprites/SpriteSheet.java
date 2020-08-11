package ui.sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private final BufferedImage image;
    private final int spritesPerRow;
    private final int spritesPerColumn;

    private final int spriteWidth;
    private final int spriteHeight;

    public SpriteSheet(BufferedImage image, int spritesPerColumn, int spritesPerRow) {
        this.image = image;
        this.spritesPerColumn = spritesPerColumn;
        this.spritesPerRow = spritesPerRow;

        spriteWidth = image.getWidth() / spritesPerColumn;
        spriteHeight = image.getHeight() / spritesPerRow;
    }

    public Sprite getSprite(int x, int y, int width, int height) {
        return new Sprite(image.getSubimage(x, y, width, height));
    }

    public List<Sprite> getRow(int rowIndex) {
        List<Sprite> sprites = new ArrayList<>();
        for (int x = 0; x < spritesPerColumn; x++) {
            Sprite sprite = new Sprite(image.getSubimage(x * spriteWidth, rowIndex * spriteHeight, spriteWidth, spriteHeight));
            sprites.add(sprite);
        }
        return sprites;
    }

    public List<Sprite> getSprites() {
        List<Sprite> sprites = new ArrayList<>();
        for (int y = 0; y < spritesPerRow; y++) {
            for (int x = 0; x < spritesPerColumn; x++) {
                Sprite sprite = new Sprite(image.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight));
                sprites.add(sprite);
            }
        }
        return sprites;
    }
}
