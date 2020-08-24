package control.movement;

/**
 * Moves an object towards a specified direction
 */
public interface DirectionMovementController extends MovementController {

    Direction getDirection();

    void setDirection(Direction direction);
}
