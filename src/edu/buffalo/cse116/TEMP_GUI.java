package edu.buffalo.cse116;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;*/
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.buffalo.cse116.Board.Mode;
import edu.buffalo.cse116.EventHandlers.ClueCountBtnHandler;
import edu.buffalo.cse116.EventHandlers.FlipTableHandler;
import edu.buffalo.cse116.EventHandlers.NewGameHandler;
import edu.buffalo.cse116.EventHandlers.QuitBtnHandler;
import edu.buffalo.cse116.EventHandlers.ResignBtnHandler;
import edu.buffalo.cse116.EventHandlers.ThreePlayerHandler;
import edu.buffalo.cse116.EventHandlers.TwoPlayerHandler;
import edu.buffalo.cse116.colors.Colors;
import edu.buffalo.cse116.grid.Location;
 

public class TEMP_GUI { 
	
	/**
	 * disableCick disable the click on all the JButton in the Spymaster interface	
	 */
	private boolean disableClick;
	/**
	 * turnsLeft tracks how many words a team has left to pick
	 */
	private int turnsLeft;
	
	private int threePlayerMagicNumber = 2;
	
	/**
	 * Board Instance
	 */
	protected Board board;
	/**
	 * JFrame for the Game
	 */
	private JFrame frame;
	/**
	 * Game MenuBar
	 */
	private JMenuBar menuBar;
	/**
	 * fileMenu
	 */
	private JMenu fileMenu;
	/**
	 * each JMenuItem represents the item in the JMenu
	 */
	private JMenuItem newGameItem, quitProgramItem,flipTableItem;
	/**
	 * buttons reprsents all the buttons in the grid given by a two dimension array
	 */
	private JButton[][] buttons;
	/**
	 * "enter" JButton
	 */
	private JButton enter;
	/**
	 * hint JTextField, count JTextField
	 */
	private JTextField hint, count;
	/**
	 * HINT JLabel
	 * Count JLabel
	 */
	private JLabel HINT, COUNT, clue, dispCount;
	/**
	 * boardPanel contains the grid, testPanel is the panel at the bottom, titlePanel is initialized once the game run
	 */
	private JPanel boardPanel, testPanel, titlePanel;
	/**
	 * ArrayList contains all the locations objects
	 */
	private ArrayList<Location> locations;
	/**
	 * Secret stuff
	 */
	private boolean once;
	public JMenuItem threePlayerItem;
	/**
	 * Instantiate stuff
	 */
	public TEMP_GUI() {		
		once = true;
		frame = new JFrame("CodeNames");
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		
		JMenuItem twoPlayerItem = new JMenuItem("Start New Game - Two Players");
		twoPlayerItem.addActionListener(new TwoPlayerHandler(this));
		fileMenu.add(twoPlayerItem);

		threePlayerItem=new JMenuItem("Start New Game - Three Players");
		threePlayerItem.addActionListener(new ThreePlayerHandler(this));
		
		fileMenu.add(threePlayerItem);
		
//		newGameItem = new JMenuItem("Start New Game");
//		newGameItem.addActionListener(new NewGameHandler(this));
//		fileMenu.add(newGameItem);
//		
		quitProgramItem = new JMenuItem("Quit Program");
		quitProgramItem.addActionListener(new QuitBtnHandler());
		fileMenu.add(quitProgramItem);
		
		flipTableItem = new JMenuItem("Flip Table");
		flipTableItem.addActionListener(new FlipTableHandler(this));
		fileMenu.add(flipTableItem);
		
		
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		
		titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.setPreferredSize(new Dimension(800, 800));
		frame.add(titlePanel);
		setTitleScreen();
		
		boardPanel = new JPanel();
		testPanel = new JPanel();
		
		frame.setVisible(true);
		
		board = new Board();
	}
	
