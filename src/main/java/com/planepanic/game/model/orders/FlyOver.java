package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Waypoint;

public final class FlyOver extends Order {
	@Getter private final Waypoint waypoint;

	public FlyOver(Waypoint waypoint) {
		this.waypoint = waypoint;
	}

	@Override
	public boolean isComplete(Plane plane) {
		return plane.closeEnough(this.waypoint);
	}

	@Override
	public void tick(Plane plane) {
		// TODO
	}

}
