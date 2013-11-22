package com.planepanic.game.model;

import lombok.Getter;

public final class Waypoint extends Drawable {
	@Getter private final String name;

	public Waypoint(double x, double y, String name) {
		super(x, y);
		this.name = name;
	}
}
