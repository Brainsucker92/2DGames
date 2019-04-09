package ui.drawings;

import java.awt.*;

public class BlueToken extends ColorToken {

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void setColor(Color color) {
        throw new UnsupportedOperationException("Cannot assign color.");
    }

    @Override
    public String toString() {
        return "B";
    }
}
