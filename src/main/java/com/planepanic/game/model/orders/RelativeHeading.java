package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

public final class RelativeHeading extends Order {
	@Getter private final double startAngle, delta;

	public RelativeHeading(double startAngle, double delta) {
		this.startAngle = startAngle;
		this.delta = delta;
	}

	@Override
	public boolean isComplete(Plane plane) {
		return plane.getPosition().angleCloseEnough(this.startAngle + this.delta);
	}

	@Override
	public void tick(Plane plane) {
		double pa = plane.getVelocity().getAngle();
		double a = this.startAngle + this.delta - pa;
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
