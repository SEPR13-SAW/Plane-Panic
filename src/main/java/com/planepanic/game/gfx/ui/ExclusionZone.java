package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.gfx.screens.Game;
import com.planepanic.game.model.Vector2d;

public class ExclusionZone extends Drawable {

	float radius;
	@Getter @Setter private boolean violated;
	@Getter @Setter private boolean selected;

	public ExclusionZone(Vector2d position) {
		super(position);
		this.radius = Game.getExclusionZone();
		this.violated = false;
		this.selected = false;
	}

	public void onResize() {
		// this.radius = Math.min(DrawThread.height, DrawThread.width - 500) /
		// 2;
	}

	@Override
	public void draw2d() {
		if (this.violated) {
			DrawUtil.setColor(0xff0000);
			DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), this.radius, false);
		} else if (this.selected) {
			DrawUtil.setColor(0x00ff00);
			DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), this.radius, false);
		}

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
