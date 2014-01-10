package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;

public final class FlyOver extends Order {
	@Getter private final Waypoint waypoint;

	public FlyOver(Plane plane, Waypoint waypoint) {
		super(plane);
		this.waypoint = waypoint;
	}

	@Override
	public boolean isComplete() {
		return this.getPlane().closeEnough(this.waypoint);
	}

	@Override
	public void tick() {
		double target = this.waypoint.getPosition().sub(this.getPlane().getPosition()).getAngle();
		double pa = this.getPlane().getVelocity().getAngle();
		double a = target - pa;
		while (a > Math.PI) {
			a -= Math.PI * 2;
		}

		if (a >= 0) {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa));
		} else {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa + Math.PI));
		}
	}

	@Override
	public void start() {

	}

}
