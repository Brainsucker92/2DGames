package control.movement.impl;

import control.movement.Direction;
import control.movement.InputController;
import control.movement.MovableObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputController extends DirectionMovementControllerImpl implements InputController {

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
                if (keyCode == northKeyCode) {
                    setDirection(Direction.NORTH);
                } else if (keyCode == eastKeyCode) {
                    setDirection(Direction.EAST);
                } else if (keyCode == southKeyCode) {
                    setDirection(Direction.SOUTH);
                } else if (keyCode == westKeyCode) {
                    setDirection(Direction.WEST);
                }
            }
        };
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
