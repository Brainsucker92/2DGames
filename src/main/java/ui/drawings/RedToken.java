package ui.drawings;

import java.awt.*;

public class RedToken extends ColorToken {

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public void setColor(Color color) {
        throw new UnsupportedOperationException("Cannot assign color.");
    }

    @Override
    public String toString() {
        return "R";
    }
}
