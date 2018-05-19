package edu.buffalo.cse116;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.buffalo.cse116.grid.Location;

public class GUI {
	
	private boolean disableClick; //disable the click in the grid for when the spymaster is playing so they can't click the buttons.
	
	private Board board;
	
//	Menu Stuff
	
	private JFrame frame;
	
	private GridBagLayout gbl;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	
	private JMenuItem newGameItem, quitProgramItem;
	
	private JButton[][] buttons;
	
	private JTextField hintField, countField;
	
	private JButton submitButton;
	
	private ArrayList<Location> locations;
	
	public GUI() {
		
		this.frame = new JFrame();
		
		this.gbl = new GridBagLayout();
		
		this.frame.setLayout(this.gbl);
		
		this.frame.setSize(new Dimension(1000, 500));
		this.frame.setTitle("Phase 2 Project");
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new JMenuBar();
		
		this.fileMenu = new JMenu("File");
		
		this.newGameItem = new JMenuItem("Start New Game");
		this.newGameItem.addActionListener(
				
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
//					Runs when Start New Game is pressed
					startGame();
				}
			}
		);
		this.fileMenu.add(this.newGameItem);
		
		this.quitProgramItem = new JMenuItem("Quit Program");
		this.quitProgramItem.addActionListener(
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
//					Runs when Quit Program is pressed
					System.exit(0);
				}
			}
		);
		this.fileMenu.add(this.quitProgramItem);
		
		this.menuBar.add(this.fileMenu);
		
		this.frame.setJMenuBar(this.menuBar);
		
//		Set frame visible at the end
		this.frame.setVisible(true);
	}
	
	private void startGame() {
		
		for (Component c : this.frame.getContentPane().getComponents()) {
			
			remove(c);
		}
		
		this.board = new Board();
		
		this.buttons = new JButton[5][5];
		
		this.board.setTeamTurn(0);
		
		this.disableClick = true;
		this.createButtonGrid();
	}
	
	private void createButtonGrid() {
		
		locations = this.board.getLocationsInGrid();
		Collections.shuffle(locations);
		
		this.gridForSpyMaster();
		
	}
	
	private void displayForTeam(String hint, String count) {
		
		JLabel 	tempHintLabel = new JLabel("CLUE: " + hint),
				tempCountLabel = new JLabel("COUNT: " + count)
		;
		
		JButton tempResignTurnButton = new JButton("Resign Turn");
		
		tempResignTurnButton.addActionListener(
				
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					board.setTeamTurn(board.getTeamTurn() == 0 ? 1 : 0);
					gridForSpyMaster();
				}
			}
		);
		
		this.setSize(tempHintLabel, new Dimension(200, 30));
		this.setSize(tempCountLabel, new Dimension(200, 30));
		this.setSize(tempResignTurnButton, new Dimension(200, 30));
		
		this.add(tempHintLabel, 0, 5);
		this.add(tempCountLabel, 1, 5);
		this.add(tempResignTurnButton, 2, 6);
		
		for (int y = 0; y < 5; y++) {
			
			for (int x = 0; x < 5; x++) {
				
				JButton button = this.buttons[y][x];
				Location locForButton = board.getLocationsInGrid().get(y * 5 + x);
				
				if (locForButton.getRevealed()) {
					
					button.setText(locForButton.getPerson().shortenedPersonType());
				} else {
					
					button.setForeground(Color.BLACK);
					button.setText(locForButton.getCodeName());
				}
			}
		}
	}
	
	private void setSize(JComponent c, Dimension d) {

		c.setPreferredSize(d);
		c.setMinimumSize(d);
	}
	
	private void add(Component c, int x, int y) {
		
		GridBagConstraints gbc = this.gbl.getConstraints(this.frame);
		
		gbc.gridx = x;
		gbc.gridy = y;
		
		this.frame.add(c, gbc);
		
		this.frame.revalidate(); //Redraws the frame
	}
	
	private void remove(Component c) {
		
		this.frame.getContentPane().remove(c);
		this.frame.revalidate();
	}
	
	private boolean isSpymasterInputValid(String clue, String count) {
		
		if (clue.length() == 0 || !this.board.isValid(clue)) {
			
			return false;
		}
		
		try {
			
			Integer.parseInt(count);
		} catch (NumberFormatException e) {
			
			return false;
		}
		
		return true;
	}
	
	private void gridForSpyMaster() {
		for (int y = 0; y < 5; y++) {
					
					final int yClone = y;
					
					for (int x = 0; x < 5; x++) {
						
						final int xClone = x;
						
						int i = 5 * y + x;
						GridBagConstraints gbc = this.gbl.getConstraints(this.frame);
						gbc.gridy = y;
						gbc.gridx = x;
						
						Location locToUse = locations.get(i);
						
						String shortenedPT = locToUse.getPerson().shortenedPersonType();
						
						JButton button = new JButton(shortenedPT + " " + locToUse.getCodeName());
					button.setBackground(Color.LIGHT_GRAY);
					button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					switch (shortenedPT) {
					case "R":
						button.setForeground(Color.RED);
						break;
					case "B":
						button.setForeground(Color.BLUE);
						break;
					case "IB":
						button.setForeground(Color.GREEN);
						break;
					case "A":
						button.setForeground(Color.MAGENTA);
						break;
					}
					
					this.setSize(button, new Dimension(200, 30));
					
					button.addActionListener(
							
						new ActionListener() {
		
							@Override
							public void actionPerformed(ActionEvent arg0) {
								
								if (disableClick) {
									
									return;
								}
								
								Location buttonLoc = board.getLocationsInGrid().get(yClone * 5 + xClone);
								
								if (buttonLoc.getRevealed() == true) {
									
									return;
								}
								
		//							TODO button logic, idk what to do here.
								
								buttonLoc.setRevealed(true);
								
								board.setTeamTurn(board.getTeamTurn() == 0 ? 1 : 0);
							}
						}
					);
					
					this.buttons[y][x] = button;
					
					this.frame.add(button, gbc);
					this.frame.revalidate(); //Redraws the frame
				}
		}
		
		hintField=new JTextField("");
		this.setSize(hintField, new Dimension(200, 30));
		this.add(hintField, 2, 5);
		
		countField=new JTextField("");
		this.setSize(countField, new Dimension(200,30));
		this.add(countField, 4, 5);
		
		this.submitButton = new JButton("Submit Clue & Count");
		this.setSize(this.submitButton, new Dimension(200, 30));
		this.add(this.submitButton, 4, 6);
		this.submitButton.addActionListener(
				
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					String 	hint = hintField.getText().toUpperCase(),
							count = countField.getText()
					;
					
					if (!isSpymasterInputValid(hint, count)) {
						
						JOptionPane.showMessageDialog(null, "Your hint is invalid or your count is not a number!");
						return;
					}
					
					hintField.setText("");
					countField.setText("");
					
					remove(hintField);
					remove(countField);
					remove(submitButton);
					
					disableClick = false;
					displayForTeam(hint, count);
				}
			}
		);
	}
	
	private void update() {
		frame.pack();
		frame.setSize(1000, 500);
	}
}

