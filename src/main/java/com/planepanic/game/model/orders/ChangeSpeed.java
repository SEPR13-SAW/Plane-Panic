package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;

public class ChangeSpeed extends Order {

	@Getter double delta, startSpeed, changeRate;

	public ChangeSpeed(double startSpeed, double delta) {
		this.startSpeed = startSpeed;
		this.delta = delta;
		this.changeRate = delta / 60f;

	}

	@Override
	public boolean isComplete(Plane plane) {
		if (this.delta > 0) {
			return plane.getSpeed() - this.getStartSpeed() - this.getDelta() > this.getChangeRate();
		} else {
			return plane.getSpeed() - this.getStartSpeed() - this.getDelta() < this.getChangeRate();
		}
	}

	@Override
	public void tick(Plane plane) {
		plane.setSpeed(plane.getSpeed() + this.getChangeRate());
		plane.setVelocity(plane.convertSpeedToVelocity(plane.getVelocity().getAngle()));
	}
}
