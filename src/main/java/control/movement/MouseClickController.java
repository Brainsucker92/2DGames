package control.movement;

import control.movement.impl.DestinationMovementControllerImpl;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickController extends DestinationMovementControllerImpl {

    private MouseListener listener;

    public MouseClickController(MovableObject movableObject) {
        super(movableObject);

        listener = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point destination = e.getPoint();
                setDestination(destination);
                requestMovementInput();
            }
        };
    }

    @Override
    public void register(Component component) {
        component.addMouseListener(listener);
    }

    @Override
    public void unregister(Component component) {
        component.removeMouseListener(listener);
    }
}
