package ui.panels;

import control.controllers.input.InputType;
import control.controllers.input.InputTypeController;

import javax.swing.*;
import java.util.Iterator;

public class MovementControlPanel extends JPanel {

    private ButtonGroup buttonGroup;

    private InputTypeController inputTypeController;

    public MovementControlPanel() {

        buttonGroup = new ButtonGroup();
    }

    private void removeButtons() {
        for (Iterator<AbstractButton> it = buttonGroup.getElements().asIterator(); it.hasNext(); ) {
            AbstractButton button = it.next();
            this.remove(button);
        }
    }

    private void addButtons() {
        InputType[] registeredInputTypes = inputTypeController.getRegisteredInputTypes();
        for (InputType inputType : registeredInputTypes) {
            JRadioButton button = new JRadioButton();
            if (inputType == inputTypeController.getCurrentInputType()) {
                button.setSelected(true);
            }
            button.setText(inputType.name());
            button.addActionListener(e -> inputTypeController.useController(inputType));
            buttonGroup.add(button);
            this.add(button);
        }
    }

    public void setInputTypeController(InputTypeController inputTypeController) {
        if (inputTypeController != null) {
            removeButtons();
        }
        this.inputTypeController = inputTypeController;
        addButtons();
    }
}
