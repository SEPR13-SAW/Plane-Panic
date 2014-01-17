package com.planepanic.game.gfx.ui;

import lombok.Getter;

import com.planepanic.game.Config;
import com.planepanic.game.model.Vector2d;

/**
 * Times time, displays it
 * 
 * @author Jonathan, Mantas, Steven, Thomas
 * 
 */
public class Timer extends TextBox {
	@Getter private int minutes = 0;
	@Getter private int seconds = 0;
	@Getter private int ticks = 0;
	@Getter private String time = "0:00";
	Vector2d position;

	public Timer(Vector2d position) {
		super("0:00");
		this.setAlign(Align.MIDDLE).setColor(0x000000).setSize(50).setPosition(position);
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
		this.setText(this.minutes + ":" + (this.seconds < 10 ? "0" : "") + this.seconds);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

}
