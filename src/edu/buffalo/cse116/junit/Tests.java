package edu.buffalo.cse116.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import edu.buffalo.cse116.Board;
import edu.buffalo.cse116.Board.Mode;
import edu.buffalo.cse116.ThreePlayerBoard;
import edu.buffalo.cse116.ThreePlayerGUI;
import edu.buffalo.cse116.grid.Location;
import edu.buffalo.cse116.person.Person;

public class Tests {

	@Test
	public void testDefineBoard() {
		Board def=new Board();
		int boardSpaces=def.getLocationsInGrid().size();
		assertEquals(25,boardSpaces);
	}

	@Test
	public void testReadCodenamesAndStore() {
		Board gameBoard = new Board();

		ArrayList<String> gameWords = gameBoard.getGamewords();

		//Assert the gamewords arraylist exists.
		assertNotNull("Expected to be Not Null but was Null", gameWords);

		//Assert that every element in the arraylist is not null.
		for(int i=0; i<gameWords.size(); i++)
			assertNotNull("Expected to be Not Null but was Null", gameWords.get(i));
	}

	@Test
	public void testSelect25RandomCodeName() {

		Board gameBoard = new Board();

		ArrayList<String> randomCodeNames = gameBoard.getRandomCodeNames();

		//		Assert the randomly generated code names list exists.
		assertNotNull(randomCodeNames);

		for (String codeName : randomCodeNames) {

			//			Assert each element in the list is not null.
			assertNotNull(codeName);
		}

		//		Assert the size is 25.
		assertEquals(randomCodeNames.size(), 25);
	}

	@Test
	public void testRandomAssignments() {
		Board b = new Board();
		//      Make sure there is 9 RedAgents, 8 Blue Agents, 7 Inn bystanders, and 1 assassin
		ArrayList<Person> listOfPeople = b.getlistOfPeople();

		int redAgentCount = 0;
		int blueAgentCount = 0;
		int bystandersCount = 0;
		int assassinCount = 0;

		for (int i = 0; i < listOfPeople.size(); i++) {
			Person person = listOfPeople.get(i);

			if (person.getPersonType().equals("RED AGENT")) {
				redAgentCount++;
			}

			else if (person.getPersonType().equals("BLUE AGENT")) {
				blueAgentCount++;
			}

			else if (person.getPersonType().equals("INNOCENT BYSTANDER")) {
				bystandersCount++;
			}

			else if (person.getPersonType().equals("ASSASSIN")) {
				assassinCount++;
			}
		}

		assertEquals(9, redAgentCount);
		assertEquals(8, blueAgentCount);
		assertEquals(7, bystandersCount);
		assertEquals(1, assassinCount);
	}

	@Test
	public void testGameStart() {
		Board begin=new Board();

		assertEquals(0, begin.getTeamTurn());
		ArrayList<Location> locationInGrid= begin.getLocationsInGrid();
		assertEquals(25, locationInGrid.size());
		for(int i=0; i<locationInGrid.size(); i=i+1) {
			assertFalse(locationInGrid.get(i).getRevealed());
			assertNotNull(locationInGrid.get(i).getCodeName());
			assertNotNull(locationInGrid.get(i).getPerson());

		}  
	}

	@Test
	public void testIsLegalMethod() {

		Board gameBoard = new Board();

		ArrayList<String> codeNamesBeingUsed = gameBoard.getRandomCodeNames();

		//		No persons have been revealed yet so every code name should be invalid.
		boolean checkInvalid = gameBoard.isValid(codeNamesBeingUsed.get(0));

		assertFalse(checkInvalid);

		String valid = "memes";
		//		Just making sure that this String is valid and isn't in the codeNames list.
		while (codeNamesBeingUsed.contains(valid)) {
			valid += "memes";
		}

		//		The String valid is not in the random code names list which means it should be valid.
		boolean checkValid = gameBoard.isValid(valid);

		assertTrue(checkValid);

		ArrayList<Location> locationsInGrid = gameBoard.getLocationsInGrid();
		Location loc = locationsInGrid.get(0);
		loc.setRevealed(true);
		//		The String shouldNowBeValid should be valid even though it's a codeName since the Person at the Location has been revealed.
		String shouldNowBeValid = loc.getCodeName();

		assertTrue(gameBoard.isValid(shouldNowBeValid));
	}

	@Test
	public void checkLocationCodenameSelected() {
		Board board = new Board();
		HashMap<String, Integer> count = board.getCount();
		int expectedRedCount = count.get("RED AGENT") - 1, expectedBlueCount = count.get("BLUE AGENT") - 1;

		board.setCount(count.get("RED AGENT")-1, count.get("BLUE AGENT"));
		int actualRed = board.getCount().get("RED AGENT");
		assertEquals(expectedRedCount, actualRed);

		board.setCount(count.get("RED AGENT"), count.get("BLUE AGENT")-1);
		int actualBlue = board.getCount().get("BLUE AGENT");
		assertEquals(expectedBlueCount, actualBlue);

		ArrayList<Location> loc = board.getLocationsInGrid();
		loc.get(0).setRevealed(true);
		loc.get(6).setRevealed(true);

		for(int i=0; i<loc.size(); i++) {
			if(i==0 || i==6) {
				assertTrue(loc.get(i).getRevealed());
			}

			else {
				assertFalse(loc.get(i).getRevealed());
			}
		}
	}

