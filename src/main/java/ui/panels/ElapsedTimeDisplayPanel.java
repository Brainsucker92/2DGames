package ui.panels;

import control.controllers.game.GameController;

import javax.swing.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ElapsedTimeDisplayPanel extends EntityValueDisplayPanel<GameController> {

    public ElapsedTimeDisplayPanel() {
        super();

        JLabel entityNameLabel = this.getEntityNameLabel();
        entityNameLabel.setText("Elapsed time: ");
    }

    @Override
    public void displayEntityValue(GameController entity) {
        JLabel entityValueLabel = this.getEntityValueLabel();
        Duration duration = Duration.ofNanos(entity.getElapsedTime(TimeUnit.NANOSECONDS));
        entityValueLabel.setText(String.valueOf(duration.toSeconds()));
    }
}
