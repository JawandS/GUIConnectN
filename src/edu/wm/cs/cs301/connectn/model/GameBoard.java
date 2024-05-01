/**
 * Maintains a matrix of location variables representing the game board.
 * 
 * Initializes the game board based on the provided size (small, medium, large)
 * Maintains the number of tokens needed to win and the size of the game board.
 * Computes the winning move for the computer if it exists.
 * Checks for a winner to see game state based on a particular location.
 * Contains the logic to place a piece and display the board visually.
 */
package edu.wm.cs.cs301.connectn.model;

public class GameBoard {
	private Location[][] board; // do not change!
	private int ROW_NUMS; // number of rows
	private int COL_NUMS; // number of columns
	private int NUM_TO_WIN; // number of tokens to win
	private int spacesRemaining;

	// constants
	final static int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };
	final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/*
	 * Set variables for the game board.
	 * 
	 * @param numToWin the number of tokens needed to win
	 * @param numOfRows the number of rows in the game board
	 * @param numOfCols the number of columns in the game board
	 */
	public void setVars(int numToWin, int numOfRows, int numOfCols) {
		NUM_TO_WIN = numToWin;
		ROW_NUMS = numOfRows;
		COL_NUMS = numOfCols;
		board = new Location[ROW_NUMS][COL_NUMS];
	}
	
	/*
	 * Constructor for the game board.
	 * 
	 * @param toCopy another gameBoard used to create a copy
	 */
	public GameBoard(GameBoard toCopy) { // create a copy of another gameboard
		// initialize values
		setVars(toCopy.NUM_TO_WIN, toCopy.ROW_NUMS, toCopy.COL_NUMS);
		// populate board
		for(int rowIdx = 0; rowIdx < ROW_NUMS; rowIdx++)
			for(int colIdx = 0; colIdx < COL_NUMS; colIdx++)
				this.board[rowIdx][colIdx] = new Location(toCopy.board[rowIdx][colIdx]);
	}

	/*
	 * Constructor for the game board.
	 * 
	 * @param size the size of the game board (small, medium, large)
	 */
	public GameBoard(String size) {
		// initialize board
		// check the size of the board and create it
		if (size.equals("small")) { // 4 rows, 5 columns, 3 in a row
			setVars(3, 4, 5);
		} else if (size.equals("medium")) { // medium, 6 rows, 7 columns, 4 in a row
			setVars(4, 6, 7);
		} else if (size.equals("large")) { // 8 rows, 9 columns, 5 in a row
			setVars(5, 8, 9);
		} else {
			// try to parse input as integer
			try {
				int input = Integer.parseInt(size);
				if (input <= 1) {
					setVars(4, 6, 7);
					System.out.println("Invalid input, default medium");
				} else if (input == 2)
					setVars(2, 2, 3);
				else
					setVars(input, 4 + 2 * (input - 3), 5 + 2 * (input - 3));
			} catch(Exception e) {
				setVars(4, 6, 7);
				System.out.println("Invalid input, default medium");
			}
		}
		// initialize empty locations
		for (int rowIdx = 0; rowIdx < ROW_NUMS; rowIdx++)
			for (int colIdx = 0; colIdx < COL_NUMS; colIdx++)
				board[rowIdx][colIdx] = new Location();
		// check number of empty spaces
		spacesRemaining = ROW_NUMS * COL_NUMS;
	}

	public int getColumns() {
		return COL_NUMS;
	}

	public int getRows() {
		return ROW_NUMS;
	}

	public int getNumToWin() {
		return NUM_TO_WIN;
	}

	public String toString() {
		// return the board state as a string
		String boardStr = "";
		for (int rowIdx = 0; rowIdx < ROW_NUMS; rowIdx++) {
			for (int colIdx = 0; colIdx < COL_NUMS; colIdx++) {
				boardStr += board[rowIdx][colIdx].getTokenVal();
			}
		}
		return boardStr;
	}

	public boolean inBounds(int yVar, int xVar) {
		boolean valid = yVar < ROW_NUMS && xVar < COL_NUMS && yVar >= 0 && xVar >= 0;
//		System.out.println("valid: " + valid + " y: " + yVar + " x: " + xVar); 
		return valid;
	}

	/*
	 * Find if a winning move exists for the current player
	 * 
	 * @param currPlayer the current player
	 * @return the column number of the winning move, -1 if no winning move
	 */
	public int findWinningMove(String currPlayer) {
		// return a winning move if it exists for the current player
		for (int colIdx = 0; colIdx < COL_NUMS; colIdx++) {
			for (int rowIdx = ROW_NUMS - 1; rowIdx >= 0; rowIdx--) {
				if (board[rowIdx][colIdx].isEmpty()) { // assume we place here
					for (int[] direction: DIRECTIONS) { // check row, col, and diagonals
						int sameCount = 1; // number of tokens same as current player
						// go in initial direction
						int yVar = rowIdx + direction[0];
						int xVar = colIdx + direction[1];
						while (inBounds(yVar, xVar) && board[yVar][xVar].getToken().toString().equals(currPlayer)) {
							sameCount++;
							yVar += direction[0];
							xVar += direction[1];
						}
						// go in opposite direction
						yVar = rowIdx - direction[0];
						xVar = colIdx - direction[1];
						while (inBounds(yVar, xVar) && board[yVar][xVar].getToken().toString().equals(currPlayer)) {
							sameCount++;
							yVar -= direction[0];
							xVar -= direction[1];
						}
						// check if won and return winning column
						if (sameCount >= NUM_TO_WIN) {
							return colIdx;
						}
					}
					break; // no need to check other rows
				}
			}
		}
		return -1; //no winning move founds 
	}

	/*
	 * Check the number of connections for a particular location
	 * 
	 * @param yPos the row index
	 * @param xPos the column index
	 * @return the maximium number of connections from the current location
	*/ 
	public int countConnections(int yPos, int xPos) {
		int maxConnections = 0;
		for (int[] direction : DIRECTIONS) {
			int yVar = yPos + direction[0];
			int xVar = xPos + direction[1];
			int sameCount = 1; // number of tokens same as current position
			// check in one direction
			while (inBounds(yVar, xVar) && board[yVar][xVar].equals(board[yPos][xPos])) {
				sameCount++;
				yVar += direction[0];
				xVar += direction[1];
			}
			// check in opposite direction
			yVar = yPos - direction[0];
			xVar = xPos - direction[1];
			while (inBounds(yVar, xVar) && board[yVar][xVar].equals(board[yPos][xPos])) {
				sameCount++;
				yVar -= direction[0];
				xVar -= direction[1];
			}
			if (sameCount >= maxConnections) {
				maxConnections = sameCount;
			}
		}
		return maxConnections;
	}

	/*
	 * Check if a winner exists for the player at a particular location
	 * 
	 * @param yPos the row index
	 * @param xPos the column index
	 * @return true if a winner exists, false otherwise
	 */
	public boolean checkForWinner(int yPos, int xPos) {
		// NOTE: kept in a seperate method since the loop terminates when a winner is found
		// go through the board and check for any winners
		for (int[] direction : DIRECTIONS) {
			int yVar = yPos + direction[0];
			int xVar = xPos + direction[1];
			int sameCount = 1; // number of tokens same as current position
			// check in one direction
			while (inBounds(yVar, xVar) && board[yVar][xVar].equals(board[yPos][xPos])) {
				sameCount++;
				yVar += direction[0];
				xVar += direction[1];
			}
			// check in opposite direction
			yVar = yPos - direction[0];
			xVar = xPos - direction[1];
			while (inBounds(yVar, xVar) && board[yVar][xVar].equals(board[yPos][xPos])) {
				sameCount++;
				yVar -= direction[0];
				xVar -= direction[1];
			}
			if (sameCount >= NUM_TO_WIN) {
				return true;
			}
		}
		return false; // continue, not winner
	}

	public boolean checkForDraw() {
		return spacesRemaining == 0; // check if no more spaces
	}

	/*
	 * Parse the column number
	 * 
	 * @param colNum the column number
	 * @return the column index
	 */
	public int parseCol(String colNum) {
		// parse column number
		int colIdx;
		if (colNum.matches("\\d+"))
			colIdx = Integer.parseInt(colNum); 
		else			
			colIdx = ALPHABET.indexOf(colNum.toUpperCase()) + 9;
		return colIdx;
	}

	/*
	 * Remove a piece from a column
	 * 
	 * @param colNum the column number
	 */
	public void removePiece(String colNum) {
		int colIdx = parseCol(colNum);
		// remove a piece from the column
		for (int rowIdx = 0; rowIdx < ROW_NUMS; rowIdx++) {
			if (!board[rowIdx][colIdx].isEmpty()) {
				board[rowIdx][colIdx].setValue(" ");
				spacesRemaining++;
				break;
			}
		}
	}

	/*
	 * Place a piece in a column
	 * 
	 * @param tokenVal the player being placed
	 * @param colNum the column number
	 * @return the result of the move and the row index
	 */
	public String[] placePiece(String tokenVal, String colNum) { // place a piece in a column
		int colIdx = parseCol(colNum);
		// check if out of bounds
		if (colIdx < 0 || colIdx >= COL_NUMS) {
			return new String[]{"invalid", "-1"};
		}
		// check for empty space
		for (int rowIdx = ROW_NUMS - 1; rowIdx >= 0; rowIdx--) {
			if (board[rowIdx][colIdx].isEmpty()) {
				board[rowIdx][colIdx].setValue(tokenVal);
				spacesRemaining--;
				if (checkForWinner(rowIdx, colIdx)) { // player won
					return new String[]{tokenVal, String.valueOf(rowIdx)};
				} else if (checkForDraw()) // draw
					return new String[]{" ", String.valueOf(rowIdx)};
				return new String[]{"valid", String.valueOf(rowIdx)};
			}
		}
		return new String[]{"invalid", "-1"}; // columns is full
	}
}
