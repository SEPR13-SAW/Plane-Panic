package com.planepanic.game;

import lombok.Getter;
import lombok.Setter;

/**
 * Player class containing name and score
 * 
 * @author Jonathan, Steven, Thomas
 */

public class Player {
	@Getter @Setter private int score = 0;
	@Getter private final String name;

	public Player(String name) {
		this.name = name;
	}
}
