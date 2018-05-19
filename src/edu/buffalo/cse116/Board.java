package edu.buffalo.cse116;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JOptionPane;

import edu.buffalo.cse116.grid.Location;
import edu.buffalo.cse116.person.Person;

/**
 * The Board class which stores a location grid.
 * @author From 0 to 1 Team.
 */
public class Board {
	
	public enum Mode {
		
		TWO_TEAM(),
		THREE_TEAM()
		;
		
		private Mode() {}
	}
	
	/**
	 * Stores the mode of the game (either two team or three team).
	 */
	protected Mode mode;
	
	/**
	 * locationsInGrid stores all the locations in the grid.
	 */
	protected ArrayList<Location> locationsInGrid;
	/**
	 * gameWords stores every gameWord read from the file selected.
	 * 
	 */
	protected ArrayList<String> gameWords;
	
	/**
	 * listOfPeople that contains the assignments of types of person.
	 * 
	 */
	protected ArrayList<Person> listOfPeople;
	
	/**
	 * RandomCodeNames stores 25 random code names from gameWords.
	 */
	protected ArrayList<String> RandomCodeNames;
	/**
	 * count is a HashMap which maps a personType to a count.
	 * For example:
	 * RED AGENT is mapped to 9 at the start.
	 * BLUE AGENT is mapped to 8 at the start.
	 * etc.
	 */
	protected HashMap<String, Integer> count;
	/**
	 * Keeps track of the team which won.
	 */
	protected HashMap<String, Boolean> whichTeamWon;
	/**
	 * True if it is the red team's turn, False otherwise.
	 */
	protected int teamTurn; 
	
	/**
	 * Regex pattern to compare to a clue to check if it is valid or not.
	 */
	private final String patten = "[A-Za-z0-9] {1,37}"; 
	
