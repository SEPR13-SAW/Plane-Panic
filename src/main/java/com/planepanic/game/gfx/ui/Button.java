package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.orders.Vector2d;

public class Button extends Drawable {

	@Accessors(chain = true) @Getter @Setter private String text;
	@Accessors(chain = true) @Setter private Runnable callback;

	private boolean dirtySize = true;
	private Vector2d textSize;

	public Button(String text) {
		super();
		this.text = text;
		this.setHitboxSize(new Vector2d(300, 90));
	}

	@Override
	public void draw2d() {
		if (this.isMouseOver()) {
			DrawUtil.setColor(0xA41CFF);
		} else {
			DrawUtil.setColor(0x7313AF);
		}
		DrawUtil.drawSquare((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) this.getHitboxSize().getX(), (float) this.getHitboxSize().getY());
		if (this.dirtySize) {
			this.textSize = DrawUtil.getSize(this.text);
		}
		DrawUtil.drawString((float) (this.getPosition().getX() + (this.getHitboxSize().getX() / 2 - this.textSize.getX() / 2)), (float) (this.getPosition().getY() + (this.getHitboxSize().getY() / 2 - this.textSize.getY() / 2)), this.text);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		if (this.callback != null) {
			this.callback.run();
			return true;
		}
		return false;
	}

}
