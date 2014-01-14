package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Runway;

public final class Land extends Order {
	@Getter private final Runway runway;

	public Land(Plane plane, Runway runway) {
		super(plane);
		this.runway = runway;
	}

	@Override
	public boolean isComplete() {
		return this.getPlane().closeEnough(this.runway);
	}

	@Override
	public void tick() {
		// TODO
	}

	@Override
	public void start() {

	}
	
	@Override
	public String getHumanReadable() {
		return "Land";
	}

}
