package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

public class Radar extends Drawable {

	float radius = 100f;
	
	public Radar() {
		super();
	}
	
	public void onResize() {
		radius = Math.min(DrawThread.height, DrawThread.width - 500) / 2;
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0x001E02);
		DrawUtil.drawCircle((float) getPosition().getX(), (float) getPosition().getY(), radius, true);
		DrawUtil.setColor(0x006009);
		DrawUtil.drawCircle((float) getPosition().getX(), (float) getPosition().getY(), radius, false);
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