	/**
	 * This allocates 25 different location instances and gives each location a job and a codename
	 */
	public Board() {
		
		this.mode = Mode.TWO_TEAM;
		
		this.locationsInGrid = new ArrayList<Location>();
		this.gameWords = new ArrayList<String>();
		this.count = new HashMap<String, Integer>();
		
		this.whichTeamWon = new HashMap<String, Boolean>();
		this.whichTeamWon.put("RED", false);
		this.whichTeamWon.put("BLUE", false);
		
		this.count.put("RED AGENT", 9);
		this.count.put("BLUE AGENT", 8);
		this.count.put("INNOCENT BYSTANDER", 7);
		this.count.put("ASSASSIN", 1);
		
		
		listOfPeople = new ArrayList<Person>();
		Person assassin = new Person("ASSASSIN");
		
		listOfPeople.add(assassin);
		
		for (int i = 0; i < 9; i++) {
			
			Person redAgent = new Person("RED AGENT");
			listOfPeople.add(redAgent);
		}
		for (int i = 0; i < 8; i++) {
			
			Person blueAgent = new Person("BLUE AGENT");
			listOfPeople.add(blueAgent);
		}
		for (int i = 0; i < 7; i++) {
			
			Person innBy = new Person("INNOCENT BYSTANDER");
			listOfPeople.add(innBy);
		}
	
		
		try {
			
			for (String str : Files.readAllLines(Paths.get("GameWords1.txt"))) {
				
				this.gameWords.add(str);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Collections.shuffle(this.gameWords);
		RandomCodeNames = new ArrayList<String>();
		for(int i = 0; i < 25; i++) {
			RandomCodeNames.add(this.gameWords.get(i));
		}
		
		for (int i = 0; i < 25; i++) {
			
			Location location = new Location(listOfPeople.get(i), RandomCodeNames.get(i));
			this.locationsInGrid.add(location);
		}
		
		teamTurn = 0;
		
	}
	/**
	 * This method checks to see if the clue is valid or not 
	 * @param a is what the clue that is being checked
	 * @return will show if the clue is valid or not (true is valid and false is invalid)
	 */
	public boolean isValid(String a) { 				//Changed the return type from "String" to "Boolean"
		for(int i=0; i<locationsInGrid.size(); i++) {
			Location l=locationsInGrid.get(i);
			
			if(l.getCodeName().equals(a) && !l.getRevealed()) {
				 return false;
			}
			
			else if(a.matches(patten))
				return false;
			
		}
		return true;
	}
	/**
	 * This checks to see if the guess is an agent
	 * @param guess is the guess that one of the players thinks is the agent
	 * @return will return true if the guess is an agent or false if it is not the agent
	 */
	public boolean checkGuess(String guess) {
		
		boolean agentFound = false;
		
		for(int i=0; i<this.locationsInGrid.size(); i++) {
			
			if(this.locationsInGrid.get(i).getCodeName().equals(guess)) {
				String person = this.locationsInGrid.get(i).getPerson().getPersonType();
				
				int c = this.count.get(person);
				this.count.put(person, --c);
				
				this.locationsInGrid.get(i).setRevealed(true);
				
				if(person.equals("RED AGENT") || person.equals("BLUE AGENT"))
					agentFound = true;
			}
			
			else
				continue;
		}
		
		if(agentFound == true)
			return true;
		
		else
			return false;
	}
	
	/**
	 * This will check and see if a team has won the game
	 * @return will return 0 if no one has won yet, 1 if red team has won, 2 if blue team has won
	 */
	public int checkState() {
		
		int w = 0;
		
		if(this.count.get("RED AGENT") == 0)
			w = 1;
		
		else if(this.count.get("BLUE AGENT") == 0)
			w = 2;
		
		else if (this.mode == Mode.THREE_TEAM && this.count.get("GREEN AGENT") == 0) {
			
			w = 3;
		}
		
		return w;
	}
	
	/**
	 * This sets the instance variable teamTurn to be equal to whatever the value of the parameter is
	 * @param turn is true or false depending on whose turn it was
	 */
	public void setTeamTurn(int turn) {
		
		JOptionPane.showMessageDialog(null, "It is now " + ((turn == 0) ? "red" : "blue") + " team's turn!");
		teamTurn=turn;
	}
	/**
	 * this will declare a winner when the assassin is revealed
	 * @return who returned the assassin
	 */
	public String checkWinner() { // i think that we should change this method to include the assassin
		int a = checkState();
		if (a == 1) {
			
			return "Red";
		} else if (a == 2) {
			return "Blue";
		} else if (a == 3) {
			
			return "Green";
		}
		return "Nobody";
	}
	
	/**
	 * Check if the game is in a winning state.
	 * @return if the game is in a winning state.
	 */
	public boolean inWinningState() {
		
		return !checkWinner().equals("Nobody");
	}
	
	 /**
     * Returns the locationInGrid ArrayList.
     * @return ArrayList containing all the elements of the Board.
     */
	public ArrayList<Location> getLocationsInGrid() {
		
		return this.locationsInGrid;
	}
	
	/**
     * Returns the gameWords ArrayList.
     * @return ArrayList containing all the gamewords   .
     */
	public ArrayList<String> getRandomCodeNames() {
		
		return this.RandomCodeNames;
	}
	
	/**
	 * Get the gamewords
	 * @return the gamewords.
	 */
	public ArrayList<String> getGamewords() {
		
		return this.gameWords;
	}
	
	public ArrayList<Person> getlistOfPeople(){
		
		return listOfPeople;
	}
	

    /**
     * Change the count of either the red team's or blue team's agents.
     * @param r Red Team's agents.
     * @param b Blue Team's agents.
     */
    public void setCount(int r, int b) {
        int redTeamCount = (r<0)?9:r; 
        int blueTeamCount = (b<0)?8:b;
        
       
        this.count.put("RED AGENT", redTeamCount);
        this.count.put("BLUE AGENT", blueTeamCount);
    }
   
    /**
     * Return the HashMap count.
     * @return A HashMap containing the count of the Red team's agents, Blue team's agents, bystanders, and the assassin.
     */
    public HashMap<String, Integer> getCount() {
        return this.count;
    }
    /**
     * @return Returns true if it's red teams turn, returns false if it's blue's.
     */
    public int getTeamTurn() {
    	return teamTurn;
    }
    /**
     * Sets whichTeamWon instance variable
     * @param s - Key
     * @param val - Value
     */
    public void setWhichTeamWon(String s) {
        this.whichTeamWon.put(s, true);
    }
    /**
     * @return whichTeamWon instance variable
     */
    public HashMap<String, Boolean> getWhichTeamWon() {
        return this.whichTeamWon;
    }
    
    /**
     * Gets the mode of the game (either two or three team)
     * @return mode of the game
     */
    public Mode getMode() {
    	
    	return this.mode;
    }
    
    /**
     * Sets the mode of the game to be any of the values in the Mode enum
     * @param m The value to set the Mode mode to.
     */
	public void setMode(Mode m) {
		this.mode = m;
	}
}
