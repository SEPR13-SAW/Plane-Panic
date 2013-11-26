package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

public class TextBox extends Drawable {

	@Accessors(chain = true) @Getter @Setter private String text;
	@Accessors(chain = true) @Getter @Setter private int color = 0xFFFFFF;

	private boolean dirtySize = true;

	public TextBox(String text) {
		super();
		this.text = text;
	}

	@Override
	public void draw2d() {
		if (this.dirtySize) {
			this.setHitboxSize(DrawUtil.getSize(this.text));
		}
		DrawUtil.drawString((float) this.getPosition().getX(), (float) this.getPosition().getY(), this.text, this.color);
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
