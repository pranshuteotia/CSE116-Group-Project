package edu.buffalo.cse116.grid;

import edu.buffalo.cse116.person.Person;

/**
 * Location class which stores the person, a codeName, and whether or not the person is revealed.
 * @author From 0 to 1 Team.
 */
public class Location {
	
	/**
	 * This boolean represents whether or not the person assigned to this Location has been revealed.
	 */
	private boolean revealed;
	
	/**
	 * This String represents the code name assigned to this location.
	 */
	private String codeName;
	
	/**
	 * This Person represents the person at this location.
	 */
	private Person person;
	/**
	 * this assigns the instance variables to the values of the parameters
	 * @param person will be assigning a value of person to instance variable person
	 * @param codeName will be holding a String that will be assigned to instance variable codeName 
	 */
	public Location(Person person, String codeName) {
		
		this.person = person;
		this.codeName = codeName;
		this.revealed = false;
	}
	/**
	 * This sets the instance variable revealed equal to whatever the value of the parameter revealed is
	 * @param revealed will be holding the new value for this.revealed
	 */
	public void setRevealed(boolean revealed) {
		
		this.revealed = revealed;
	}
	/**
	 * This will return whatever the value at the instance variable revealed is
	 * @return will return the value instance variable revealed is
	 */
	public boolean getRevealed() {
		
		return this.revealed;
	}
	/**
	 * This will return whatever the value at instance variable codeName is
	 * @return will return the value at the instance variable codeName
	 */
	public String getCodeName() {
		return codeName;
	}
	/**
	 * This will return whatever the value at instance variable person is
	 * @return will return the value at the instance variable person
	 */
	public Person getPerson() {
		return person;
	}
}
