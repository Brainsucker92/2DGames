package control.movement;

import control.movement.impl.MovementControllerImpl;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputController extends MovementControllerImpl {

    private KeyListener listener;

    private int northKeyCode;
    private int eastKeyCode;
    private int southKeyCode;
    private int westKeyCode;

    public KeyInputController(MovableObject movableObject, int northKeyCode, int eastKeyCode, int southKeyCode, int westKeyCode) {
        super(movableObject);
        // TODO make sure input keys are different.
        this.northKeyCode = northKeyCode;
        this.eastKeyCode = eastKeyCode;
        this.southKeyCode = southKeyCode;
        this.westKeyCode = westKeyCode;
        init();
    }

    private void init() {
        listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                MovableObject movableObject = getMovableObject();
                if (keyCode == northKeyCode) {
                    movableObject.setDirection(Direction.NORTH);
                } else if (keyCode == eastKeyCode) {
                    movableObject.setDirection(Direction.EAST);
                } else if (keyCode == southKeyCode) {
                    movableObject.setDirection(Direction.SOUTH);
                } else if (keyCode == westKeyCode) {
                    movableObject.setDirection(Direction.WEST);
                }
            }
        };
    }

    @Override
    public void requestMovementInput() {
        // empty
    }

    @Override
    public void register(Component component) {
        component.addKeyListener(listener);
    }

    @Override
    public void unregister(Component component) {
        component.removeKeyListener(listener);
    }
}
