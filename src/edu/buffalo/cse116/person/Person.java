package edu.buffalo.cse116.person;

/**
 * Person class which stores the type of the person.
 * @author From 0 to 1 Team.
 */
public class Person {
	
	/**
	 * This String represents the type of the person (i.e. the role the person plays).
	 * For example: personType may be assigned to "RED AGENT".
	 */
	private String personType;
	
	/**
	 * this assigns the value of the instance variable to the value of the parameter
	 * @param personType is the variable that will be assigned to the instance variable personType
	 */
	public Person(String personType) {
		
		this.personType = personType;
	}
	/**
	 * This will return the value of whatever the variable at instance variable personType is
	 * @return will return the value at the instance variable personType
	 */
	public String getPersonType() {
		
		return this.personType;
	}
	
	public String shortenedPersonType() {
		
		switch (this.personType) {
		case "RED AGENT":
			return "R";
		case "BLUE AGENT":
			return "B";
		case "GREEN AGENT":
			return "G";
		case "INNOCENT BYSTANDER":
			return "IB";
		case "ASSASSIN":
			return "A";
			default:
				return "error";
		}
	}
}
