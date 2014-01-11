package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.Vector2d;

public class OrderPanel extends Drawable {

	private Controls controls;
	private PlaneInfo planeinfo;

	public OrderPanel(Vector2d position) {
		super(position, new Vector2d(500, 720));
		this.controls = new Controls();
		this.controls.setOrderState(0);
		this.planeinfo = new PlaneInfo(this);
		this.setPriority(0.4f);
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0xffffff);
		DrawUtil.drawSquare((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) this.getHitboxSize().getX(), (float) this.getHitboxSize().getY(), true, this.getPriority());
		DrawUtil.setColor(0x000000);
		DrawUtil.drawSquare((float) this.getPosition().getX(), 150, 500, 1, true, this.getPriority() + 0.01f);
		DrawUtil.drawSquare((float) this.getPosition().getX(), DrawThread.height - 180, 500, 1, true, this.getPriority() + 0.01f);
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

	public void tick() {
		this.planeinfo.tick();
	}

}
