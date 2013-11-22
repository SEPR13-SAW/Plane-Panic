package com.planepanic.game.gfx;

import lombok.Getter;
import lombok.Setter;

/**
 * An abstract class used to represent objects which can be drawn to the screen.
 * @author Thomas Cheyney, Jonathan
 */
public abstract class Drawable {
	@Getter @Setter protected double x, y;

	public Drawable(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Drawable() {
		this(0, 0);
	}

	/**
	 * Draw on 2d view
	 */
	public abstract void draw2d();
	
	/**
	 * Draw on 2.5/3d view
	 */
	public abstract void draw3d();
}
