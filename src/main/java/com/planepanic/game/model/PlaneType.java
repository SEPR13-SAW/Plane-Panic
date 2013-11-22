package com.planepanic.game.model;

import lombok.Getter;

/**
 * Plane type class, defines immutable statistics such as maximum fuel/passenger capacity and velocity.
 * @author Jonathan
 *
 */
public enum PlaneType {
	BOEING_747_400("Boeing 747-400", 524, 181965, 291),
	;

	@Getter private final String name;
	@Getter private final int maxPassengers;
	@Getter private final double maxFuel, maxVelocity;

	/**
	 * 
	 * @param name Nicely formatted name of the plane model.
	 * @param maxPassengers Maximum passenger capacity.
	 * @param maxFuel Maximum fuel capactity, measured in litres.
	 * @param maxVelocity Maximum velocity measured in meters per second.
	 */
	PlaneType(String name, int maxPassengers, double maxFuel, double maxVelocity) {
		this.name = name;
		this.maxPassengers = maxPassengers;
		this.maxFuel = maxFuel;
		this.maxVelocity = maxVelocity;
	}
}
