package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;

public class ChangeSpeed extends Order {

	@Getter double delta, startSpeed, changeRate;

	public ChangeSpeed(Plane plane, double delta) {
		super(plane);
		this.delta = delta;
		this.changeRate = delta / 60f;
	}

	@Override
	public boolean isComplete() {
		if (this.delta > 0) {
			return this.getPlane().getSpeed() - this.getStartSpeed() - this.getDelta() > this.getChangeRate();
		} else {
			return this.getPlane().getSpeed() - this.getStartSpeed() - this.getDelta() < this.getChangeRate();
		}
	}

	@Override
	public void tick() {
		this.getPlane().setSpeed(this.getPlane().getSpeed() + this.getChangeRate());
		this.getPlane().setVelocity(this.getPlane().convertSpeedToVelocity(this.getPlane().getVelocity().getAngle()));
	}

	@Override
	public void start() {
		this.startSpeed = this.getPlane().getSpeed();
	}

	@Override
	public String getHumanReadable() {
		return "Change Speed";
	}
}
