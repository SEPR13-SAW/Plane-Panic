package com.planepanic.game.model;

import java.util.ArrayList;
import java.util.List;

import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;

/**
 * A class to represent Airport Entry points.
 * 
 * @author Mantas, Jonathan
 */
public final class Airport extends PointOfInterest {
	private List<Plane> planeList = new ArrayList<>();
	private Image sprite;

	public Airport(Vector2d position) {
		super(position);
		this.sprite = new Image(Resources.AIRPORT, position);
	}

	public void addPlane(Plane plane) {
		this.planeList.add(plane);
	}

	@Override
	public void draw2d() {
		this.sprite.draw2d();
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
