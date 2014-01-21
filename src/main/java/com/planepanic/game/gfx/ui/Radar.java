package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.model.Vector2d;

/**
 * Draws the background for the scene
 * 
 * @author Jonathan, Mantas, Steven, Thomas
 * 
 */
public class Radar extends Image {

	public Radar() {
		super(Resources.GRASS, new Vector2d(448, 360));
		this.setPriority(-0.9f);

	}

	@Override
	public void draw3d() {
		// Empty.
	}

	@Override
	public boolean onClick() {
		return false;
	}

	@Override
	protected boolean keyPress(int key) {
		return false;
	}

}
