package com.planepanic.game.model;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.gfx.DrawUtil;

/**
 * A class to represent waypoints.
 * 
 * @author Jonathan, Mantas
 */
public final class Waypoint extends SimplePointOfInterest {
	@Getter private final String name;
	@Getter @Setter private static Waypoint via = null, target = null;

	public Waypoint(Vector2d position, String name) {
		super(position);
		this.name = name;
	}

	@Override
	public void draw2d() {
		super.draw2d();
		Vector2d size = DrawUtil.getSize(this.name, 12);
		DrawUtil.drawString((float) (this.getPosition().getX() - size.getX() / 2), (float) (this.getPosition().getY() - size.getY() / 2), this.name, 0xFFFFFF, 12, this.getPriority());
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		if(Waypoint.via == null)
			Waypoint.via = this;
		else if(Waypoint.via != this){
		
			Waypoint.target = this;
		}
		return true;
	}
}
