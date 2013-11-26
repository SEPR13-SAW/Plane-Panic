package com.planepanic.game.model.orders;

import com.planepanic.game.model.Plane;

public abstract class Order {
	public abstract boolean isComplete(Plane plane);

	public abstract void tick(Plane plane);
}
