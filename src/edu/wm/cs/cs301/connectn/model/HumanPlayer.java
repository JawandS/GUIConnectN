/**
 * Represents the human player in the game.
 * 
 * Allows the user to take a turn, scans for user columns selection, and calls the place piece method from game board.
 */
package edu.wm.cs.cs301.connectn.model;

public class HumanPlayer {
	private String currentInput;
	private GameBoard gameBoard;
	private String result;

	/**
	 * Constructor for the human player.
	 * 
	 * @param board the game board being used
	 */
	public HumanPlayer(GameBoard board) {
		currentInput = "";
		gameBoard = board;
		result = "";
	}

	public String getInput() {
		return currentInput;
	}

	public String getCurrentResult() {
		return result;
	}

	/**
	 * Allows the user to take a turn by selecting a column to place a piece.
	 * 
	 * @param colVal the column value to place the piece
	 * @return the result code, the row of the turn (-1 if invalid)
	 */
	public String[] takeTurn(int colVal) {
		currentInput = String.valueOf(colVal);
		result = "";
		System.out.println("Current input: " + currentInput); 
		while (result.equals("invalid") || result.equals("")) {
			if (result == "invalid") {
				System.out.println("Invalid input! Reinput column: ");
				return new String[] {"invalid", "-1"};
			}

			if (currentInput.equals("QUIT")) { // end game signal
				return new String[] {"quit", "-1"};
			}
			if (currentInput.equals("")){
				System.out.println("Invalid input, please reinput column: ");
				continue;
			} else if (currentInput.length() > 1) {
				System.out.println("Invalid input, please reinput column: ");
				continue;
			}
			if (currentInput.matches("\\d+")) { // numeric
				int tmpVal = Integer.parseInt(currentInput) - 1;
				currentInput = Integer.toString(tmpVal);
			}
			String colNum = currentInput;
			String[] resultVals = gameBoard.placePiece("X", colNum);
			result = resultVals[0];
			System.out.println("Result: " + result);
			return resultVals;
		}
		return new String[] {"invalid", "-1"};
	}
}
