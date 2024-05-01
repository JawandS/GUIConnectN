package edu.wm.cs.cs301.connectn.view;
/*
 * Asks for the user's name when a new highscore occurs.
 */

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wm.cs.cs301.connectn.controller.NameInputAction;
import edu.wm.cs.cs301.connectn.model.ConnectNModel;

public class NameDialog extends JDialog {
    private final ConnectNModel model;

    /*
     * Constructor for the name dialog.
     * 
     * @param model the ConnectN model being used
     * @param connectNFrame the ConnectN frame being used
     */
    public NameDialog(ConnectNModel model, ConnectNFrame connectNFrame) {
        super(connectNFrame.getFrame(), "New Highscore", true);
        this.model = model;

        // close the dialog when the user clicks the close button
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(createAndShowGUI(), BorderLayout.CENTER);

        // display
        pack();
        setLocationRelativeTo(connectNFrame.getFrame());
        setVisible(true);
    }

    /*
     * Creates the name dialog.
     * 
     * @return the panel for the name dialog
     */
    private JPanel createAndShowGUI() {
        JPanel panel = new JPanel(new BorderLayout());

        // get user input for name
        JLabel nameLabel = new JLabel("Enter your name: ");
        JTextField nameField = new JTextField(20);

        // submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // add new highscore to leaderboard
            new NameInputAction(model, nameField.getText());
            NameDialog.this.dispose();
        });

        // add components to panel
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(nameField, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        return panel;
    }
}
