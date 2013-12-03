package com.planepanic.game;

import lombok.Getter;
import lombok.Setter;


/**
 * Player class containing name and score
 * 
 * @author Steven Whitelock
 */

public class Player {

	@Getter @Setter private int score = 0;
	@Getter @Setter private String playerName = "Doge";

}
