package ui;

public class WallComponent extends GameComponent {

    private Drawable drawable = (g, position, size) -> {
        g.fillRect(0, 0, ((int) size.getWidth()), ((int) size.getHeight()));
    };

    public WallComponent() {
        this.setDrawable(drawable);
    }
}
