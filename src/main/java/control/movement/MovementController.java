package control.movement;

import java.awt.*;

public interface MovementController {

    void requestMovementInput();

    void register(Component component);

    void unregister(Component component);

    MoveableObject getMoveableObject();

    void setMoveableObject(MoveableObject moveableObject);

}
