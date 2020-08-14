package data.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyObserver extends KeyAdapter {

    private static KeyObserver instance;

    private final Map<Integer, Key> keyMap = new HashMap<>();

    private KeyObserver() {
        // private constructor
    }

    public KeyObserver getInstance() {
        if (instance == null) {
            instance = new KeyObserver();
        }
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Key key = this.getKey(keyCode);
        key.pressed();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Key key = this.getKey(keyCode);
        key.released();
    }

    public Key getKey(int keyCode) {
        if (!keyMap.containsKey(keyCode)) {
            Key key = new Key(keyCode);
            keyMap.put(keyCode, key);
            return key;
        }
        return keyMap.get(keyCode);
    }
}
