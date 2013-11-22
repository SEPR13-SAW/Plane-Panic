package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;

public final class RelativeHeading extends Order {
	@Getter private final double startAngle, delta;

	public RelativeHeading(double startAngle, double delta) {
		this.startAngle = startAngle;
		this.delta = delta;
	}

	@Override
	public boolean isComplete(Plane plane) {
		return plane.getPosition().angleCloseEnough(startAngle + delta);
	}

	@Override
	public void tick(Plane plane) {
		//TODO
	}

}
