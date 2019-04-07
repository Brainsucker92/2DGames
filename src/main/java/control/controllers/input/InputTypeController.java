package control.controllers.input;

import control.movement.MovableObject;
import control.movement.MovementController;

public interface InputTypeController {

    void registerInputController(InputType inputType, MovementController movementController);

    void unregisterInputController(InputType inputType);

    void setMovableObject(MovableObject movableObject);

    void useController(InputType inputType);

    InputType getCurrentInputType();

    InputType[] getRegisteredInputTypes();

}
