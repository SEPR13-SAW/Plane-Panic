package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;

public final class FlyOver extends Order {
	@Getter private final Waypoint waypoint;
	@Getter private final Waypoint targetWaypoint;
	private boolean partComplete = false;

	public FlyOver(Plane plane, Waypoint waypoint, Waypoint waypoint2) {
		super(plane);
		this.waypoint = waypoint;
		this.targetWaypoint = waypoint2;
	}
	//Maximum close distance is too small to work
	@Override
	public boolean isComplete() {

		if(!partComplete){
			this.partComplete = this.getPlane().distanceTo(waypoint) < 1;
			return false;
		}
		return this.getPlane().distanceTo(targetWaypoint) < 1;
	}

	@Override
	public void tick() {
		if(!partComplete){
			this.changeHeading(this.waypoint);
		} else {
			this.changeHeading(this.targetWaypoint);
		}

	}
	
	public void changeHeading(Waypoint waypoint){
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

}
