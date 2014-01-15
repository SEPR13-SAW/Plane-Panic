package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

public class TextBox extends Drawable {

	@Accessors(chain = true) @Getter private String text;
	@Accessors(chain = true) @Getter @Setter private int color = 0xFFFFFF;
	@Accessors(chain = true) @Getter @Setter private Align align = Align.MIDDLE;
	@Accessors(chain = true) @Getter @Setter private int cursorPosition = 0;
	@Accessors(chain = true) @Getter private final boolean editable;

	private boolean dirtySize = true;

	public TextBox(String text, boolean editable) {
		super();
		this.text = text;
		this.editable = editable;
	}

	public TextBox(String text) {
		this(text, false);
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

		if (editable && DrawThread.getFocus() == this) {
			int cursorX = (int) (x + DrawUtil.getSize(this.text.substring(0, cursorPosition)).getX());
			DrawUtil.setColor(0x888888);
			DrawUtil.drawSquare(cursorX, (int) (this.getPosition().getY() - (this.getHitboxSize().getY() / 2) + 16 + 3), 2, 32, true, this.getPriority());
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
		if (!editable) return false;

		DrawThread.setFocus(this);

		cursorPosition = 0;

		for (int i = 0; i < this.text.length(); i++) {
			int x = (int) (Mouse.getX() - this.getPosition().getX());

			switch (this.align) {
				case RIGHT:
					x += this.getHitboxSize().getX() / 2;
				case MIDDLE:
					x += this.getHitboxSize().getX() / 2;
			}

			int width = (int) DrawUtil.getSize(this.text.substring(0, i)).getX();
			int width2 = (int) DrawUtil.getSize(this.text.substring(0, i+1)).getX();

			if (x > width && x < width2) {
				if (width2 - x > x - width) {
					cursorPosition = i;
				} else {
					cursorPosition = i + 1;
				}
			}
		}

		return true;
	}

	@Override
	protected boolean keyPress(int key) {
		try {
			if (key == Keyboard.KEY_BACK) {
				setText(getText().substring(0, cursorPosition-1) + getText().substring(cursorPosition, getText().length()));
				cursorPosition--;
			} else if (key == Keyboard.KEY_DELETE) {
				setText(getText().substring(0, cursorPosition) + getText().substring(cursorPosition+1, getText().length()));
			} else if (key == Keyboard.KEY_LEFT) {
				cursorPosition--;
				if (cursorPosition < 0) cursorPosition = 0;
			} else if (key == Keyboard.KEY_RIGHT) {
				cursorPosition++;
				if (cursorPosition > getText().length()) cursorPosition = getText().length();
			} else {
				if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					setText(getText().substring(0, cursorPosition) + new String(Keyboard.getKeyName(key)).toLowerCase() + getText().substring(cursorPosition, getText().length()));
				} else {
					setText(getText().substring(0, cursorPosition) + new String(Keyboard.getKeyName(key)) + getText().substring(cursorPosition, getText().length()));
				}
				cursorPosition++;
			}
		} catch (StringIndexOutOfBoundsException ex) {
			/* ignore */
		}
		return true;
	}

}
