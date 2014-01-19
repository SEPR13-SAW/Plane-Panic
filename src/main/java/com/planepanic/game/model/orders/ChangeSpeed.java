package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;

/**
 * Order a plane to change speed by a specified amount
 * 
 * @author Mantas, Thomas
 * 
 */
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
			if (this.getPlane().getSpeed() + this.changeRate < this.getPlane().getType().getMaxVelocity()) {
				return this.getPlane().getSpeed() - this.getStartSpeed() - this.getDelta() > this.getChangeRate();
			} else {
				this.getPlane().setSpeed(this.getPlane().getType().getMaxVelocity());
				this.getPlane().setVelocity(this.getPlane().convertSpeedToVelocity(this.getPlane().getVelocity().getAngle()));
				return true;
			}
		} else {
			if (this.getPlane().getSpeed() - this.changeRate > this.getPlane().getType().getMaxVelocity() / 2) {
				return this.getPlane().getSpeed() - this.getStartSpeed() - this.getDelta() < this.getChangeRate();
			} else {
				return true;
			}
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
		return "Change Speed by " + this.getDelta() + "m/s";
	}
}
