package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.lwjgl.input.Mouse;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

public class TextBox extends Drawable {

	@Accessors(chain = true) @Getter private String text;
	@Accessors(chain = true) @Getter @Setter private int color = 0xFFFFFF;
	@Accessors(chain = true) @Getter @Setter private Align align = Align.MIDDLE;

	private boolean dirtySize = true;

	public TextBox(String text) {
		super();
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
		this.dirtySize = true;
	}

	@Override
	public void draw2d() {
		if (this.dirtySize) {
			this.setHitboxSize(DrawUtil.getSize(this.text));
		}
		double x = this.getPosition().getX();
		switch (this.align) {
			case RIGHT:
				x -= this.getHitboxSize().getX() / 2;
			case MIDDLE:
				x -= this.getHitboxSize().getX() / 2;
		}
		DrawUtil.drawString((float) x, (float) (this.getPosition().getY() - this.getHitboxSize().getY() / 2), this.text, this.color, 32, this.getPriority());
	}

	@Override
	public boolean isMouseOver() {
		int x = Mouse.getX();
		int y = DrawThread.height - Mouse.getY();

		double ox = this.getPosition().getX();
		switch (this.align) {
			case RIGHT:
				ox -= this.getHitboxSize().getX() / 2;
			case MIDDLE:
				ox -= this.getHitboxSize().getX() / 2;
		}

		return x > ox && x < ox + this.getHitboxSize().getX() && y > this.getPosition().getY() - this.getHitboxSize().getY() / 2 && y < this.getPosition().getY() + this.getHitboxSize().getY() / 2;
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
