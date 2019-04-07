package ui.panels;

import control.movement.MovableObject;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class MovementSpeedDisplayPanel extends EntityValueDisplayPanel<MovableObject> {

    public MovementSpeedDisplayPanel() {
        super(new JTextField(), new JTextField());
        JTextComponent movementSpeedTextField = this.getEntityNameTextField();
        movementSpeedTextField.setText("Movement speed:");
    }

    public void displayEntityValue(MovableObject movableObject) {
        JTextComponent movementSpeedValueTextField = this.getEntityValueTextField();
        double movementSpeed = movableObject.getMovementSpeed();
        movementSpeedValueTextField.setText(String.valueOf(movementSpeed));
    }
}
