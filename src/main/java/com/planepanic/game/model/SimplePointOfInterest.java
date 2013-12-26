package com.planepanic.game.model;

import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

/**
 * An abstract class to represent Points of Interest.
 * 
 * @author Mantas
 */

public abstract class SimplePointOfInterest extends Drawable implements PointOfInterest {

	public SimplePointOfInterest(Vector2d position) {
		super(position);
		this.setPriority(-0.2f);
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0xFF4B2B);
		DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), 10f, false, getPriority());
	}

}
