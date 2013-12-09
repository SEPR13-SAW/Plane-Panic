package com.planepanic.game;

import com.planepanic.game.gfx.DrawThread;

/**
 * Example main
 * 
 * @author Thomas Cheyney
 */
public class Main {

	public static void main(String[] args) throws Exception {
		DrawThread.getInstance().changeScreen(new com.planepanic.game.gfx.screens.Main());
	}

}
