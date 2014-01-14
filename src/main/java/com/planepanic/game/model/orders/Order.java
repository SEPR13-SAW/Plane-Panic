package com.planepanic.game.model.orders;

import lombok.AccessLevel;
import lombok.Getter;

import com.planepanic.game.model.Plane;

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
