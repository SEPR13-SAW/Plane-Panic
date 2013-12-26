package com.planepanic.game.model;

import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;

public class OrderPanel extends Image {

	public OrderPanel(Vector2d position) {
		super(Resources.PLANEPANEL, position);
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
		// TODO Auto-generated method stub
		return false;
	}

}
