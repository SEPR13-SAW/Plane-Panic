package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

/**
 * Order a plane to turn by a specified amount
 * 
 * @author Jonathan, Mantas, Thomas
 * 
 */
public final class AbsoluteHeading extends Order {
	@Getter private final double delta;

	public AbsoluteHeading(Plane plane, double delta) {
		super(plane);
		this.delta = delta;
	}

	@Override
	public void start() {
		// empty
	}

	@Override
	public boolean isComplete() {
		return this.getPlane().getVelocity().angleCloseEnough(this.delta);
	}

	@Override
	public void tick() {
		double pa = this.getPlane().getVelocity().getAngle();
		double a = this.delta - pa;
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

	@Override
	public String getHumanReadable() {
		return "Change absolute Heading by " + this.getDelta() * 180 / Math.PI;
	}

}
