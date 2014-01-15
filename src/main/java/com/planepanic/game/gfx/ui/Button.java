package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.planepanic.game.gfx.Callback;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.Vector2d;

public class Button extends Drawable {

	@Accessors(chain = true) @Getter @Setter private String text;
	@Accessors(chain = true) @Setter private Callback<Boolean> callback;

	private boolean dirtySize = true;
	private Vector2d textSize;

	public Button(String text) {
		super();
		this.text = text;
		this.setHitboxSize(new Vector2d(300, 90));
		this.setPriority(0.1f);
	}

	@Override
	public void draw2d() {
		if (this.isMouseOver()) {
			DrawUtil.setColor(0xA41CFF);
		} else {
			DrawUtil.setColor(0x7313AF);
		}
		DrawUtil.drawSquare((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) this.getHitboxSize().getX(), (float) this.getHitboxSize().getY(), true, this.getPriority());
		if (this.dirtySize) {
			this.textSize = DrawUtil.getSize(this.text);
		}
		DrawUtil.drawString((float) (this.getPosition().getX() - this.textSize.getX() / 2), (float) (this.getPosition().getY() - this.textSize.getY() / 2), this.text, 0xFFFFFF, 32, this.getPriority() + 0.01f);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		if (this.callback != null) {
			return this.callback.call();
		}
		return false;
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}

}
