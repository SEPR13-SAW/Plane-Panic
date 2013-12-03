package com.planepanic.game;

import lombok.Getter;

/**
 * Starting Class with Enumerated Values for Readability. Allows for reading and altering of the Current Game State.
 * 
 * @author Ben Leyland
 */

public class StartingClass {

	private enum GameState {
		RUNNING, PAUSED, GAMEOVER, INITIAL;	
	}

	private enum Difficulty {
		EASY, MEDIUM, HARD;
	}
	
	@Getter private GameState currentGameState = GameState.INITIAL;
	@Getter private final Difficulty difficulty;

	public StartingClass(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public void startGame() {
		this.currentGameState = GameState.RUNNING;
	}

	public void gameOver() {
		this.currentGameState = GameState.GAMEOVER;
	}
	
	public void pauseGame() {
		this.currentGameState = GameState.PAUSED;
	}
	
	public void reInitialise() {
		this.currentGameState = GameState.INITIAL;
	}
	
	
}
