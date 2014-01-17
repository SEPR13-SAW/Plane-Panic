package com.planepanic.game.model.orders;

import lombok.Getter;

import com.planepanic.game.model.ExitPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;

/**
 * Order a plane to "fly by" a waypoint to another waypoint
 * 
 * @author Jonathan, Mantas, Thomas
 * 
 */
public final class FlyBy extends Order {
	@Getter private final Waypoint waypoint;
	@Getter private final Waypoint targetWaypoint;
	private boolean partComplete = false;
	private boolean changeComplete = false;

	public FlyBy(Plane plane, Waypoint waypoint, Waypoint waypoint2) {
		super(plane);
		this.waypoint = waypoint;
		this.targetWaypoint = waypoint2;
	}

	/*
	 * If the target waypoint is exit point removes plane
	 */
	@Override
	public boolean isComplete() {

		if (!this.partComplete) {
			this.partComplete = this.getPlane().distanceTo(this.waypoint) < 40;
			return false;
		}
		if (this.getPlane().distanceTo(this.targetWaypoint) < 1) {
			if (this.getTargetWaypoint().getClass() != this.getWaypoint().getClass()) {
				((ExitPoint) this.getTargetWaypoint()).removePlane(this.getPlane());
			}
			return true;
		}
		return false;
	}

	@Override
	public void tick() {
		if (!this.partComplete) {
			if (!this.changeComplete) {
				this.changeHeading(this.waypoint);
			} else {
				this.changeComplete = false;
			}
		} else {
			if (!this.changeComplete) {
				this.changeHeading(this.targetWaypoint);
			} else {
				this.changeComplete = false;
			}
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
		if (a >= 0.05 || a <= -0.05) {
			if (a >= 0) {
				this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa + Math.PI / 2));
			} else {
				this.getPlane().getVelocity().applyChange(Vector2d.fromAngle(pa - Math.PI / 2));
			};
		} else {
			this.changeComplete = true;
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
