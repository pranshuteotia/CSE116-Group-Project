
	package edu.buffalo.cse116.EventHandlers;

	import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.buffalo.cse116.Board.Mode;
import edu.buffalo.cse116.TEMP_GUI;
import edu.buffalo.cse116.ThreePlayerGUI;

	public class ThreePlayerHandler implements ActionListener{

		TEMP_GUI _X;
		
		public ThreePlayerHandler(TEMP_GUI x) {
			_X = x;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			_X.getFrame().dispose();
			
			if (_X.getBoard().getMode() == Mode.THREE_TEAM) {
				
				new ThreePlayerGUI().startGame();
				return;
			}
			
			
			new ThreePlayerGUI().startGame();
		}

	}


