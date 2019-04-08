package control.movement.impl;

import control.movement.InputMovementController;
import control.movement.MovableObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class MouseMotionController extends DestinationMovementControllerImpl implements InputMovementController {

    private MouseMotionListener listener;

    public MouseMotionController(MovableObject movableObject) {
        super(movableObject);
        init();
    }

    private void init() {

        listener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point destination = e.getPoint();
                setDestination(destination);
            }
        };
    }

    @Override
    public void register(Component component) {
        component.addMouseMotionListener(listener);
    }

    @Override
    public void unregister(Component component) {
        component.removeMouseMotionListener(listener);
    }
}
