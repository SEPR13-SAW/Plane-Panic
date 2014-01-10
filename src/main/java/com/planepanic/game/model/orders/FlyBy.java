package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Waypoint;

public final class FlyBy extends Order {
	@Getter private final Waypoint waypoint;

	public FlyBy(Plane plane, Waypoint waypoint) {
		super(plane);
		this.waypoint = waypoint;
	}

	@Override
	public boolean isComplete() {
		return this.getPlane().closeEnough(this.waypoint);
	}

	@Override
	public void tick() {
		// TODO
	}

	@Override
	public void start() {

	}

}
