package com.planepanic.game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent Airport Entry points.
 * 
 * @author Mantas, Jonathan
 */
public final class Airport extends PointOfInterest {
	private List<Plane> planeList = new ArrayList<>();

	public Airport(Vector2d position) {
		super(position);
	}

	public void addPlane(Plane plane) {
		this.planeList.add(plane);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}
}
