package ui.panels;

import control.movement.MovableObject;

import javax.swing.*;
import java.text.DecimalFormat;

public class MovementSpeedDisplayPanel extends EntityValueDisplayPanel<MovableObject> {

    public MovementSpeedDisplayPanel() {
        super();
        JLabel entityNameLabel = this.getEntityNameLabel();
        entityNameLabel.setText("Movement speed:");
    }

    public void displayEntityValue(MovableObject movableObject) {
        JLabel entityValueLabel = this.getEntityValueLabel();
        double movementSpeed = movableObject.getMovementSpeed();
        DecimalFormat format = new DecimalFormat("##.00");
        entityValueLabel.setText(format.format(movementSpeed));
    }
}
