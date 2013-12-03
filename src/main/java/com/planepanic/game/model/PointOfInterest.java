package com.planepanic.game.model;

import com.planepanic.game.gfx.Drawable;

/**
 * An abstract class to represent Points of Interest.
 * 
 * @author Mantas
 */

public abstract class PointOfInterest extends Drawable {

	public PointOfInterest(Vector2d position) {
		super(position);
	}

	@Override
	public abstract void draw2d();

	@Override
	public abstract void draw3d();

	@Override
	public abstract boolean onClick();

}
