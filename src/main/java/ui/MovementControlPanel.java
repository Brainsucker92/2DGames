package ui;

import control.InputType;
import control.InputTypeController;

import javax.swing.*;
import java.util.Iterator;

public class MovementControlPanel extends JPanel {

    private JRadioButton keyButton;
    private JRadioButton mouseClickButton;
    private JRadioButton mouseMotionButton;

    private ButtonGroup buttonGroup;

    private InputTypeController inputTypeController;

    public MovementControlPanel() {

        keyButton = new JRadioButton();
        keyButton.setText("Keyboard");
        keyButton.setSelected(true);
        keyButton.addActionListener(e -> inputTypeController.useController(InputType.KEY));

        mouseClickButton = new JRadioButton();
        mouseClickButton.setText("Mouse click");
        mouseClickButton.addActionListener(e -> inputTypeController.useController(InputType.MOUSE_CLICK));

        mouseMotionButton = new JRadioButton();
        mouseMotionButton.setText("Mouse motion");
        mouseMotionButton.addActionListener(e -> inputTypeController.useController(InputType.MOUSE_MOTION));

        buttonGroup = new ButtonGroup();
        buttonGroup.add(keyButton);
        buttonGroup.add(mouseClickButton);
        buttonGroup.add(mouseMotionButton);


        for (Iterator<AbstractButton> it = buttonGroup.getElements().asIterator(); it.hasNext(); ) {
            AbstractButton button = it.next();
            this.add(button);
        }
    }

    public void setInputTypeController(InputTypeController inputTypeController) {
        this.inputTypeController = inputTypeController;
    }
}
