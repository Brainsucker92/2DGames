package ui.panels;

import javax.swing.*;
import javax.swing.text.JTextComponent;


/**
 * @param <T> The entity that is holding the data to display
 */
public abstract class EntityValueDisplayPanel<T> extends JPanel {

    private JTextComponent entityNameTextField;
    private JTextComponent entityValueTextField;

    public EntityValueDisplayPanel(JTextComponent entityNameTextField, JTextComponent entityValueTextField) {
        super();
        this.entityNameTextField = entityNameTextField;
        this.entityValueTextField = entityValueTextField;

        this.entityNameTextField.setEditable(false);
        this.entityValueTextField.setEditable(false);

        this.entityValueTextField.setText("Waiting for data input.");

        this.add(entityNameTextField);
        this.add(entityValueTextField);
    }

    public JTextComponent getEntityNameTextField() {
        return entityNameTextField;
    }

    public JTextComponent getEntityValueTextField() {
        return entityValueTextField;
    }

    public abstract void displayEntityValue(T entity);
}
