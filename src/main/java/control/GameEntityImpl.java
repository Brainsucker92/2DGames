package control;

import ui.GameComponent;

public class GameEntityImpl implements GameEntity {

    private GameComponent gameComponent;

    @Override
    public GameComponent getGameComponent() {
        return this.gameComponent;
    }
}
