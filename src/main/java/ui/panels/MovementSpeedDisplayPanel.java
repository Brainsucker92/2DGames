package ui.panels;

import control.movement.MovableObject;

import javax.swing.*;

public class MovementSpeedDisplayPanel extends EntityValueDisplayPanel<MovableObject> {

    public MovementSpeedDisplayPanel() {
        super();
        JLabel entityNameLabel = this.getEntityNameLabel();
        entityNameLabel.setText("Movement speed:");
    }

    public void displayEntityValue(MovableObject movableObject) {
        JLabel entityValueLabel = this.getEntityValueLabel();
        double movementSpeed = movableObject.getMovementSpeed();
        entityValueLabel.setText(String.valueOf(movementSpeed));
    }
}
