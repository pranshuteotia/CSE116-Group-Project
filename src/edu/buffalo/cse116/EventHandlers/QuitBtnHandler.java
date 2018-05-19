package edu.buffalo.cse116.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.buffalo.cse116.TEMP_GUI;

/**
 * 
 * @author Joey
 *
 *The QuitBtnHandler completely closes the program.
 *
 */
public class QuitBtnHandler implements ActionListener{
	
	public QuitBtnHandler() {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}

}
