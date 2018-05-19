package edu.buffalo.cse116;

import java.util.ArrayList;

public class ThreePlayerGUI extends TEMP_GUI {
	
	/**
	 * The teamsOut ArrayList stores the teams who have "found" the assassin and are out.
	 */
	public ArrayList<String> teamsOut;
	
	public ThreePlayerGUI() {
		
		super();
		
		super.board = new ThreePlayerBoard();
		this.teamsOut = new ArrayList<String>();	
		super.getFrame().setTitle(super.getFrame().getTitle() + " - 3 player mode");
	}
}
