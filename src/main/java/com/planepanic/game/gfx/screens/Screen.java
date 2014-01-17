package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.DrawThread;

/**
 * Abstract screen One screen is displayed at a time
 * 
 * @author Thomas
 * 
 */
public abstract class Screen {

	public Screen() {
		DrawThread.getInstance().clearScreen();
	}

	public void resize() {};

	public void tick() {};

}
