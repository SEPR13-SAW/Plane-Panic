package com.planepanic.game.model;

import lombok.Getter;
import lombok.Setter;

/**
 * An abstract class used to represent objects which can be drawn to the screen.
 * @author Jonathan
 */
public abstract class Drawable {
	@Getter @Setter private double x, y;

	public Drawable(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Drawable() {
		this(0, 0);
	}
}
