package ui.panels;

import control.controllers.game.GameController;
import data.grid.event.EventListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.concurrent.TimeUnit;

public class ElapsedTimeDisplayPanel extends EntityValueDisplayPanel<GameController> {

    private EventListener eventListener;

    public ElapsedTimeDisplayPanel() {
        super(new JTextField(), new JTextField());

        JTextComponent entityNameTextField = this.getEntityNameTextField();
        entityNameTextField.setText("Elapsed time: ");
    }

    @Override
    public void displayEntityValue(GameController entity) {
        JTextComponent entityValueTextField = this.getEntityValueTextField();
        // TODO format time string properly.
        entityValueTextField.setText(String.valueOf(entity.getElapsedTime(TimeUnit.SECONDS)));
    }
}
