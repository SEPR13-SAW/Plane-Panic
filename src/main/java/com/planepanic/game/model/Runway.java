package com.planepanic.game.model;

import lombok.Getter;

import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.orders.Vector2d;

/**
 * A class to represent runways.
 * 
 * @author Jonathan
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
		// TODO Auto-generated method stub

	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}
}
