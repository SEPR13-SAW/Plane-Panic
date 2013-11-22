package com.planepanic.game.model.orders;

import lombok.Getter;
import lombok.Setter;

public final class Vector2d {
	private final double MAXIMUM_CLOSE_ANGLE = 0.001;

	@Getter @Setter private double x, y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d() {
		this(0, 0);
	}

	public double getAngle() {
		return Math.atan2(y, x);
	}

	public boolean angleCloseEnough(double angle) {
		return (((Math.abs(angle - getAngle()) + (Math.PI*2)) % (Math.PI*2)) < MAXIMUM_CLOSE_ANGLE);
	}
}
