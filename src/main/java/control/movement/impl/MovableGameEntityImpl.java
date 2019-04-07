package control.movement.impl;

import control.movement.MovableGameEntity;
import control.movement.MovableObject;
import data.grid.event.Event;
import data.grid.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.GameComponent;

import java.awt.geom.Point2D;

public class MovableGameEntityImpl implements MovableGameEntity {

    public static final Logger LOGGER = LoggerFactory.getLogger(MovableGameEntity.class);

    private GameComponent gameComponent;
    private MovableObject movableObject;

    public MovableGameEntityImpl() {
        gameComponent = new GameComponent();
        movableObject = new MovableObjectImpl();

        movableObject.addEventListener(new EventListener() {
            @Override
            public void onEventFired(Event event) {
                if (event instanceof MovableObjectImpl.PositionChangedEvent) {
                    // keep position of MovableObject and GameComponent in sync.
                    MovableObjectImpl.PositionChangedEvent evt = ((MovableObjectImpl.PositionChangedEvent) event);
                    Point2D newPosition = evt.getNewPosition();
                    gameComponent.setLocation(((int) newPosition.getX()), ((int) newPosition.getY()));
                }
            }
        });
    }

    @Override
    public GameComponent getGameComponent() {
        return gameComponent;
    }

    @Override
    public MovableObject getMovableObject() {
        return movableObject;
    }
}
