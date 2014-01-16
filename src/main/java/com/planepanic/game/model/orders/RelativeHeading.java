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
public final class RelativeHeading extends Order {
	@Getter private final double delta;
	@Getter private double startAngle;

	public RelativeHeading(Plane plane, double delta) {
		super(plane);
		this.delta = delta;
	}

	@Override
	public void start() {
		this.startAngle = this.getPlane().getVelocity().getAngle();
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
		return "Change relative Heading by " + this.getDelta() * 180 / Math.PI;
	}

}
