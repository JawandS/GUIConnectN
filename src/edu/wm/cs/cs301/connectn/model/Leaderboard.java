/*
 * Contains the logic to update and maintain the leader board.
 * 
 * Reads in the leader board data when initialized.
 * Displays the leader board if a valid score if found. 
 * Updates the the leader board based on the size of the game played and the score.
 */

 package edu.wm.cs.cs301.connectn.model;

 import java.util.Scanner;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileWriter;
 import java.io.IOException;
 
 public class Leaderboard {
	 File leaderboardFile = new File("resources/leaderboard.txt");
	 Scanner fileReader;
	 String[] records = new String[3];
 
	 /*
	  * Constructor for the leader board. 
	  * Attempts to read in data from the leader board file. If the file does not exist, it creates a new file and writes the default values.
	  */
	 public Leaderboard() {
		 try {
			 fileReader = new Scanner(leaderboardFile);
			 for (int idx = 0; idx < 3; idx++)
				 records[idx] = fileReader.nextLine();
		 } catch (FileNotFoundException e) {
			 createFile();
			 try {
				 fileReader = new Scanner(leaderboardFile);
				 for (int idx = 0; idx < 3; idx++)
					 records[idx] = fileReader.nextLine();
			 } catch (FileNotFoundException e1) {
				 System.out.println("Failed to find leaderboard file");
				 e1.printStackTrace();
			 }
		 }
	 }

	 public String[] getRecords() {
		 return records;
	 }

	 /*
	  * Creates a new leader board file with default values.
	  */
	 public void createFile() {
		 try {
			 leaderboardFile.createNewFile();
			 FileWriter fileWriter = new FileWriter("resources/leaderboard.txt");
			 fileWriter.write("small,-1,\nmedium,-1,\nlarge,-1,");
			 fileWriter.close();
		 } catch (IOException e) {
			 System.out.println("Failed to create a leaderboard file.");
			 e.printStackTrace();
		 }
	 
	 }
 
	 /*
	  * Updates the leader board with the new high score.
	  * 
	  * @param size the size of the game played
	  * @param score the score of the game
	  * @param name the name of the player
	  */
	 public void updateLeaderboard(String size, int score, String name) {
		score -= 1; // decrement score to match turn number
		 // map size to index
		 int idx = -1;
		 if (size.equals("small"))
			 idx = 0;
		 else if (size.equals("large"))
			 idx = 2;
		 else if (size.equals("medium") || size.length() != 1) // medium
			 idx = 1;
		 // update records
		 int currentHighscore = Integer.parseInt(records[idx].split(",")[1]);
		 if (currentHighscore < 0 || score < currentHighscore) {
			 // prompt user for their name
			 System.out.println("You have the new high score! Please enter your name: ");
			 records[idx] = records[idx].split(",")[0] + "," + score + "," + name;
		 }
		 // write to leader board
		 try {
			 FileWriter fileWriter = new FileWriter("resources/leaderboard.txt");
			 fileWriter.write(records[0] + "\n" + records[1] + "\n" + records[2]);
			 fileWriter.close();
		 } catch (IOException e) {
			 System.out.println("An error occurred.");
			 e.printStackTrace();
		 }
	 }
 }