	@Test
	public void checkIfAnyTeamWon() {
		Board gameBoard = new Board();

		int c = gameBoard.checkState();
		assertEquals("Expected 0 (Nobody Won...yet)", 0, c);

		gameBoard.setCount(0, -1);
		c = gameBoard.checkState();
		assertEquals("Expected 1 (Red Team Won)", 1, c);

		gameBoard.setCount(-2, 0);
		c = gameBoard.checkState();
		assertEquals("Expected 2 (Blue Team won)", 2, c);

		gameBoard.setCount(-1, -1);
		c = gameBoard.checkState();
		assertEquals("Expected 0 (Nobody Won...yet", 0, c);
	}

	@Test
	public void checkWinningTeamCorrect() {

		Board gameBoard = new Board();
		ArrayList<Location> locations = gameBoard.getLocationsInGrid();

		int stopRed = 0;

		//		Set up the game in a winning state.
		for (Location loc : locations) {

			String personType = loc.getPerson().getPersonType();

			if (personType.equals("ASSASSIN")) {

				loc.setRevealed(true);
			} else if (!personType.equals("INNOCENT BYSTANDER")) {

				if (personType.equals("RED AGENT")) {

					stopRed++;

					//					I use stopRed to make sure that the red team has 1 agent that still isn't found.
					//					Blue team has all of its agents found.
					if (stopRed == 9) {

						continue;
					}
				}

				gameBoard.checkGuess(loc.getCodeName());
			}
		}

//		The blue team wins because it has found all its agents while the red team still has 1 agent that isn't found.
		boolean blueTeamWins = gameBoard.checkWinner().equals("Blue");

		assertTrue(blueTeamWins);
	}
	
	@Test
	public void WinningState() {
		
		Board alpha = new Board();
		
		boolean bravo = alpha.getWhichTeamWon().get("RED");
		boolean charlie = alpha.getWhichTeamWon().get("BLUE");
		boolean delta = false;
		if (alpha.getMode() == Mode.THREE_TEAM) {
			
			delta = alpha.getWhichTeamWon().get("GREEN");
		}
		if (bravo || charlie || delta) {
			
			assertTrue(alpha.inWinningState());
		} else {
			
			assertFalse(alpha.inWinningState());
		}
		
	}


	
	//Creates a List containing randomly generated assignments for 
	//each of the 6 Red Agents, 5 Blue Agents, 5 Green Agents, 7 Innocent Bystanders, 
	//and 2 Assassins [3 points]	
	@Test
	public void PersonAssignmentTest() {
	
	ThreePlayerBoard T = new ThreePlayerBoard();
	ArrayList<Person> TestList = T.getlistOfPeople();
	int RA = 0;
	int BA = 0;
	int GA = 0;
	int IB = 0;
	int A = 0;
	for(int i = 0; i < TestList.size(); i ++) {
		if(TestList.get(i).getPersonType().equals("RED AGENT")) {
			RA++;
		}
		if(TestList.get(i).getPersonType().equals("BLUE AGENT")) {
			BA++;
		}
		if(TestList.get(i).getPersonType().equals("GREEN AGENT")) {
			GA++;
		}
		if(TestList.get(i).getPersonType().equals("INNOCENT BYSTANDER")) {
			IB++;
		}
		if(TestList.get(i).getPersonType().equals("ASSASSIN")) {
			A++;
		}
	}
	assertEquals(6,RA);
	assertEquals(5,BA);
	assertEquals(5,GA);
	assertEquals(7,IB);
	assertEquals(2,A);
	
	
	assertEquals(25,TestList.size());
	}


	@Test 
	public void CheckForTwoAssassin() {
		Board b=new Board();
		
		String s="RED";
		b.setWhichTeamWon(s);
		
		if(b.getMode()==Mode.THREE_TEAM) {
		
			if(b.getCount().get("ASSASSIN")==0) {
				assertTrue(b.getWhichTeamWon().get(s));
			}
		}
//				
	}
	
	@Test
	public void predictTurns() {
		Board i1 = new Board();
		ThreePlayerGUI i2 = new ThreePlayerGUI();
		
		i1.setMode(Mode.THREE_TEAM);
		i2.teamsOut.add("GREEN");
		i1.setTeamTurn(1);
		
		int currentTurn = i1.getTeamTurn();
		int prediction = -1;
		
		if(i1.getMode() == Mode.TWO_TEAM) {
			if(currentTurn == 0)
				prediction = 1;
			
			else
				prediction = 0;
		}
		
		else {
			if(currentTurn == 0) {
				if(i2.teamsOut.contains("BLUE"))
					prediction = 2;
				
				else
					prediction = 1;
			}
			
			if(currentTurn == 1) {
				if(i2.teamsOut.contains("GREEN"))
					prediction = 0;
				
				else
					prediction = 2;
			}
			
			else {
				if(i2.teamsOut.contains("RED"))
					prediction = 1;
				
				else
					prediction = 0;
			}
		}
		
		assertEquals(0, prediction);
		//Not Anthony's Test Case.
	}
}
