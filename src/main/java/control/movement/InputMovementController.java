package control.movement;

import java.awt.*;

public interface InputMovementController extends MovementController {

    void register(Component component);

    void unregister(Component component);
}
