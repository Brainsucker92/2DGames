package control.movement.impl;

import control.movement.MoveableObject;
import control.movement.MovementController;

public abstract class MovementControllerImpl implements MovementController {

    private MoveableObject moveableObject;

    public MovementControllerImpl(MoveableObject moveableObject) {
        this.moveableObject = moveableObject;
    }

    @Override
    public MoveableObject getMoveableObject() {
        return this.moveableObject;
    }

    @Override
    public void setMoveableObject(MoveableObject moveableObject) {
        this.moveableObject = moveableObject;
    }
}
