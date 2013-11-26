package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.orders.Vector2d;

public class Button extends Drawable {
	
	@Accessors(chain=true) @Getter @Setter private String text;
	@Accessors(chain=true) @Setter private Runnable callback;
	
	private boolean dirtySize = true;
	private Vector2d textSize;

	public Button(String text) {
		super();
		this.text = text;
		setHitboxSize(new Vector2d(300, 90));
	}
	
	@Override
	public void draw2d() {
		if (isMouseOver()) {
			DrawUtil.setColor(0xA41CFF);
		} else {
			DrawUtil.setColor(0x7313AF);
		}
		DrawUtil.drawSquare((float) getPosition().getX(), (float) getPosition().getY(), (float) getHitboxSize().getX(), (float) getHitboxSize().getY());
		if (dirtySize) {
			textSize = DrawUtil.getSize(text);
		}
		DrawUtil.drawString((float) (getPosition().getX() + ((getHitboxSize().getX() / 2) - (textSize.getX() / 2))), (float) (getPosition().getY() + ((getHitboxSize().getY() / 2) - (textSize.getY() / 2))), text);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onClick() {
		if (callback != null) {
			callback.run();
			return true;
		}
		return false;
	}
	
}