package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

public class Radar extends Drawable {

	public Radar() {
		super();
		this.setPriority(-1);
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0x001E02);
		DrawUtil.drawSquare((DrawThread.width - 500) / 2, DrawThread.height / 2, DrawThread.width - 500, DrawThread.height, true, -0.9f);
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
