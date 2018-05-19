package edu.buffalo.cse116.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.buffalo.cse116.Board;
import edu.buffalo.cse116.TEMP_GUI;
import edu.buffalo.cse116.Board.Mode;
/**
 * 
 * @author Joey
 * 
 * The ClueCountBtnHandler it takes the input from the the two JTextFields, count and hint, and displays it on the grid.
 * it also checks to see if the count and the clue is valid.
 * 
 */
public class ClueCountBtnHandler implements ActionListener{
	private Board _b;
	
	private TEMP_GUI _g;
	
	public ClueCountBtnHandler(TEMP_GUI g, Board b) {
		_b=b;
		_g = g;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String 	hint = _g.getClue(),
				count = _g.getCount();
		int redCount=_b.getCount().get("RED AGENT");
		int blueCount=_b.getCount().get("BLUE AGENT");
		int greenCount = -7;
		
		if(_b.getMode() == Mode.THREE_TEAM)
			greenCount = _b.getCount().get("GREEN AGENT");
		
		try {
			
			Integer.parseInt(count);
		} catch (NumberFormatException e) {
			
			JOptionPane.showMessageDialog(null, "The count is invalid!  It must be a number!");
			return;
		}
		
		if(_b.getTeamTurn() == 0 ) {
			if(Integer.parseInt(count)>redCount) {
				JOptionPane.showMessageDialog(null,   "The count is more than the agents");
				return;
			}
			
		}
		else  if(_b.getTeamTurn() == 1){
			if(Integer.parseInt(count)>blueCount) {
				JOptionPane.showMessageDialog(null,   "The count is more than the agents");
				return;
			}
		}
		
		else {
			if(Integer.parseInt(count)>greenCount && greenCount != -7) {
				JOptionPane.showMessageDialog(null,   "The count is more than the agents");
				return;
			}
		}
		
		
		int retVal = _g.isSpymasterInputValid(hint, count);
		
		if(Integer.parseInt(count) == 0) {
			JOptionPane.showMessageDialog(null, "I don't want to live now. I have seen everything. Really!? '0' ?");
			_g.setCount("");
			return;
		} else if (retVal == 1) {
			JOptionPane.showMessageDialog(null, "Your hint is invalid!  Make sure it is not empty nor a codename!");
			_g.setClue("");
			return;
		} else if(retVal == 2) {
			JOptionPane.showMessageDialog(null, "Your count is not a number!  Please try again.");
			_g.setCount("");
			return;
		} else if (retVal == 3) {
			int echo = _g.getBoard().getTeamTurn();
			String foxtrot = "";
			if (echo == 0) {
				foxtrot = "RED";
			}
			else if (echo == 1) {
				foxtrot = "BLUE";
			}
			else if (echo == 2) {
				foxtrot = "GREEN";
			}
		
			JOptionPane.showMessageDialog(null, "You entered the seceret code: Congradulations the " + foxtrot + " team wins");
			
			_g.getFrame().dispose();
			new TEMP_GUI();
		}

		
		_g.setClue("");
		_g.setCount("");
		
		_g.assignButtonsForPlayers(hint, count);
	}
}
