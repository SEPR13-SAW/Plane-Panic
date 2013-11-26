package com.planepanic.game.model;

import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.orders.Vector2d;

import lombok.Getter;

/**
 * A class to represent waypoints.
 * @author Jonathan
 */
public final class Waypoint extends Drawable {
	@Getter private final String name;

	public Waypoint(Vector2d position, String name) {
		super(position);
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
