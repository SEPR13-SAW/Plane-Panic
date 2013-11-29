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
		return currentGameState;
	}
	
	public GameState startGame() {
		currentGameState = GameState.RUNNING;
		return currentGameState;
	}

	public GameState gameOver() {
		currentGameState = GameState.GAMEOVER;
		return currentGameState;
	}
	
	public GameState pauseGame() {
		currentGameState = GameState.PAUSED;
		return currentGameState;
	}
	
	public GameState reInitialise() {
		currentGameState = GameState.INITIAL;
		return currentGameState;
	}
	
	
}
}
