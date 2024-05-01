package edu.wm.cs.cs301.connectn.view;
/*
 * Frame for the instructions at the start of the game.
 */

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class InstructionsFrame {
    @SuppressWarnings("unused")
    private final JFrame frame;

    /*
     * Constructor for the instructions frame.
     */
    public InstructionsFrame() {
        this.frame = createAndShowGUI();
    }

    /*
     * Creates the instructions frame.
     * 
     * @return the instructions frame
     */
    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("Instructions");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			 public void windowClosing(WindowEvent event) {
				frame.dispose();
			}
		});

        // add text field with instructions
        String instructionsText = "<html>Welcome! This is a game of ConnectN <br>(small = 3 in a row, medium = 4 in a row, large = 5 in a row).<br>To play, click on a column to drop a token. <br>The first player to connect N tokens in a row wins!</html>";
        JLabel instructions = new JLabel(instructionsText);
        frame.add(instructions, BorderLayout.CENTER);

        // display
        frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

        return frame;
    }
}
