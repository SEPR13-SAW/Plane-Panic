package com.planepanic.game.model;

import lombok.Getter;

/**
 * Plane type class, defines immutable statistics such as maximum fuel/passenger capacity and velocity.
 * 
 * @author Jonathan
 * 
 */
public enum PlaneType {
	BOEING_747_400("Boeing 747-400", 524, 181965, 291, 58390, 3.937, 1000), ;

	@Getter private final String name;
	@Getter private final int maxPassengers, score;
	@Getter private final double maxFuel, maxVelocity, mass, fuelConsumption;

	/**
	 * 
	 * @param name
	 *            Nicely formatted name of the plane model.
	 * @param maxPassengers
	 *            Maximum passenger capacity.
	 * @param maxFuel
	 *            Maximum fuel capactity, measured in litres.
	 * @param maxVelocity
	 *            Maximum velocity measured in meters per second.
	 * @param mass
	 *            Mass of the plane in kg.
	 * @param fuelConsumption
	 *            The fuel, in litres, consumed each second at maxVelocity.
	 */
	PlaneType(String name, int maxPassengers, double maxFuel, double maxVelocity, double mass, double fuelConsumption, int score) {
		this.name = name;
		this.maxPassengers = maxPassengers;
		this.maxFuel = maxFuel;
		this.maxVelocity = maxVelocity;
		this.mass = mass;
		this.fuelConsumption = fuelConsumption;
		this.score = score;
	}
}
