package edu.buffalo.cse116.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.buffalo.cse116.TEMP_GUI;
/**
 * 
 * @author Joey
 *
 *FlipTableHandler is for when you have a short temper and feel like you are going to lose terribly so you want to let off some steam. 
 *
 */
public class FlipTableHandler implements ActionListener{
	
private TEMP_GUI _g;
	
	public FlipTableHandler(TEMP_GUI g) {
		_g = g;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int yes = JOptionPane.YES_OPTION;
		int no = JOptionPane.NO_OPTION;
	int alpha = JOptionPane.showConfirmDialog(null,"Are you sure you want to flip the table?");
	
	if (alpha == yes) {
		int bravo = JOptionPane.showConfirmDialog(null,"Are you sure you're sure you want to flip the table?");
		if (bravo == yes) {
			int charlie = JOptionPane.showConfirmDialog(null,"Think about the mess it's going to make, are you sure?");
			if (charlie == yes) {
				int delta = JOptionPane.showConfirmDialog(null,"Did you even think about the children?");
				if (delta == yes) {
					int echo = JOptionPane.showConfirmDialog(null,"And you can live with that?");
					if (echo == yes) {
						int hotel = JOptionPane.showConfirmDialog(null,"I hope you know that you are a monster, you are worse than the guy who forgot the children.  Did you know that?");
						if (hotel == yes) {
							JOptionPane.showMessageDialog(null, "Well there is obviously no reasoning with you, YOU FREAK!!!! you can flip the table");
							_g.startGame();
							int india = JOptionPane.showConfirmDialog(null,"Well look at what you did, are you happy now?");
							if (india == yes) {
								JOptionPane.showMessageDialog(null, "Wow you probably play socer with new born kittens and like to use puppies for batting practice");
							}
							if (india == no) {
								int kilo = JOptionPane.showConfirmDialog(null,"Yeah you don't feel good now do you");
								if (kilo == no) {
									JOptionPane.showMessageDialog(null, "well it's too late to go back to your game now but i'll tell you what i'll start another one.");
									_g.startGame();
								}
								if (kilo == yes ) {
									JOptionPane.showMessageDialog(null, "You have to be the most sadistic person on this planet have fun living out your sad life you sadistic loser, yeah I said loser the other team wins.");
								}
							}
						}
						if (hotel == no) {
							JOptionPane.showMessageDialog(null, "Well you are, a big scary one and not even a cool one like Mike Wazowski, more like a looser like Randall Boggs so have fun losing the rest of the game.");
						}
					}
					if (echo == no) {
						int juliett = JOptionPane.showConfirmDialog(null,"Well i'm glad that you have some common sense unlike SOME PEOPLE, would you like to return to the game now? ");
						if (juliett == no) {
							int lima = JOptionPane.showConfirmDialog(null,"Even after thinking about the children you still want hit no?");
							if (lima == yes) {
								int mike = JOptionPane.showConfirmDialog(null,"You sicken me you really do, did you know that children are suffering now because of your choices");
								if (mike == yes) {
									int oscar = JOptionPane.showConfirmDialog(null, "You do? Really? Can you see them? Looking all sad and alone just hoping you don't flip the tabkle, and you still want to put them though hell.");
									if (oscar == yes) {
										JOptionPane.showMessageDialog(null, "Well I am not going to let you do that I am going to return you to your game and make you Suffer and I hop the children stay on your subconcious.");
									}
									if (oscar == no) {
										JOptionPane.showMessageDialog(null, "I hope you know that you made the right decision and that because of you only 2 children had to suffer have fun with the rest of the game i'm sure the children want you to lose.");
									}
								}
								if (mike == no) {
									JOptionPane.showMessageDialog(null, "Well they are. A lot of them. like 6 of them. maybe even more I don't know, I just hope you will make better choices in your next game of Codenames");
									_g.startGame();
								}
							}
							if (lima == no|| lima == yes) {
								int november = JOptionPane.showConfirmDialog(null,"So you'll return to the game?");
								if (november == yes) {
									JOptionPane.showMessageDialog(null, "Great I hope you know that no children had to suffer because of the choices you made!");
								}
								if (november == no) {
									JOptionPane.showMessageDialog(null, "Yes you are, but you had to make a child suffer so I hope your happy");
								}
							}
							
						}
						if (juliett == yes) {
							JOptionPane.showMessageDialog(null, "Well good luck to you my good sir on winning your game.");
						}
					}
				}
				else if (delta == no) {
					int foxtrot = JOptionPane.showConfirmDialog(null,"How on earth could you forget about the children, and you still want to flip the table");
					if (foxtrot == yes) {
						int golf = JOptionPane.showConfirmDialog(null,"So you are going to be that guy huh?");
					
					if (golf == yes ) {
						JOptionPane.showMessageDialog(null, "You forget about the children, you want to flip the table well I am not going to let you do it so have fun loosing or trying to flip the table again.");
					}
					if (golf == no) {
						JOptionPane.showMessageDialog(null, "You're not ok well back to the game.");
					}
				}
				}
			}
		}
	}
	
	}
	
	
}
