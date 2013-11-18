package view.gui;

import interfaces.IStatus;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * The StatusPanel class is a JPanel to display information and errors.
 */
public class StatusPanel extends JPanel {

    private final JLabel statusLabel = new JLabel("");
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new StatusPanel Object.
     */
    public StatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(statusLabel);
        this.setBackground(Color.WHITE);
    }

    /**
     * Update the status panel, depending to a Status object. On a error,
     * the status panel will be highlighted in RED, otherwise in WHITE.
     * @status The status object, which saves normal text and errors.
     */
    public final void update(IStatus status) {
        if (status.errorExist()) {
            statusLabel.setText(status.getError());
            this.setBackground(Color.RED);
        } else if (status.textExist()) {
            statusLabel.setText(status.getText());
            this.setBackground(Color.WHITE);
        } else {
            statusLabel.setText("");
        }
    }
    
    /**
     * Clear the text from the status panel.
     */
    public void clear() {
        statusLabel.setText("");
    }
}
