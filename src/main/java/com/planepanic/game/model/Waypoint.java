package com.planepanic.game.model;

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
}
