package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

public class Radar extends Drawable {

	public Radar() {
		super();
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0x001E02);
		DrawUtil.drawSquare((float) this.getPosition().getX(), (float) this.getPosition().getY(), DrawThread.width - 500, DrawThread.height);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

}
