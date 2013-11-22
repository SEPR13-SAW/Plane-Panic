package com.planepanic.game.gfx;

import com.planepanic.game.model.orders.Vector2d;

import lombok.Getter;
import lombok.Setter;

/**
 * An abstract class used to represent objects which can be drawn to the screen.
 * @author Thomas Cheyney, Jonathan
 */
public abstract class Drawable {
	private final double MAXIMUM_CLOSE_DISTANCE = 0.001;

	@Getter @Setter protected Vector2d position;

	public Drawable(Vector2d position) {
		this.position = position;
	}

	public Drawable() {
		this(new Vector2d());
	}

	/**
	 * Draw on 2d view
	 */
	public abstract void draw2d();
	
	/**
	 * Draw on 2.5/3d view
	 */
	public abstract void draw3d();

	public double distanceTo(Drawable d) {
		return Math.sqrt((position.getX()-d.position.getX())*(position.getX()-d.position.getX()) + (position.getY()-d.position.getY())*(position.getY()-d.position.getY()));
	}

	public boolean closeEnough(Drawable d) {
		return distanceTo(d) < MAXIMUM_CLOSE_DISTANCE;
	}
}
