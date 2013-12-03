package com.planepanic.game;

import lombok.Getter;
import lombok.Setter;

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
	
	public GameState startGame() {
		this.currentGameState = GameState.RUNNING;
		return this.currentGameState;
	}

	public GameState gameOver() {
		this.currentGameState = GameState.GAMEOVER;
		return this.currentGameState;
	}
	
	public GameState pauseGame() {
		this.currentGameState = GameState.PAUSED;
		return this.currentGameState;
	}
	
	public GameState reInitialise() {
		this.currentGameState = GameState.INITIAL;
		return this.currentGameState;
	}
	
	
}
