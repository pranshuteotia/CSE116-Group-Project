package edu.buffalo.cse116.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.buffalo.cse116.Board;
import edu.buffalo.cse116.TEMP_GUI;
import edu.buffalo.cse116.ThreePlayerGUI;
import edu.buffalo.cse116.Board.Mode;

/**
 * 
 * @author Joey
 *
 *The ResignBtnHandler ends the current teams turn.
 *
 */

public class ResignBtnHandler implements ActionListener{
	
	private TEMP_GUI _g;
	private Board _b;
	
	public ResignBtnHandler(TEMP_GUI g, Board b) {
		_g = g;
		_b = b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Board board = _b;
		int turn;
    	if (board.getMode() == Mode.TWO_TEAM) {
    		
    		turn = board.getTeamTurn() == 0 ? 1 : 0;
    	} else {
    		
    		turn = board.getTeamTurn() == 0 ? 1 : board.getTeamTurn() == 1 ? 2 : 0;
    		ThreePlayerGUI threePlayerGUI = ((ThreePlayerGUI) _g);
    		if(turn == 0 && threePlayerGUI.teamsOut.contains("RED"))
    			turn = 1;
    		
    		else if(turn ==1 && threePlayerGUI.teamsOut.contains("BLUE"))
    			turn = 2;
    		
    		else if(turn == 2 && threePlayerGUI.teamsOut.contains("GREEN"))
    			turn = 0;
    		
    	}
    	
        board.setTeamTurn(turn);
        _g.assignButtonsForSpyMaster();
	}

}
