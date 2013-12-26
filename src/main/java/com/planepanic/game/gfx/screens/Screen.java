package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.DrawThread;

public abstract class Screen {

	public Screen() {
		DrawThread.getInstance().clearScreen();
	}

	public void resize() {};

	public void tick() {};

}
