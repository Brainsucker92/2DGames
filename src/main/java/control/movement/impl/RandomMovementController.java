package control.movement.impl;

import control.movement.MovableObject;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomMovementController extends MovementControllerImpl {

    private Random random;

    private Rectangle rectangle;

    public RandomMovementController(MovableObject movableObject) {
        super(movableObject);
        random = new Random();
        rectangle = new Rectangle();
    }

    @Override
    public void move(long delta, TimeUnit timeUnit) {
        MovableObject movableObject = this.getMovableObject();

        int randX = random.nextInt(rectangle.width) + rectangle.x;
        int randY = random.nextInt(rectangle.height) + rectangle.y;
        movableObject.setPosition(randX, randY);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
