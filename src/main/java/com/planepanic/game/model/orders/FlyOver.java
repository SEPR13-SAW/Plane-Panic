package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
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
		double target = waypoint.getPosition().sub(plane.getPosition()).getAngle();
		double pa = plane.getVelocity().getAngle();
		double a = target - pa;
		while (a > Math.PI) {
			a -= Math.PI * 2;
		}

		if (a >= 0) {
			plane.getVelocity().applyChange(Vector2d.fromAngle(pa));
		} else {
			plane.getVelocity().applyChange(Vector2d.fromAngle(pa + Math.PI));
		}
	}

}
