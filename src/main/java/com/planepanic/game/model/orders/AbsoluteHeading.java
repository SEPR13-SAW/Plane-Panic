package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

public final class AbsoluteHeading extends Order {
	@Getter private final double angle;

	public AbsoluteHeading(Plane plane, double angle) {
		super(plane);
		this.angle = angle;
	}

	@Override
	public void start() {

	}

	@Override
	public boolean isComplete() {
		return this.getPlane().getVelocity().angleCloseEnough(this.angle);
	}
	
	@Override
	public void tick() {
		double pa = this.getPlane().getVelocity().getAngle();
		double a = this.angle - pa;
		while (a > Math.PI) {
			a -= Math.PI * 2;
		}
		while (a < -Math.PI) {
			a += Math.PI * 2;
		}
		
		if (a >= 0) {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa + Math.PI / 2));
		} else {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa - Math.PI / 2));
		}
	}

}
