package com.planepanic.game.model;

import lombok.Getter;

import com.planepanic.game.gfx.Drawable;

/**
 * A class to represent runways.
 * 
 * @author Jonathan, Thomas
 */
public final class Runway extends Drawable {
	@Getter private final String name;
	@Getter private final Vector2d end;

	public Runway(Vector2d start, Vector2d end, String name) {
		super(start);
		this.end = end;
		this.name = name;
	}

	@Override
	public void draw2d() {
		// TODO Draw runway.
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
