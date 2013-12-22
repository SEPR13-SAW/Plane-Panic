package com.planepanic.game.gfx.ui;

import lombok.Getter;

import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.gfx.screens.Game;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

public class ExclusionZone extends Drawable {

	float radius;
	Vector2d position;
	Game game;
	@Getter private Plane plane;
	@Getter private Plane plane2;

	public ExclusionZone(Game game, Vector2d position, Plane plane, Plane plane2) {
		super(position);
		this.game = game;
		this.radius = Game.getExclusionZone();
		this.plane = plane;
		this.plane2 = plane2;
	}

	public void onResize() {
		//this.radius = Math.min(DrawThread.height, DrawThread.width - 500) / 2;
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0xff0000);
		DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), this.radius, true);
		DrawUtil.setColor(0xff0010);
		DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), this.radius, false);
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
