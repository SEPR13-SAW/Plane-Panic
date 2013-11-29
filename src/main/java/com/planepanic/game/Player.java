package com.planepanic.game;

public class Player {

	/**
	 *Player class containing name and score
	 * 
	 * @author Steven Whitelock
	 */
	
	public int score = 0;
	public String playerName = "Doge";
	
	public void changeScore(int newValue) {
		score = newValue;
	}

	public void changeName(String newName) {
		playerName = newName;
		
	}
}