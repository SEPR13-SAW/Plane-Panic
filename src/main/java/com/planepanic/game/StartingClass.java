package com.planepanic.game;

/**
 * Starting Class with Enumerated Values for Readability. Allows for reading and altering of the Current Game State.
 * 
 * @author Ben Leyland
 */

public class StartingClass {

	private enum GameState {
		RUNNING, PAUSED, GAMEOVER, INITIAL;	
	}
	
	private GameState currentGameState = GameState.INITIAL;
	
	public GameState getCurentState() {
		return this.currentGameState;
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
