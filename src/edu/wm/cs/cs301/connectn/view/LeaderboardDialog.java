package edu.wm.cs.cs301.connectn.view;
/*
 * Displays the leaderboard before each game starts.
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;

import edu.wm.cs.cs301.connectn.model.ConnectNModel;

public class LeaderboardDialog extends JDialog {
    private final ConnectNModel model;

    /*
     * Constructor for the leaderboard dialog.
     * 
     * @param model the ConnectN model being used
     * @param connectNFrame the ConnectN frame being used
     */
    public LeaderboardDialog(ConnectNModel model, ConnectNFrame connectNFrame) {
        super(connectNFrame.getFrame(), "Leaderboard", true);
        this.model = model;

        // close the dialog when the user clicks the close button
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel mainPanel = createAndShowGUI();
        add(mainPanel, BorderLayout.CENTER);
       

        // display
        pack();
        setLocationRelativeTo(connectNFrame.getFrame());
        setVisible(true);
    }

    /*
     * Creates the leaderboard dialog.
     * 
     * @return the panel for the leaderboard dialog
     */
    private JPanel createAndShowGUI() {
        JPanel panel = new JPanel(new GridLayout(4, 3));
        panel.add(new JLabel("Mode"));
        panel.add(new JLabel("Turns"));
        panel.add(new JLabel("Player"));
        
        String[] records = model.getRecords();
        for (int i = 0; i < records.length; i++) {
            String[] data = records[i].split(",");
            JLabel mode = new JLabel();
            JLabel turns = new JLabel();
            JLabel player = new JLabel();
            mode.setText(data[0]);
            if (Integer.parseInt(data[1]) < 0) {
                turns.setText("");
                player.setText("");
            } else {
                turns.setText(data[1]);
                player.setText(data[2]);
            }
            panel.add(mode);
            panel.add(turns);
            panel.add(player);
        }

        return panel;
    }
}
