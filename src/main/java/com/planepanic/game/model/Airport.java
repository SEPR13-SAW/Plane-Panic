
package com.planepanic.game.model;

import java.util.ArrayList;

import com.planepanic.game.model.orders.Vector2d;
/**
 * A class to represent Entry points.
 * 
 * @author Mantas
 */
public final class Airport extends PointOfInterest{
	private ArrayList<Plane> planeList = new ArrayList<Plane>();
	
	public Airport(Vector2d position) {
		super(position);
	}
	
	public void addPlane(){
		this.planeList.add(new Plane(null, 0, 0));
		// TODO make it do something
		
	}

	@Override
	public void draw2d() {
		// TODO Auto-generated method stub

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
