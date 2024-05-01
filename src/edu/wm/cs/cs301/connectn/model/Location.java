/**
 * Represents on location on the game board.
 * 
 * Checks the location is empty, and can return the value at a location.
 * Overrides the equals method to compare two locations based on their token values.
 */
package edu.wm.cs.cs301.connectn.model;

public class Location {
	private Character symbol;
	
	/*
	 * Default constructor for the location.
	 */
	public Location() {
		this.symbol = Character.valueOf(' ');
	}

	/*
	 * Constructor for the location based on another location.
	 */
	public Location(Location otherLoc) { // copy other location
		this.symbol = otherLoc.getToken();
	}
	
	public boolean isEmpty() {
		// check if symbol is empty 
		return symbol.equals(Character.valueOf(' '));
	}
	
	public void setValue(String tokenVal) {
		symbol = Character.valueOf(tokenVal.charAt(0));
	}
	
	public Character getToken() {
		return symbol;
	}

	public String getTokenVal() {
		return symbol.toString();
	}
	
	/*
	 * Overrides the equals method to compare two locations based on their token values.
	 */
	@Override
	public boolean equals(Object other) { 
		// return false if not the correct type
		if (!(other instanceof Location))
            return false;
		// return false if either position is empty
		Location otherLoc = (Location) other;
        if (symbol.charValue() == ' ' || otherLoc.getToken().charValue() == ' ') 
        	return false;
        // return the two values being equal
        return symbol.charValue() == otherLoc.getToken().charValue();
	}
	
}
