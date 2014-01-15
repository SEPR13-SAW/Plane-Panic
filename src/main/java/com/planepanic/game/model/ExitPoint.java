package com.planepanic.game.model;

/**
 * A class to represent Entry points.
 * 
 * @author Mantas
 */
public final class ExitPoint extends SimplePointOfInterest {

	public ExitPoint(Vector2d position) {
		super(position);
	}

	public void removePlane() {
		// TODO make it do something
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}
}
