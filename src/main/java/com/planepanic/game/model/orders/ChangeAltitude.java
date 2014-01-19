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
		this.delta = delta;
	}

	@Override
	public boolean isComplete() {
		if (this.getDelta() > 0) {
			if (this.getPlane().getAltitude() + this.changeRate < this.getPlane().getType().getMaxAltitude()) {
				return this.getPlane().getAltitude() - this.getStartAltitude() - this.delta > 1;
			} else {
				this.getPlane().setAltitude(this.getPlane().getType().getMaxAltitude());
				return true;
			}
		} else {
			if (this.getPlane().getAltitude() - this.changeRate > this.getPlane().getType().getMaxAltitude() * 0.7) {
				return this.getPlane().getAltitude() - this.getStartAltitude() - this.delta < 1;
			} else {
				this.getPlane().setAltitude(this.getPlane().getType().getMaxAltitude() * 0.7);
				return true;
			}
		}
	}

	@Override
	public void tick() {
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
		return "Change Altitude by " + this.getDelta() + "m";
	}
}
