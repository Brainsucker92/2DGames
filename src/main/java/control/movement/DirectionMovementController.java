package control.movement;

public interface DirectionMovementController extends MovementController {

    Direction getDirection();

    void setDirection(Direction direction);
}
