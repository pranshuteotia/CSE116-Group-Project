package edu.buffalo.cse116.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.buffalo.cse116.Board.Mode;
import edu.buffalo.cse116.TEMP_GUI;

public class TwoPlayerHandler implements ActionListener {
	
	private TEMP_GUI gui;
	
	public TwoPlayerHandler(TEMP_GUI gui) {
	
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		this.gui.getFrame().dispose();
		
		if (this.gui.getBoard().getMode() == Mode.TWO_TEAM) {
			
			new TEMP_GUI().startGame();
			return;
		}
		
		
		new TEMP_GUI().startGame();
	}
}
