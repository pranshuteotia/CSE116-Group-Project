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

public class ThreePlayerBoard extends Board {
	
	public ThreePlayerBoard() {
		
		super.mode = Mode.THREE_TEAM;
		
		super.locationsInGrid = new ArrayList<Location>();
		super.gameWords = new ArrayList<String>();
		super.count = new HashMap<String, Integer>();
		
		super.whichTeamWon = new HashMap<String, Boolean>();
		super.whichTeamWon.put("RED", false);
		super.whichTeamWon.put("BLUE", false);
		super.whichTeamWon.put("GREEN", false);
		
		super.count.put("RED AGENT", 6);
		super.count.put("BLUE AGENT", 5);
		super.count.put("GREEN AGENT", 5);
		super.count.put("INNOCENT BYSTANDER", 7);
		super.count.put("ASSASSIN", 2);
		
		
		listOfPeople = new ArrayList<Person>();
		 Person assassin01 = new Person("ASSASSIN");
         Person assassin02 = new Person("ASSASSIN");
        
         listOfPeople.add(assassin01);
         listOfPeople.add(assassin02);
		
		for (int i = 0; i < 6; i++) {
			
			Person redAgent = new Person("RED AGENT");
			listOfPeople.add(redAgent);
		}
		for (int i = 0; i < 5; i++) {
			
			Person blueAgent = new Person("BLUE AGENT");
			Person greenAgent = new Person("GREEN AGENT");
			
			listOfPeople.add(blueAgent);
			listOfPeople.add(greenAgent);
		}
		for (int i = 0; i < 7; i++) {
			
			Person innBy = new Person("INNOCENT BYSTANDER");
			listOfPeople.add(innBy);
		}
		
		try {
			
			for (String str : Files.readAllLines(Paths.get("GameWords1.txt"))) {
				
				super.gameWords.add(str);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Collections.shuffle(super.gameWords);
		RandomCodeNames = new ArrayList<String>();
		for(int i = 0; i < 25; i++) {
			RandomCodeNames.add(super.gameWords.get(i));
		}
		
		for (int i = 0; i < 25; i++) {
			
			Location location = new Location(listOfPeople.get(i), RandomCodeNames.get(i));
			super.locationsInGrid.add(location);
		}
	}
	
	/**
     * This checks to see if the guess is an agent
     * @param guess is the guess that one of the players thinks is the agent
     * @return will return true if the guess is an agent or false if it is not the agent
     */
    public boolean checkGuess(String guess) {
       
        boolean agentFound = false;
   
        for(int i=0; i<super.locationsInGrid.size(); i++) {
       
            if(super.locationsInGrid.get(i).getCodeName().equals(guess)) {
                String person = super.locationsInGrid.get(i).getPerson().getPersonType();
           
                int c = super.count.get(person);
                super.count.put(person, --c);
           
                super.locationsInGrid.get(i).setRevealed(true);
           
                if(person.equals("RED AGENT") || person.equals("BLUE AGENT") || person.equals("GREEN AGENT"))
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
       
        else if(this.count.get("GREEN AGENT") == 0) {
            w = 3;
        }
       
        return w;
    }
   
    /**
     * This sets the instance variable teamTurn to be equal to whatever the value of the parameter is
     * @param turn is true or false depending on whose turn it was
     */
    public void setTeamTurn(int turn) {
       
        JOptionPane.showMessageDialog(null, "It is now " + ((turn == 0) ? "red" : (turn == 1) ?  "blue" : "green") + " team's turn!");
        teamTurn=turn;
    }
	
}
