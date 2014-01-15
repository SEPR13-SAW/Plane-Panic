package com.planepanic.game.gfx.ui;

import com.planepanic.game.model.Vector2d;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.gfx.Image;

public class Radar extends Image{

	public Radar() {
		//super();
		super(Resources.GRASS, new Vector2d(400,360)); 
		this.setPriority(-0.9f);

	}

	@Override
	public void draw2d() {
		//DrawUtil.setColor(0x001E02);
		//DrawUtil.drawSquare((DrawThread.width - 500) / 2, DrawThread.height / 2, DrawThread.width - 500, DrawThread.height, true, -0.9f);
		super.draw2d();
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}

}
