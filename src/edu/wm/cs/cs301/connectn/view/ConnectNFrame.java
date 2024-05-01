package edu.wm.cs.cs301.connectn.view;
/*
 * Represents the frame for the ConnectN game which contains the main game interface.
 */


import edu.wm.cs.cs301.connectn.model.ConnectNModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.GridLayout;

public class ConnectNFrame {
    private JFrame frame;
    private final ConnectNModel model;
	private JLabel turnNumber;
	private JButton[][] buttons;
	private JPanel connectNGridPanel;
	private GridLayout gridLayout;

	/*
	 * Constructor for the ConnectN frame.
	 * 
	 * @param model the ConnectN model being used
	 */
    public ConnectNFrame(ConnectNModel model) {
		this.model = model;
        this.frame = createAndShowGUI();
		new LeaderboardDialog(model, this);
    }

	/*
	 * Redraws the frame.
	 */
	public void redoFrame() {
		this.frame.dispose();
		this.frame = createAndShowGUI();
		new LeaderboardDialog(model, this);
	}

	/*
	 * Creates the frame for the ConnectN game.
	 */
    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("ConnectN");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setJMenuBar(createMenuBar());
		frame.setResizable(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			 public void windowClosing(WindowEvent event) {
				shutdown();
			}
		});

		// create a grid of buttons representing the connect N board
		gridLayout = new GridLayout(model.getMaximumRows(), model.getColumnCount());
		connectNGridPanel = new JPanel(gridLayout);
		
		// turn number
		turnNumber = new JLabel("Turn: " + model.getTurnNumber());
		frame.add(turnNumber, BorderLayout.NORTH);

		// matrix of buttons
		buttons = new JButton[model.getMaximumRows()][model.getColumnCount()];
		// add buttons
		createButtonGrid();
		frame.add(connectNGridPanel, BorderLayout.CENTER);

		// display
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		System.out.println("Frame size: " + frame.getSize());
		
		return frame;
    }

	/*
	 * Creates a grid of buttons representing the connect N board.
	 * Contains the logic for handling the GUI elements of when a button is clicked.
	 */
	public void createButtonGrid() {
		connectNGridPanel = new JPanel(gridLayout);
		buttons = new JButton[model.getMaximumRows()][model.getColumnCount()];
		for (int i = 0; i < model.getMaximumRows(); i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				JButton button = new JButton();
				button.setText(" ");
				button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				// increase button size
				Dimension buttonSize = new Dimension(50, 50);
				button.setPreferredSize(buttonSize);
				// action listener
				final int col = j + 1;
				button.addActionListener(event -> { 
					// player clicked, make move for player
					String[] resultVals = model.takePlayerTurn(col);
					String resultStr = resultVals[0];
					int row = Integer.parseInt(resultVals[1]);
					if (resultStr.equals("invalid")) {
						System.out.println("Invalid move");
						return;
					} else {
						// update button
						buttons[row][col - 1].setText("X");
						buttons[row][col - 1].repaint();
						// check if player won
						if (resultStr.equals("X")) {
							System.out.println("Player won");
							// check if player beat high score
							Boolean newHighScore = model.checkHighScore();
							if (newHighScore) {
								new NameDialog(model, this);
							}
							new GameoverDialog("Player won", model, this);
							turnNumber.setText("Turn: " + model.getTurnNumber());
							return;
						} else if (resultStr.equals(" ")) {
							System.out.println("Draw");
							new GameoverDialog("Draw", model, this);
							turnNumber.setText("Turn: " + model.getTurnNumber());
							return;
						}
					}
					System.out.println("Button clicked: " + col);
					// computer takes turn
					String[] computerResultVals = model.takeComputerTurn();
					int computerRow = Integer.parseInt(computerResultVals[0]);
					int computerCol = Integer.parseInt(computerResultVals[1]);
					String computerResult = computerResultVals[2];
					while (computerResult.equals("invalid")) {
						computerResultVals = model.takeComputerTurn();
						computerRow = Integer.parseInt(computerResultVals[0]);
						computerCol = Integer.parseInt(computerResultVals[1]);
						computerResult = computerResultVals[2];
					}
					
					buttons[computerRow][computerCol].setText("O");
					if (computerResult.equals("O")) {
						System.out.println("Computer won");
						new GameoverDialog("Computer won", model, this);
						turnNumber.setText("Turn: " + model.getTurnNumber());
						return;
					} else if (computerResult.equals(" ")) {
						System.out.println("Draw");
						new GameoverDialog("Draw", model, this);
						turnNumber.setText("Turn: " + model.getTurnNumber());
						return;
					}
				
					turnNumber.setText("Turn: " + model.getTurnNumber());
				});
				connectNGridPanel.add(button);
				buttons[i][j] = button;
			}
		}
		// repaint
		connectNGridPanel.repaint();
	}

	/*
	 * Creates the menu bar for the ConnectN game.
	 * 
	 * @return the menu bar for the ConnectN game
	 */
    private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

        // add a difficulty dropdown
		JMenu difficultyMenu = new JMenu("Size");
		JMenuItem easyItem = new JMenuItem("Small");
		easyItem.addActionListener(event -> { 
			model.setSize("small");
			// repaint
			redoFrame();
		});
		difficultyMenu.add(easyItem);
		JMenuItem mediumItem = new JMenuItem("Medium");
		mediumItem.addActionListener(event -> { 
			model.setSize("medium");
			// repaint
			redoFrame();
		});
		difficultyMenu.add(mediumItem);
		JMenuItem hardItem = new JMenuItem("Large");
		hardItem.addActionListener(event -> { 
			model.setSize("large");
			// repaint
			redoFrame();
		});
		difficultyMenu.add(hardItem);
		menuBar.add(difficultyMenu);

        // add an exit option
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> {
            shutdown();
        });
        menuBar.add(exitItem);

        return menuBar;
    }

	public JFrame getFrame() {
		return frame;
	}

	/*
	 * Shuts down the ConnectN game.
	*/
    public void shutdown() {
		frame.dispose();
		System.exit(0);
	}

}
