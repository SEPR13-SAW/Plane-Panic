package com.planepanic.game.model;

import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;

public class OrderPanel extends Drawable {
	private Image background;

	public OrderPanel(Vector2d position) {
		this.background = new Image(Resources.PLANEPANEL, position);
	}

	@Override
	public void draw2d() {
		this.background.draw2d();
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean onClick() {
		// TODO Auto-generated method stub
		return false;
	}

}
