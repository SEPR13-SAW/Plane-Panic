package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Runway;

public final class TakeOff extends Order {
	@Getter private final Runway runway;

	public TakeOff(Runway runway) {
		this.runway = runway;
	}

	@Override
	public boolean isComplete(Plane plane) {
		return !plane.closeEnough(this.runway);
	}

	@Override
	public void tick(Plane plane) {
		// TODO
	}

}
