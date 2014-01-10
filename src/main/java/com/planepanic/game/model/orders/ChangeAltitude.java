package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.Config;
import com.planepanic.game.model.Plane;

public class ChangeAltitude extends Order {

	@Getter double delta, speed, startAltitude, changeRate;

	public ChangeAltitude(Plane plane, double startAltitude, double speed, double delta) {
		super(plane);
		this.startAltitude = startAltitude;
		this.speed = speed;
		this.delta = delta;
	}

	@Override
	public boolean isComplete() {
		return this.getPlane().getAltitude() == this.getStartAltitude() + this.delta;
	}

	@Override
	public void tick() {
		// plane.setVelocity(plane.convertSpeedToVelocity(3)); //This may be
		// changed once I know what the angle is respective too and if it is
		// degrees or radians.
		if (this.getDelta() < 0) {
			this.getPlane().setAltitude(this.getPlane().getAltitude() - this.getPlane().getSpeed() / Config.FRAMERATE);
		} else {
			this.getPlane().setAltitude(this.getPlane().getAltitude() + this.getPlane().getSpeed() / Config.FRAMERATE);
		}
	}

	@Override
	public void start() {

	}
}
