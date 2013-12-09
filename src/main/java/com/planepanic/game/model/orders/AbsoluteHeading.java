package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

public final class AbsoluteHeading extends Order {
	@Getter private final double angle;

	public AbsoluteHeading(double angle) {
		this.angle = angle;
	}

	@Override
	public boolean isComplete(Plane plane) {
		return plane.getVelocity().angleCloseEnough(this.angle);
	}

	@Override
	public void tick(Plane plane) {
		double pa = plane.getVelocity().getAngle();
		double a = this.angle - pa;
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
