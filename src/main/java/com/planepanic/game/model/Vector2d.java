package com.planepanic.game.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public final class Vector2d {
	private final double MAXIMUM_CLOSE_ANGLE = 0.01;

	@Getter @Setter private double x, y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d() {
		this(0, 0);
	}

	public double getAngle() {
		return Math.atan2(this.y, this.x);
	}

	public boolean angleCloseEnough(double angle) {
		return (Math.abs(angle - this.getAngle()) + Math.PI * 2) % (Math.PI * 2) < this.MAXIMUM_CLOSE_ANGLE;
	}

	public void applyChange(Vector2d velocity) {
		this.x += velocity.getX();
		this.y += velocity.getY();
	}

	@Override
	protected Vector2d clone() {
		return new Vector2d(this.x, this.y);
	}

	public static Vector2d fromAngle(double r) {
		double s = 0.01;
		return new Vector2d(-s * Math.sin(r), s * Math.cos(r));
	}

}
