package ui.panels;

import control.controllers.game.TrumpGameController;

import javax.swing.*;

public class AmountCoinsCollectedDisplayPanel extends EntityValueDisplayPanel<TrumpGameController> {


    public AmountCoinsCollectedDisplayPanel() {
        super();

        JLabel entityNameLabel = this.getEntityNameLabel();
        entityNameLabel.setText("Coins collected: ");

    }

    @Override
    public void displayEntityValue(TrumpGameController entity) {
        JLabel entityValueLabel = this.getEntityValueLabel();
        entityValueLabel.setText(String.valueOf(entity.getAmountCoinsCollected()));
    }
}
