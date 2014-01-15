package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;
import com.planepanic.game.model.ExitPoint;

public final class FlyBy extends Order {
	@Getter private final Waypoint waypoint;
	@Getter private final Waypoint targetWaypoint;
	private boolean partComplete = false;

	public FlyBy(Plane plane, Waypoint waypoint, Waypoint waypoint2) {
		super(plane);
		this.waypoint = waypoint;
		this.targetWaypoint = waypoint2;
	}

	// Maximum close distance is too small to work
	@Override
	public boolean isComplete() {

		if (!this.partComplete) {
			this.partComplete = this.getPlane().distanceTo(this.waypoint) < 40;
			return false;
		}
		if(this.getPlane().distanceTo(this.targetWaypoint) < 1){
			if(this.getTargetWaypoint().getClass() != this.getWaypoint().getClass())
				((ExitPoint) this.getTargetWaypoint()).removePlane(this.getPlane());
			return true;
		}
		return false;
	}

	@Override
	public void tick() {
		if (!this.partComplete) {
			this.changeHeading(this.waypoint);
		} else {
			this.changeHeading(this.targetWaypoint);
		}

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
		return "Fly By " + this.getWaypoint().getName() + " to " + this.getTargetWaypoint().getName();
	}
}
