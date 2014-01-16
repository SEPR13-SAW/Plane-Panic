package com.planepanic.game.model.orders;

import lombok.AccessLevel;
import lombok.Getter;

import com.planepanic.game.model.Plane;

/**
 * A list of orders define how a plane behaves over time
 * Each order is ticked until it says that it has been completed
 * 
 * @author Jonathan, Thomas
 *
 */
public abstract class Order {

	@Getter(AccessLevel.PROTECTED) private Plane plane;

	public Order(Plane plane) {
		this.plane = plane;
	}

	public abstract boolean isComplete();

	public abstract void tick();

	public abstract void start();

	public abstract String getHumanReadable();
}
