package data.input;

/**
 * Stores information whether this Key is currently pressed or not.
 */
public class Key {

    private int keyCode;
    private boolean isPressed = false;

    Key(int keyCode) {
        this.keyCode = keyCode;
    }

    void pressed() {
        isPressed = true;
    }

    void released() {
        isPressed = false;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
