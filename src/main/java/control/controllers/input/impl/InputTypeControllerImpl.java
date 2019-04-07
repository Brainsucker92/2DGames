package control.controllers.input.impl;

import control.controllers.input.InputType;
import control.controllers.input.InputTypeController;
import control.movement.MovableObject;
import control.movement.MovementController;

import java.util.HashMap;
import java.util.Map;

public class InputTypeControllerImpl implements InputTypeController {

    private Map<InputType, MovementController> inputTypeMovementControllerMap = new HashMap<>();
    private MovableObject movableObject;
    private InputType currentInputType;

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
        currentInputType = inputType;
    }

    @Override
    public InputType getCurrentInputType() {
        return currentInputType;
    }

    @Override
    public InputType[] getRegisteredInputTypes() {
        return inputTypeMovementControllerMap.keySet().toArray(InputType[]::new);
    }

    @Override
    public void setMovableObject(MovableObject movableObject) {
        this.movableObject = movableObject;
    }
}
