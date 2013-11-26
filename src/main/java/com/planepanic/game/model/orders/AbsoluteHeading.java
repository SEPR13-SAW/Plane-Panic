package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;

public final class AbsoluteHeading extends Order {
	@Getter private final double angle;

	public AbsoluteHeading(double angle) {
		this.angle = angle;
	}

	@Override
	public boolean isComplete(Plane plane) {
		return plane.getPosition().angleCloseEnough(this.angle);
	}

	@Override
	public void tick(Plane plane) {
		// TODO
	}

}
