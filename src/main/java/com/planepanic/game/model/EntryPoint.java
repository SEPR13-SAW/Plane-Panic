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
		return Plane.randomPlane(new Random(), this.position);
	}

	@Override
	public void draw2d() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw3d() {


	}

	@Override
	public boolean onClick() {
		return false;
	}
}
