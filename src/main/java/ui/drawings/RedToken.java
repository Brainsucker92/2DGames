package ui.drawings;

import java.awt.*;

public class RedToken extends ColorToken {

    @Override
    Color getColor() {
        return Color.RED;
    }

    @Override
    public String toString() {
        return "R";
    }
}
