package com.planepanic.game.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.model.orders.Order;


/**
 * Plane class.
 * 
 * @author Jonathan
 */
public final class Plane extends Drawable {
	@Getter private final PlaneType type;
	@Getter private final int passengers;
	@Getter private Image sprite;
	@Getter @Setter private double fuel;
	@Getter @Setter private Vector2d velocity;
	@Getter private Vector2d position;

	@Getter private final Queue<Order> orders = new ArrayDeque<>(64);

	public Plane(PlaneType type, int passengers, double fuel, Vector2d position, Image sprite) {
		super(position);
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;
		this.velocity = new Vector2d();
		this.sprite = sprite;
	}

	@Override
	public void draw2d() {
		this.sprite.draw2d();		
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
		return orders.peek();
	}

	public void tick() {
		Order order = getCurrentOrder();
		order.tick(this);
		if (order.isComplete(this)) orders.poll();
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public static Plane randomPlane(Random rng, Vector2d position) {
		int index = rng.nextInt(PlaneType.values().length);
		PlaneType type = PlaneType.values()[index];

		int passengers = type.getMaxPassengers() / 2 + rng.nextInt(type.getMaxPassengers() / 2);
		double fuel = type.getMaxFuel() / 2 + rng.nextDouble() * type.getMaxFuel() / 2;
		Image sprite = new Image(Resources.PLANE);

		return new Plane(type, passengers, fuel, position, sprite);
	}
}
