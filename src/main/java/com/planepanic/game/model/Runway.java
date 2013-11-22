package com.planepanic.game.model;

import lombok.Getter;

/**
 * A class to represent runways.
 * @author Jonathan
 */
public final class Runway extends Drawable {
	@Getter private final String name;
	@Getter private final double endX, endY;

	public Runway(double startX, double startY, double endX, double endY, String name) {
		super(startX, startY);
		this.endX = endX;
		this.endY = endY;
		this.name = name;
	}
}
