package com.planepanic.game;

import lombok.Getter;
import lombok.Setter;


/**
 * Player class containing name and score
 * 
 * @author Steven Whitelock
 */

public class Player {

	@Getter @Setter public int score = 0;
	@Getter @Setter public String playerName = "Doge";

}
