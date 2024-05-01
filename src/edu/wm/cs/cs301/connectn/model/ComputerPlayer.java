/**
 * Represents the computer player.
 * 
 * Contains a reference to the main game board.
 * Implement the take turn method which calls the game method place piece to place a piece for the computer.
 * Uses the game board method best computer move to find a winning move for the computer if it exists. 
 */
package edu.wm.cs.cs301.connectn.model;

public class ComputerPlayer {
	private GameBoard gameBoard;
	private int numCols;
	private String result;

	/*
	 * Constructor for the computer player.
	 * 
	 * @param board the game board being used
	 */
	public ComputerPlayer(GameBoard board) {
		gameBoard = board;
		numCols = gameBoard.getColumns();
		result = "";
	}

	public String getCurrentResult() {
		return result;
	}

	/**
	 * Allows the computer to take a turn by selecting a column to place a piece.
	 * 
	 * @return the row, column, and result of the turn
	 */
	public String[] takeTurn() {
		do {
			int moveCol = gameBoard.findWinningMove("O");
			if (moveCol < 0) {
				// int selectCol = (int) minimax(gameBoard, 0.0, Main.COMPUTER_TOKEN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
				// if (selectCol < 0) // no best move found
					// System.err.println("No best move found");
				// select random column
				int selectCol = (int) (Math.random() * numCols);
				String[] resultVals = gameBoard.placePiece("O", "" + selectCol);
				result = resultVals[0];
				String row = resultVals[1];
				String selectColStr = "" + selectCol;
				return new String[]{row, selectColStr, result};
			} else { // winning move
				System.out.println("Winning move found!");
				String[] resultVals = gameBoard.placePiece("O", "" + moveCol);
				result = resultVals[0];
				String row = resultVals[1];
				String moveColStr = "" + moveCol;
				return new String[]{row, moveColStr, result};
			}
		} while (result.equals("invalid"));
	}

}
