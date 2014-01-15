package com.planepanic.game.model;

/**
 * A class to represent Entry points.
 * 
 * @author Mantas
 */
public final class ExitPoint extends Waypoint {

	public ExitPoint(Vector2d position) {
		super(position, "E");
	}

	public void removePlane(Plane plane) {
		// TODO make it do something
		System.out.println("plane removed");
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		if (Waypoint.getVia() == null && Plane.getSelected() != null) {
			Waypoint.setVia(this);
			Waypoint.setFlyBy(false);
		}
		else if (Waypoint.getVia() != this) {

			Waypoint.setTarget(this);
		}
		return true;
	}
}
