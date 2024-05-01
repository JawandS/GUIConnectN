package edu.wm.cs.cs301.connectn.view;
/*
 * Dialog for the end of the game, informs game status and allows player to start another game or exit.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wm.cs.cs301.connectn.model.ConnectNModel;

public class GameoverDialog extends JDialog {
    private final ConnectNModel model;
    private final ConnectNFrame connectNFrame;

    /*
     * Constructor for the game over dialog.
     * 
     * @param gameStatus the status of the game (win, lose, tie)
     * @param model the ConnectN model being used
     * @param connectNFrame the ConnectN frame being used
     */
    public GameoverDialog(String gameStatus, ConnectNModel model, ConnectNFrame connectNFrame) {
        super(connectNFrame.getFrame(), "GameOver", true);

        add(createAndShowGUI(gameStatus), BorderLayout.CENTER);
        this.model = model;
        this.connectNFrame = connectNFrame;

        // display
        pack();
		setLocationRelativeTo(connectNFrame.getFrame());
		setVisible(true);
    }

    /*
     * Creates the game over dialog.
     * 
     * @param gameStatus the status of the game (win, lose, tie)
     * @return the panel for the game over dialog
     */
    private JPanel createAndShowGUI(String gameStatus) {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        
        // add dialog
        JLabel dialog = new JLabel("Game Over: " + gameStatus);
        panel.add(dialog);

        // add exit and play again buttons
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
            model.restart();
            connectNFrame.redoFrame();
            GameoverDialog.this.dispose();
        });
        panel.add(exitButton);
        panel.add(playAgainButton);

        return panel;
    }

}
