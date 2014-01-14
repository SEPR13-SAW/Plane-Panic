package com.planepanic.game.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public final class Vector2d {
	private static final double MAXIMUM_CLOSE_ANGLE = Math.PI / 80;

	@Getter @Setter private double x, y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d() {
		this(0, 0);
	}

	public double getAngle() {
		return Math.atan2(-this.y, this.x);
	}

	public boolean angleCloseEnough(double angle) {
		return (Math.abs(angle - this.getAngle()) + Math.PI * 2) % (Math.PI * 2) < Vector2d.MAXIMUM_CLOSE_ANGLE;
	}

	public void applyChange(Vector2d velocity) {
		this.x += velocity.getX();
		this.y += velocity.getY();
	}

	public double getLength() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
	}

	/**
	 * Calculates the squared distance between two given points, at the moment
	 * works in 2d, but can easily be extended to work in 3d
	 * 
	 * @param location The other point
	 * @return The distance between the points
	 */
	public double distanceFrom(Vector2d location) {
		return (location.getX() - this.getX()) * (location.getX() - this.getX()) + (location.getY() - this.getY()) * (location.getY() - this.getY());
	}

	@Override
	protected Vector2d clone() {
		return new Vector2d(this.x, this.y);
	}

	public static Vector2d fromAngle(double r) {
		double s = 0.01;
		return new Vector2d(s * Math.cos(r), -s * Math.sin(r));
	}

	public Vector2d add(Vector2d v) {
		return new Vector2d(this.x + v.x, this.y + v.y);
	}

	public Vector2d sub(Vector2d v) {
		return new Vector2d(this.x - v.x, this.y - v.y);
	}

	public Vector2d mul(double d) {
		return new Vector2d(this.x * d, this.y * d);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2d)) {
			return false;
		}
		Vector2d other = (Vector2d) obj;
		return other.getX() == getX() && other.getY() == getY();
	}

}
