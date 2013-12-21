package com.planepanic.game.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.model.orders.Order;

/**
 * Plane class.
 * 
 * @author Jonathan
 */
public final class Plane extends Image {
	@Getter private final PlaneType type;
	@Getter private final int passengers;
	@Getter @Setter private double fuel;
	@Getter @Setter private double speed; // Simple linear speed, to avoid having to calculate length of the vector every tick
	@Getter @Setter private Vector2d velocity;

	@Getter private final Queue<Order> orders = new ArrayDeque<>(64);

	public Plane(PlaneType type, int passengers, double fuel, double speed, Vector2d position, Resources sprite) {
		super(sprite, position);
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;
		this.speed = speed;
		this.velocity = this.convertSpeedToVelocity(Math.PI / 2);
		System.out.println(this.getVelocity().getX()+" "+this.getVelocity().getY());
	}

	@Override
	public void draw2d() {
		super.draw2d();
		this.tick();
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

	public Order getCurrentOrder() {
		return this.orders.peek();
	}

	public void tick() {
		this.consumeFuel();

		Order order = this.getCurrentOrder();
		if (order != null) {
			order.tick(this);
			if (order.isComplete(this)) {
				this.orders.poll();
			}
		}
		this.getPosition().applyChange(this.getVelocity());
		this.setAngle((float) Math.toDegrees(this.getVelocity().getAngle()) + 90);
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

	public static Plane randomPlane(Random rng, Vector2d position) {
		int index = rng.nextInt(PlaneType.values().length);
		PlaneType type = PlaneType.values()[index];

		int passengers = type.getMaxPassengers() / 2 + rng.nextInt(type.getMaxPassengers() / 2);
		double fuel = type.getMaxFuel() / 2 + rng.nextDouble() * type.getMaxFuel() / 2;
		double speed = type.getMaxVelocity();

		return new Plane(type, passengers, fuel, speed, position, Resources.PLANE);
	}
	
	// Calculates the fuel consumption in l/s scaling by how much of a max speed plane is flying
	public void consumeFuel() {
		this.setFuel(this.getFuel() - this.type.getFuelConsumption() / Config.FRAMERATE * (this.getSpeed() / this.type.getMaxVelocity()));
	};
	
	// Converts speed in m/s and starting angle, to a Cartesian vector
	// for simpler use in other calculations
	public Vector2d convertSpeedToVelocity(double angle){
		double x,y;
		x = this.getSpeed() * Math.cos(angle) / Config.FRAMERATE / 10;
		y = this.getSpeed() * Math.sin(angle) / Config.FRAMERATE / 10;
		if (x < 0.01)
			x = 0;
		if (y < 0.01)
			y = 0;
		return new Vector2d(x, y);
	}
}
