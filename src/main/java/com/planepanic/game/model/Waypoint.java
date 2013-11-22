package com.planepanic.game.model;

import com.planepanic.game.gfx.Drawable;

import lombok.Getter;

/**
 * A class to represent waypoints.
 * @author Jonathan
 */
public final class Waypoint extends Drawable {
	@Getter private final String name;

	public Waypoint(double x, double y, String name) {
		super(x, y);
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
}
