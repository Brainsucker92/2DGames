package ui.panels;

import control.controllers.game.TrumpGameController;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class AmountCoinsCollectedDisplayPanel extends EntityValueDisplayPanel<TrumpGameController> {


    public AmountCoinsCollectedDisplayPanel() {
        super(new JTextField(), new JTextField());

        JTextComponent entityNameTextField = this.getEntityNameTextField();
        entityNameTextField.setText("Coins collected: ");

    }

    @Override
    public void displayEntityValue(TrumpGameController entity) {
        JTextComponent entityValueTextField = this.getEntityValueTextField();
        entityValueTextField.setText(String.valueOf(entity.getAmountCoinsCollected()));
    }
}
