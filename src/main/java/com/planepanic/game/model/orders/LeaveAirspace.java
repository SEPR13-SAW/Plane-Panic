package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.ExitPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;

/**
 * Order a plane to leave the airspace
 * 
 * @author Mantas, Thomas
 *
 */
public final class LeaveAirspace extends Order {
	@Getter private final ExitPoint exit;

	public LeaveAirspace(Plane plane, ExitPoint exit) {
		super(plane);
		this.exit = exit;
	}

	/*
	 * If the target waypoint is exit point removes plane
	 */
	@Override
	public boolean isComplete() {
		if (this.getPlane().distanceTo(this.exit) < 1) {
			this.exit.removePlane(this.getPlane());
			return true;
		}
		return false;
	}

	@Override
	public void tick() {
		this.changeHeading(this.exit);
	}

	public void changeHeading(Waypoint waypoint) {
		double target = waypoint.getPosition().sub(this.getPlane().getPosition()).getAngle();
		double pa = this.getPlane().getVelocity().getAngle();
		double a = target - pa;
		while (a > Math.PI) {
			a -= Math.PI * 2;
		}
		while (a < -Math.PI) {
			a += Math.PI * 2;
		}

		if (a >= 0) {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa + Math.PI / 2));
		} else {
			this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa - Math.PI / 2));
		}
	}

	@Override
	public void start() {

	}

	@Override
	public String getHumanReadable() {
		return "Leave airspace through " + this.getExit().getName();
	}
}
