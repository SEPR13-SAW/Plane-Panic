package com.planepanic.game.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.orders.Order;
import com.planepanic.game.model.orders.Vector2d;

/**
 * Plane class.
 * 
 * @author Jonathan
 */
public final class Plane extends Drawable {
	@Getter private final PlaneType type;
	@Getter private final int passengers;
	@Getter @Setter private double fuel;
	@Getter @Setter private Vector2d velocity;

	@Getter private final Queue<Order> orders = new ArrayDeque<>(64);

	public Plane(PlaneType type, int passengers, double fuel) {
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;

		this.velocity = new Vector2d();
	}

	@Override
	public void draw2d() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

	public static Plane randomPlane(Random rng) {
		int index = rng.nextInt(PlaneType.values().length);
		PlaneType type = PlaneType.values()[index];

		int passengers = type.getMaxPassengers() / 2 + rng.nextInt(type.getMaxPassengers() / 2);
		double fuel = type.getMaxFuel() / 2 + rng.nextDouble() * type.getMaxFuel() / 2;

		return new Plane(type, passengers, fuel);
	}
}
