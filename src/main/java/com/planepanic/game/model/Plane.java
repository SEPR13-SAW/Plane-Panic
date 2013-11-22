package com.planepanic.game.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Plane class.
 * @author Jonathan
 */
public final class Plane {
	@Getter private final PlaneType type;
	@Getter private final int passengers;
	@Getter @Setter private double fuel, velocity;

	public Plane(PlaneType type, int passengers, double fuel) {
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;

		this.velocity = 0;
	}
}
