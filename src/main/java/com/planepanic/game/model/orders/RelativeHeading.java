package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

public final class RelativeHeading extends Order {
	@Getter private final double delta;
	@Getter private double startAngle;

	public RelativeHeading(Plane plane, double delta) {
		super(plane);
		this.delta = delta;
	}

	@Override
	public void start() {
		this.startAngle = this.getPlane().getAngle();
	}

	@Override
	public boolean isComplete() {
		return this.getPlane().getVelocity().angleCloseEnough(this.startAngle + this.delta);
	}

	@Override
	public void tick() {
		double pa = this.getPlane().getVelocity().getAngle();
		double a = this.startAngle + this.delta - pa;
		while (a > Math.PI) {
			a -= Math.PI * 2;
		}

		if (a >= 0) {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa));
		} else {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa + Math.PI));
		}
	}

}
