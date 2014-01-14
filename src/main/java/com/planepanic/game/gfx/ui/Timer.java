package com.planepanic.game.gfx.ui;

import lombok.Getter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.Vector2d;

public class Timer extends Drawable {
	@Getter private int minutes = 0;
	@Getter private int seconds = 0;
	@Getter private int ticks = 0;
	private int color = 0x000000;
	private int size = 50;
	@Getter private String time = "0:00";
	Vector2d position;

	public Timer(Vector2d position) {
		this.position = position;
	}

	@Override
	public void draw2d() {
		// TODO Auto-generated method stub
		DrawUtil.drawString((float) this.position.getX(), (float) this.position.getY(), this.time, this.color, this.size);

	}

	public void tick() {
		if (this.ticks == Config.FRAMERATE) {
			if (this.seconds < 59) {
				this.seconds++;
			} else {
				this.seconds = 0;
				this.minutes++;
			}
			this.ticks = 0;
		} else {
			this.ticks++;
		}
		if (this.seconds < 10) {
			this.time = this.minutes + ":0" + this.seconds;
		} else {
			this.time = this.minutes + ":" + this.seconds;
		}
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
