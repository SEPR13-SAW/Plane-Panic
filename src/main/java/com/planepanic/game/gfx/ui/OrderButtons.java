package com.planepanic.game.gfx.ui;

import lombok.Setter;
import lombok.experimental.Accessors;

import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.model.Vector2d;

public class OrderButtons extends Drawable {

	@Accessors(chain = true) @Setter private Runnable callback;

	private Image orderbutton;

	public OrderButtons(int xPosition, int yPosition, Resources buttonType) {
		super();
		this.orderbutton = new Image(buttonType, new Vector2d(xPosition, yPosition));
		this.setHitboxSize(new Vector2d(128, 64));
		this.setPosition(new Vector2d(xPosition - 64, yPosition - 32));
	}

	@Override
	public void draw2d() {
		this.orderbutton.draw2d();

	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean onClick() {
		if (this.callback != null) {
			this.callback.run();
			return true;
		}
		return false;
	}

}
