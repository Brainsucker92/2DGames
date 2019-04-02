package control.movement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMotionController extends MouseAdapter {

    @Override
    public void mouseMoved(MouseEvent e) {
        e.getPoint();
    }
}
