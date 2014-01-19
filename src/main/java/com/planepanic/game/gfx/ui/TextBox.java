package com.planepanic.game.gfx.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;

/**
 * Draws text Can be editable
 * 
 * @author Jonathan, Thomas
 * 
 */
public class TextBox extends Drawable {

	@Accessors(chain = true) @Getter private String text;
	@Accessors(chain = true) @Getter @Setter private int color = 0xFFFFFF;
	@Accessors(chain = true) @Getter private int size = 32;
	@Accessors(chain = true) @Getter @Setter private Align align = Align.MIDDLE;
	@Accessors(chain = true) @Getter @Setter private int cursorPosition = 0;
	@Accessors(chain = true) @Getter private final boolean editable;
	/*
	 * Numpad is only enabled when numbersOnly is true
	 */
	@Accessors(chain = true) @Getter @Setter private boolean numbersOnly;

	private boolean dirtySize = true;

	public TextBox(String text, boolean editable, boolean numbersOnly) {
		super();
		this.text = text;
		this.editable = editable;
		this.numbersOnly = numbersOnly;
	}

	public TextBox(String text) {
		this(text, false, false);
	}

	public TextBox setText(String text) {
		this.text = text;
		this.dirtySize = true;
		return this;
	}

	public TextBox setSize(int size) {
		this.size = size;
		this.dirtySize = true;
		return this;
	}

	@Override
	public void draw2d() {
		if (this.dirtySize) {
			this.setHitboxSize(DrawUtil.getSize(this.text, this.size));
		}

		double x = this.getPosition().getX();
		switch (this.align) {
			case RIGHT:
				x -= this.getHitboxSize().getX() / 2;
			case MIDDLE:
				x -= this.getHitboxSize().getX() / 2;
			default:
				break;
		}

		if (this.editable && DrawThread.getFocus() == this) {
			int cursorX = (int) (x + DrawUtil.getSize(this.text.substring(0, this.cursorPosition)).getX());
			DrawUtil.setColor(0x888888);
			DrawUtil.drawSquare(cursorX, (int) (this.getPosition().getY() - this.getHitboxSize().getY() / 2 + 16 + 3), 2, this.size, true, this.getPriority());
		}

		DrawUtil.drawString((float) x, (float) (this.getPosition().getY() - this.getHitboxSize().getY() / 2), this.text, this.color, this.size, this.getPriority());
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
		if (!this.editable) {
			return false;
		}

		DrawThread.setFocus(this);

		this.cursorPosition = 0;

		for (int i = 0; i < this.text.length(); i++) {
			int x = (int) (Mouse.getX() - this.getPosition().getX());

			switch (this.align) {
				case RIGHT:
					x += this.getHitboxSize().getX() / 2;
				case MIDDLE:
					x += this.getHitboxSize().getX() / 2;
				default:
					break;
			}

			int width = (int) DrawUtil.getSize(this.text.substring(0, i)).getX();
			int width2 = (int) DrawUtil.getSize(this.text.substring(0, i + 1)).getX();

			if (x > width && x < width2) {
				if (width2 - x > x - width) {
					this.cursorPosition = i;
				} else {
					this.cursorPosition = i + 1;
				}
			}
		}

		return true;
	}

	@Override
	protected boolean keyPress(int key) {
		try {
			if (key == Keyboard.KEY_BACK) {
				this.setText(this.getText().substring(0, this.cursorPosition - 1) + this.getText().substring(this.cursorPosition, this.getText().length()));
				this.cursorPosition--;
			} else if (key == Keyboard.KEY_DELETE) {
				this.setText(this.getText().substring(0, this.cursorPosition) + this.getText().substring(this.cursorPosition + 1, this.getText().length()));
			} else if (key == Keyboard.KEY_LEFT) {
				this.cursorPosition--;
				if (this.cursorPosition < 0) {
					this.cursorPosition = 0;
				}
			} else if (key == Keyboard.KEY_RIGHT) {
				this.cursorPosition++;
				if (this.cursorPosition > this.getText().length()) {
					this.cursorPosition = this.getText().length();
				}
			} else {
				if (this.numbersOnly) {
					if (key >= 2 && key <= 11) {
						this.setText(this.getText().substring(0, this.cursorPosition) + new String(Keyboard.getKeyName(key)) + this.getText().substring(this.cursorPosition, this.getText().length()));
						this.cursorPosition++;
					}
					if (key >= 69 && key <= 82 && key != 70 && key != 74){
						this.setText(this.getText().substring(0, this.cursorPosition) + new String(Keyboard.getKeyName(key).substring(6)) + this.getText().substring(this.cursorPosition, this.getText().length()));
						this.cursorPosition++;
					}
				} else {
					if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						this.setText(this.getText().substring(0, this.cursorPosition) + new String(Keyboard.getKeyName(key)).toLowerCase() + this.getText().substring(this.cursorPosition, this.getText().length()));
					} else {
						this.setText(this.getText().substring(0, this.cursorPosition) + new String(Keyboard.getKeyName(key)) + this.getText().substring(this.cursorPosition, this.getText().length()));
					}
					this.cursorPosition++;
				}
			}
		} catch (StringIndexOutOfBoundsException ex) {
			/* ignore */
		}
		return true;
	}

}
