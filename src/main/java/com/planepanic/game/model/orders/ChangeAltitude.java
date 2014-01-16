package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.Config;
import com.planepanic.game.model.Plane;

/**
 * Order a plane to ascend or descend by a specified amount 
 * 
 * @author Ben, Mantas, Thomas
 *
 */
public class ChangeAltitude extends Order {

	@Getter double delta, speed, startAltitude, changeRate;

	public ChangeAltitude(Plane plane, double delta) {
		super(plane);
		this.startAltitude = plane.getAltitude();
		this.delta = delta / Config.SCALE;
	}

	@Override
	public boolean isComplete() {
		if (this.getDelta() > 0) {
			return this.getPlane().getAltitude() - this.getStartAltitude() - this.delta > 1;
		} else {
			return this.getPlane().getAltitude() - this.getStartAltitude() - this.delta < 1;
		}
	}

	@Override
	public void tick() {
		// plane.setVelocity(plane.convertSpeedToVelocity(3)); //This may be
		// changed once I know what the angle is respective too and if it is
		// degrees or radians.
		if (this.getDelta() < 0) {
			this.getPlane().setAltitude(this.getPlane().getAltitude() - 20.0 / Config.FRAMERATE);
		} else {
			this.getPlane().setAltitude(this.getPlane().getAltitude() + 20.0 / Config.FRAMERATE);
		}
	}

	@Override
	public void start() {

	}

	@Override
	public String getHumanReadable() {
		return "Change Altitude by " + this.getDelta() * Config.SCALE + "m";
	}
}
