package ui.panels;

import javax.swing.*;


/**
 * @param <T> The entity that is holding the data to display
 */
public abstract class EntityValueDisplayPanel<T> extends JPanel {

    private JLabel entityNameLabel;
    private JLabel entityValueLabel;

    public EntityValueDisplayPanel() {
        super();
        this.entityNameLabel = new JLabel();
        this.entityValueLabel = new JLabel();

        this.entityNameLabel.setText("Missing entity name");
        this.entityValueLabel.setText("Waiting for data input.");

        this.add(entityNameLabel);
        this.add(entityValueLabel);
    }

    public JLabel getEntityNameLabel() {
        return entityNameLabel;
    }

    public JLabel getEntityValueLabel() {
        return entityValueLabel;
    }

    public abstract void displayEntityValue(T entity);
}