	/**
	 * Start the Game, instantiate the board and give the turn to red
	 * Select 25 random locations 
	 * Assign 25 locations to the grid
	 */
	public void startGame() {
		update();
		
		board.setTeamTurn(0);
		
		locations = board.getLocationsInGrid();
		Collections.shuffle(locations);
		buttons = new JButton[5][5];
		
		
		boardPanel.setLayout(new GridLayout(5,5,5,5));
		boardPanel.setPreferredSize(new Dimension(800,600));
		frame.add(boardPanel);
		
		
		frame.add(testPanel);
		
		
		int index = 0;
		for (int i = 0; i < 5; i++) {
			
			for (int j = 0; j < 5; j++) {
				
				JButton b = buttons[i][j] = new JButton("");
				
				Location locForButton = locations.get(index++);

				final TEMP_GUI thisInstance = this;
				
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (disableClick || locForButton.getRevealed()) {
							
							return;
						}
						
						locForButton.setRevealed(true);
				
			
						board.getCount().replace(locForButton.getPerson().getPersonType(), board.getCount().get(locForButton.getPerson().getPersonType()) - 1);
						
						turnsLeft--;
						
						//Checks for Winner
                        if (board.getMode() == Mode.TWO_TEAM) {
                            if (board.getCount().get(board.getTeamTurn() == 0 ? "RED AGENT" : "BLUE AGENT") <= 0) {
                               
                                String echo = board.getTeamTurn() == 0 ? "RED" : "BLUE";
                                board.setWhichTeamWon(echo);
                                JOptionPane.showMessageDialog(null, "The " + echo + " team has won!");
                            } else if (board.getTeamTurn() == 0 && !locForButton.getPerson().getPersonType().equals("RED AGENT") ||
                                board.getTeamTurn() == 1 && !locForButton.getPerson().getPersonType().equals("BLUE AGENT") ||
                                locForButton.getPerson().getPersonType().equals("INNOCENT BYSTANDER")){
                                String echo = "";
                               
                                char won = 'N';
                               
                                if (board.getTeamTurn() == 1) {
                                   
                                    if(board.getCount().get("RED AGENT") == 0)
                                        won = 'R';
                                }
                                else if (board.getTeamTurn() == 0) {
                                    if(board.getCount().get("BLUE AGENT") == 0)
                                        won = 'B';
                                }
                               
                                if(won == 'R') {
                                    echo = "RED";
                                    board.setWhichTeamWon(echo);
                                    JOptionPane.showMessageDialog(null,"Congratulations!!!! you found the last RED AGENT.  " + echo + " team Won!");
                                }
                               
                                else if(won == 'B') {
                                    echo = "BLUE";
                                    board.setWhichTeamWon(echo);
                                    JOptionPane.showMessageDialog(null,"Way to go champ you found the Last BLUE AGENT soooo you lost.  " + echo + " team Won!");
                                   
                                }
                               
                                else if(board.getCount().get("ASSASSIN") == 1) {
                                    JOptionPane.showMessageDialog(null, "Your turn was ended early since you picked " + locForButton.getPerson().getPersonType());
                                }
                                turnsLeft = 0;
                            }
                            

                        } else {
                           
                            if (board.getCount().get(board.getTeamTurn() == 0 ? "RED AGENT" : board.getTeamTurn() == 1 ? "BLUE AGENT" : "GREEN AGENT") <= 0) {
                               
                                String echo = board.getTeamTurn() == 0 ? "RED" : board.getTeamTurn() == 1 ? "BLUE" : "GREEN";
                                board.setWhichTeamWon(echo);
                                JOptionPane.showMessageDialog(null, "The " + echo + " team has won!");
                            } else if (board.getTeamTurn() == 0 && !locForButton.getPerson().getPersonType().equals("RED AGENT") ||
                                    board.getTeamTurn() == 1 && !locForButton.getPerson().getPersonType().equals("BLUE AGENT") ||
                                    board.getTeamTurn() == 2 && !locForButton.getPerson().getPersonType().equals("GREEN AGENT") ||
                                    locForButton.getPerson().getPersonType().equals("INNOCENT BYSTANDER")){
                                String echo = "";
                               
                                char won = 'N';
                               
                                if (board.getTeamTurn() == 1) {
                                   
                                    if(board.getCount().get("RED AGENT") == 0)
                                        won = 'R';                         
                                    else if (board.getCount().get("GREEN AGENT") == 0) {
                                       
                                        won = 'G';
                                    }
                                }
                                else if (board.getTeamTurn() == 0) {
                                    if(board.getCount().get("BLUE AGENT") == 0)
                                        won = 'B';
                                    else if (board.getCount().get("GREEN AGENT") == 0)
                                        won = 'G';
                                } else if (board.getTeamTurn() == 2) {
                                   
                                    if (board.getCount().get("RED AGENT") == 0) {
                                       
                                        won = 'R';
                                    } else if (board.getCount().get("BLUE AGENT") == 0) {
                                       
                                        won = 'B';
                                    }
                                }
                               
                                if(won == 'R') {
                                    echo = "RED";
                                    board.setWhichTeamWon(echo);
                                    JOptionPane.showMessageDialog(null,"Congratulations!!!! you found the last RED AGENT.  " + echo + " team Won!");
                                }
                               
                                else if(won == 'B') {
                                    echo = "BLUE";
                                    board.setWhichTeamWon(echo);
                                    JOptionPane.showMessageDialog(null,"Way to go champ you found the Last BLUE AGENT soooo you lost.  " + echo + " team Won!");
                                   
                                } else if (won == 'G') {
                                    echo = "GREEN";
                                    board.setWhichTeamWon(echo);
                                    JOptionPane.showMessageDialog(null,"What an unbelievable performance you found the last " + echo + " AGENT. You should feel absolutley ashamed of yourself because  " + echo + " team Won!");
                                }
                               
                                else if(board.getCount().get("ASSASSIN") == 1) {
                                    JOptionPane.showMessageDialog(null, "Your turn was ended early since you picked " + locForButton.getPerson().getPersonType());
                                }
                                turnsLeft = 0;
                            }
                        }
                       
                        // Check if Assassin count is 0
                        if(board.getCount().get("ASSASSIN") < 1 && board.getMode() == Mode.TWO_TEAM) {
                       
                        	String echo = board.getTeamTurn() == 0 ? "BLUE" : "RED";
                            board.setWhichTeamWon(echo);
                            JOptionPane.showMessageDialog(null, "Your team found the Assassin. " + echo + " team Wins!!");
                        }
                        
                        else if(board.getCount().get("ASSASSIN") < threePlayerMagicNumber && board.getMode() == Mode.THREE_TEAM) {
                        	String echo = board.getTeamTurn() == 0 ? "RED" : board.getTeamTurn() == 1 ? "BLUE" : "GREEN";
                            ThreePlayerGUI threePlayerGUI = ((ThreePlayerGUI) thisInstance);
                            threePlayerGUI.teamsOut.add(echo);
                            JOptionPane.showMessageDialog(null, echo + " team has found the Assassin and is eliminated from the game!");
                            if (threePlayerGUI.teamsOut.size() >= 2) {
                                String winner = "REDBLUEGREEN";
                                if (threePlayerGUI.teamsOut.contains("RED")) {
                                   
                                    winner = winner.replaceFirst("RED", "");
                                }
                                if (threePlayerGUI.teamsOut.contains("BLUE")) {
                                   
                                    winner = winner.replaceFirst("BLUE", "");
                                }
                                if (threePlayerGUI.teamsOut.contains("GREEN")) {
                                   
                                    winner = winner.replaceFirst("GREEN", "");
                                }
                                board.setWhichTeamWon(winner);
                                JOptionPane.showMessageDialog(null, "Both assassins were found by the other teams... " + winner + " team has won!");
                            }
                            
                            threePlayerMagicNumber--;
                        }
                       
                       
                        assignButtonsForPlayers(getClue(), "");
                       
                        if (turnsLeft <= 0) {
                           
                            if(board.getWhichTeamWon().get("RED") || board.getWhichTeamWon().get("BLUE") || (board.getMode() == Mode.THREE_TEAM && board.getWhichTeamWon().get("GREEN"))){
                                rebuildTitleScreen();
                            }
                           
       
                            else {
                            	
                            	int turn;
                            	
                            	if (board.getMode() == Mode.TWO_TEAM) {
                            		
                            		turn = board.getTeamTurn() == 0 ? 1 : 0;
                            	} else {
                            		
                            		turn = board.getTeamTurn() == 0 ? 1 : board.getTeamTurn() == 1 ? 2 : 0;
                            		ThreePlayerGUI threePlayerGUI = ((ThreePlayerGUI) thisInstance);
                            		if(turn == 0 && threePlayerGUI.teamsOut.contains("RED"))
                            			turn = 1;
                            		
                            		else if(turn ==1 && threePlayerGUI.teamsOut.contains("BLUE"))
                            			turn = 2;
                            		
                            		else if(turn == 2 && threePlayerGUI.teamsOut.contains("GREEN"))
                            			turn = 0;
                            		
                            	}
                            	
                            	disableClick = true;
                                board.setTeamTurn(turn);
                                assignButtonsForSpyMaster();
                            }
                        }
                    }
				});
			}
		}
		assignButtonsForSpyMaster();
		//frame.revalidate();
	}
	/**
	 * update() update the game
	 * give the actual hint and count to the actual JLabel
	 * Add action listener to the resign turn button 
	 * "resign turn" button give up the chance to pick
	 */
	public void assignButtonsForPlayers(String hint, String count) {
		update();
		
		if (count.length() != 0) {
			
			this.turnsLeft = Integer.valueOf(count);
		}
		
		this.disableClick = false;
		int index = 0;
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				Location locToUse = locations.get(index++);
				buttons[i][j].setText(locToUse.getCodeName());

				setButtonProperties(buttons[i][j]);
				
				if (locToUse.getRevealed()) {
					
					buttons[i][j].setText(locToUse.getPerson().getPersonType());
					
					switch (locToUse.getPerson().shortenedPersonType()) {
					case "R":
						buttons[i][j].setForeground(Color.WHITE);
						buttons[i][j].setBackground(Color.RED);
						break;
					case "B":
						buttons[i][j].setForeground(Color.BLUE);
						buttons[i][j].setBackground(Color.CYAN);
						break;
					case "G":
						buttons[i][j].setForeground(Color.GREEN);
						buttons[i][j].setBackground(Colors.DARK_GREEN);
						break;
					case "IB":
						buttons[i][j].setForeground(Color.BLACK);
						buttons[i][j].setBackground(Color.GREEN);
						break;
					case "A":
						buttons[i][j].setForeground(Color.WHITE);
						buttons[i][j].setBackground(Color.BLACK);
						break;
					}
				}
				boardPanel.add(buttons[i][j]);
			}
		}
		
		clue = new JLabel("CLUE: " + hint);
		clue.setFont(new Font("Helvetica", Font.BOLD, 14));
		dispCount = new JLabel("COUNT: " + turnsLeft);
		dispCount.setFont(new Font("Helvetica", Font.BOLD, 14));
		testPanel.add(clue);
		testPanel.add(dispCount);
		//testPanel.add(new JLabel("TURNS LEFT: " + turnsLeft));
		JButton resign = new JButton("Resign Turn");
		resign.addActionListener(new ResignBtnHandler(this, board));
		testPanel.add(resign);
	}
	
	/**
	 * creates a 5x5 grid with all the agents assassins and by standers revealed
	 */
	
	public void assignButtonsForSpyMaster() {
		update();
		disableClick = true;
		int index = 0;
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				
				Location locToUse = locations.get(index++);
				String shortenedPT = locToUse.getPerson().shortenedPersonType();
				buttons[i][j].setText(shortenedPT + " - " + locToUse.getCodeName());
				
				setButtonProperties(buttons[i][j]);
				
				switch (shortenedPT) {
				case "R":
					buttons[i][j].setForeground(Color.RED);
					break;
				case "B":
					buttons[i][j].setForeground(Color.BLUE);
					break;
				case "G":
					buttons[i][j].setForeground(Colors.DARK_GREEN);
					break;
				case "IB":
					buttons[i][j].setForeground(Color.GREEN);
					break;
				case "A":
					buttons[i][j].setForeground(Color.MAGENTA);
					break;
				}
				
				if (locToUse.getRevealed()) {
					
					switch (shortenedPT) {
					case "R":
						buttons[i][j].setForeground(Color.WHITE);
						buttons[i][j].setBackground(Color.RED);
						break;
					case "B":
						buttons[i][j].setForeground(Color.BLUE);
						buttons[i][j].setBackground(Color.CYAN);
						break;
					case "G":
						buttons[i][j].setForeground(Color.GREEN);
						buttons[i][j].setBackground(Colors.DARK_GREEN);
						break;
					case "IB":
						buttons[i][j].setForeground(Color.BLACK);
						buttons[i][j].setBackground(Color.GREEN);
						break;
					case "A":
						buttons[i][j].setForeground(Color.WHITE);
						buttons[i][j].setBackground(Color.BLACK);
						break;
					}
					
					buttons[i][j].setText(locToUse.getPerson().getPersonType());
				}
				
				boardPanel.add(buttons[i][j]);
			}
		}
		
		assignClueFields();
	}
	/**
	 * assigns the hint JTextfield and the count JTextField
	 */
	public void assignClueFields() {
		HINT = new JLabel("HINT:");
		hint = new JTextField("", 30);
		COUNT = new JLabel("COUNT:");
		count = new JTextField("", 5);
		enter = new JButton("Enter");
		enter.addActionListener(new ClueCountBtnHandler(this, board));
		
		testPanel.add(HINT);
		testPanel.add(hint);
		testPanel.add(COUNT);
		testPanel.add(count);
		testPanel.add(enter);
	}
	/**
	 * updates after every action
	 */
	public void update() {
		//frame.removeAll();
		frame.getContentPane().remove(titlePanel);
		boardPanel.removeAll();
		testPanel.removeAll();
		if (this != null) {
			frame.pack();
			frame.setSize(800, 800);
		}
	}
	/**
	 * set buttons properties
	 * @param button
	 */
	public void setButtonProperties(JButton button) {
		button.setFont(new Font("Helvetica", Font.BOLD, 14));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		//button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
		button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	}
	/**
	 * sets the title screen view settings 
	 * creates a new JLabel called title which will display "CODENAMES", etc.
	 */
	
	public void setTitleScreen() {
        JPanel toptop = new JPanel();
        toptop.setPreferredSize(new Dimension(100, 100));
       
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100, 100));
        top.setBackground(Color.BLACK);
       
        JPanel middle = new JPanel();
        middle.setBackground(Color.BLACK);
        middle.setPreferredSize(new Dimension(100, 200));
        JLabel title = new JLabel("CODENAMES");
        title.setFont(new Font("Times New Roman", Font.BOLD, 86));
        title.setForeground(Color.WHITE);
        title.setVerticalAlignment(JLabel.CENTER);
        middle.add(title);
       
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        JLabel credits = new JLabel("Created By: Only Joey.");
        credits.setFont(new Font("Arial", Font.PLAIN, 32));
        bottom.add(credits);
       
        JPanel bottombottom = new JPanel();
        bottombottom.setPreferredSize(new Dimension(100, 100));
       
        titlePanel.add(toptop);
        titlePanel.add(top);
        titlePanel.add(middle);
        titlePanel.add(bottom);
        titlePanel.add(bottombottom);
    }
   
    public void rebuildTitleScreen() {
        frame.getContentPane().remove(boardPanel);
        frame.getContentPane().remove(testPanel);
        frame.add(titlePanel);
    }
	/**if the clue length is zero i.e there' no text/words in the hint JTextField 
	 * or the clue is not a valid string meaning the word you enter exist on the board
	 * i.e. its a code name 
	 * if it returns 2 that means the count is not a number 
	 * if it return 
	 * @param clue
	 * @param count
	 * @return
	 */
	public int isSpymasterInputValid(String clue, String count) {
		
	
		String konami = "UPUPDOWNDOWNLEFTRIGHTLEFTRIGHTBASTART";
		if (clue.equals(konami)) {
			return 3;
		}
		if (clue.length() == 0 || !board.isValid(clue)) {
			
			return 1;
		}
		
		try {
			
			Integer.parseInt(count);
		} catch (NumberFormatException e) {
			
			return 2;
		}
		
		return 0;
	}
	/**
	 * returns the clue and sets the letters to all CAPS 
	 * @return
	 */
	//Getters and Setters
	public String getClue() {
		return hint.getText().toUpperCase();
	}
	/**
	 * returns the count displayed in the JTextField 
	 * @return
	 */
	public String getCount() {
		return count.getText();
	}
	/**
	 * sets the clue variable
	 * @param clue
	 */
	public void setClue(String clue) {
		hint.setText(clue);
	}
	/**
	 * sets the count to be how many words are associated with the hint
	 * @param c
	 */
	public void setCount(String c) {
		count.setText(c);
	}
	/**
    * @return the secret once
    */
   public boolean getOnce() { return once; }
  
   /**
    * Sets the secret once
    */
   public void setOnce() { once = false; }
	
   /**
    * Get the JFrame of the current gui instance.
    * @return the JFrame frame.
    */
   public JFrame getFrame() {
	   
	   return this.frame;
   }
   
   /**
    * Get the Board from the current gui instance.
    * @return the Board board.
    */
   public Board getBoard() {
	   
	   return this.board;
   }
}