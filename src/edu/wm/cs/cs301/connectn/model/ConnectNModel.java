package edu.wm.cs.cs301.connectn.model;

/*
 * Represents the model for the ConnectN game.
 * 
 * Contains the game board, human player, computer player, and leaderboard.
 * Implements the logic for the game.
 * Used by the views to display the game state.
 */

public class ConnectNModel {
	private int turnNumber;
	GameBoard gameBoard;
	private HumanPlayer humanPlayer;
	private ComputerPlayer computerPlayer;
	private Leaderboard leaderboard;
	private String size;
	private String gameStatus;

	/*
	 * Constructor for the ConnectN model.
	 */
    public ConnectNModel() {
		this.size = "medium";
		turnNumber = 1;
		gameBoard = new GameBoard(size);
		humanPlayer = new HumanPlayer(gameBoard);
		computerPlayer = new ComputerPlayer(gameBoard);
		gameStatus = "";
		this.leaderboard = new Leaderboard();
	}

	/*
	 * Restart the game. Reset the game board, human player, computer player, and game status.
	 */
	public void restart() {
		turnNumber = 1;
		gameBoard = new GameBoard(this.size);
		humanPlayer = new HumanPlayer(gameBoard);
		computerPlayer = new ComputerPlayer(gameBoard);
		gameStatus = "";
	}

	public void setSize(String size) {
		this.size = size;
		restart();
	}

	public String[] getRecords() {
		return leaderboard.getRecords();
	}

	/*
	 * Take the player's turn by placing a piece in the specified column.
	 * 
	 * @param col the column to place the piece
	 * @return the result of the turn and row of the piece placed
	 */
	public String[] takePlayerTurn(int col) {
		if (!gameStatus.equals(""))
			return new String[] {"invalid", "-1"};
		String[] resultVals = humanPlayer.takeTurn(col);
		int colNum = Integer.parseInt(resultVals[1]);
		if (colNum >= 0)
			turnNumber++;
		if (resultVals[0].equals("X") || resultVals[0].equals("O") || resultVals[0].equals(" "))
			gameStatus = resultVals[0];
		return resultVals;
	}

	/*
	 * Take the computer's turn.
	 * 
	 * @return the row, column, and result of the turn
	 */
	public String[] takeComputerTurn() {
		if (!gameStatus.equals(""))
			return new String[] {"-1", "-1", "invalid"};
		String[] resultVals = computerPlayer.takeTurn();
		if (resultVals[2].equals("X") || resultVals[2].equals("O") || resultVals[2].equals(" "))
			gameStatus = resultVals[2];
		return resultVals;
	}

	/*
	 * Check if the game resulted in a new high score.
	 * 
	 * @return true if the game resulted in a new high score, false otherwise
	 */
	public boolean checkHighScore() {
		// get record
		String[] records = leaderboard.getRecords();
		// get current score
		int currentScore = getTurnNumber() - 1;
		// get current size
		String currentSize = getSize();
		// check if current score is higher than any of the records
		for (int idx = 0; idx < records.length; idx++) {
			String[] data = records[idx].split(",");
			if (currentSize.equals(data[0]) && (Integer.parseInt(data[1]) < 0 || currentScore < Integer.parseInt(data[1]))) {
				return true;
			}
		}		
		return false;
	}

	/*
	 * Update the leaderboard with the player's name.
	 * 
	 * @param name the player's name
	 */
	public void updateLeaderboard(String name) {
		leaderboard.updateLeaderboard(size, turnNumber, name);
	}

    public int getColumnCount() {
		return gameBoard.getColumns();
	}

    public int getMaximumRows() {
		return gameBoard.getRows();
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public String getSize() {
		return size;
	}
}
