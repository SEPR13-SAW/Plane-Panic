package com.planepanic.game.model;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.screens.Game;

/**
 * A class to represent Entry points.
 * 
 * @author Mantas
 */
public final class ExitPoint extends Waypoint {
	private Game game;
	@Getter @Setter private static ExitPoint exitPoint;

	public ExitPoint(Vector2d position, String name, Game game) {
		super(position, name);
		this.game = game;
	}

	public void removePlane(Plane plane) {
		// TODO make it do something
		this.game.getPlaneList().remove(plane);
		DrawThread.draw.removeObject(plane);
	}

	@Override
	public void draw2d() {
		super.draw2d();

	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		if (Waypoint.getVia() == null && Plane.getSelected() != null) {
			ExitPoint.exitPoint = this;
		}
		else if (Waypoint.getVia() != this) {

			Waypoint.setTarget(this);
		}
		return true;
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}
}
