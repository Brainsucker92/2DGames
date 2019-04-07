package control.movement.impl;

import control.movement.MovableObject;
import control.movement.MovementController;

public abstract class MovementControllerImpl implements MovementController {

    private MovableObject movableObject;

    public MovementControllerImpl(MovableObject movableObject) {
        this.movableObject = movableObject;
    }

    @Override
    public MovableObject getMovableObject() {
        return this.movableObject;
    }

    @Override
    public void setMovableObject(MovableObject movableObject) {
        this.movableObject = movableObject;
    }
}
