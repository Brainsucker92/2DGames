package control.impl;

import control.InputType;
import control.InputTypeController;
import control.movement.MovableObject;
import control.movement.MovementController;

import java.util.HashMap;
import java.util.Map;

public class InputTypeControllerImpl implements InputTypeController {

    private Map<InputType, MovementController> inputTypeMovementControllerMap = new HashMap<>();
    private MovableObject movableObject;

    @Override
    public void registerInputController(InputType inputType, MovementController movementController) {
        inputTypeMovementControllerMap.put(inputType, movementController);
    }

    @Override
    public void unregisterInputController(InputType inputType) {
        inputTypeMovementControllerMap.remove(inputType);
    }

    @Override
    public void useController(InputType inputType) {
        MovementController controller = inputTypeMovementControllerMap.get(inputType);
        movableObject.setMovementController(controller);
    }

    @Override
    public void setMovableObject(MovableObject movableObject) {
        this.movableObject = movableObject;
    }
}
