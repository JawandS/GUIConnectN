package edu.wm.cs.cs301.connectn;
/*
 * Main class for the ConnectN game.
 */
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import edu.wm.cs.cs301.connectn.model.ConnectNModel;
import edu.wm.cs.cs301.connectn.view.ConnectNFrame;
import edu.wm.cs.cs301.connectn.view.InstructionsFrame;

public class ConnectN implements Runnable {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ConnectN());
		
		//Can't use the Cross-Platform Look and Feel on Windows - Needs investigation
		if (!System.getProperty("os.name").contains("Windows")) {
			//Must use cross-platform look and feel so button backgrounds work on Mac
			try {
			    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Runs the ConnectN game.
	 */
	@Override
	public void run() {
		new InstructionsFrame();
		new ConnectNFrame(new ConnectNModel());
	}

}
