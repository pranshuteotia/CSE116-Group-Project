package edu.buffalo.cse116.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.buffalo.cse116.TEMP_GUI;

/**
 * 
 * @author Joey
 *
 *The NewGameHandler begins a new game.
 *
 */
public class NewGameHandler implements ActionListener{
	
	private TEMP_GUI _g;
	
	public NewGameHandler(TEMP_GUI g) {
		_g = g;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(_g.getOnce()) {
			_g.setOnce();
			JOptionPane.showMessageDialog(null, "BTW this game was a joint effort. Just letting you know, in case you are like, REALLY gullible.");
		}
		_g.startGame();
	}

}
