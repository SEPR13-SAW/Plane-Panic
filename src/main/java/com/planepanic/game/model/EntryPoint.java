package com.planepanic.game.model;

import java.util.Random;

/**
 * A class to represent Entry points.
 * 
 * @author Mantas
 */
public final class EntryPoint extends PointOfInterest {

	public EntryPoint(Vector2d position) {
		super(position);
	}

	public Plane addPlane() {
		return Plane.randomPlane(new Random(), this.position.clone());
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean onClick() {
		return false;
	}
}
