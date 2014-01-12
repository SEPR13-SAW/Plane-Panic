package com.planepanic.game.gfx;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.lwjgl.input.Mouse;

import com.planepanic.game.model.Vector2d;

/**
 * An abstract class used to represent objects which can be drawn to the screen.
 * 
 * @author Thomas Cheyney, Jonathan
 */
public abstract class Drawable {
	private final double MAXIMUM_CLOSE_DISTANCE = 0.001;

	@Accessors(chain = true) @Getter @Setter private Vector2d position;
	@Accessors(chain = true) @Getter @Setter private float priority = 0;
	/**
	 * Used to determine click and hover states
	 */
	@Accessors(chain = true) @Getter @Setter private Vector2d hitboxSize;
	@Accessors(chain = true) @Setter private CallbackP<Boolean, Integer> scrollCallback;

	public Drawable(Vector2d position, Vector2d hitboxSize) {
		this.position = position;
		this.hitboxSize = hitboxSize;
	}

	public Drawable(Vector2d position) {
		this(position, new Vector2d());
	}

	public Drawable() {
		this(new Vector2d());
	}

	/**
	 * Draw on 2d view
	 */
	public abstract void draw2d();

	/**
	 * Draw on 2.5/3d view
	 */
	public abstract void draw3d();

	public double distanceTo(Drawable d) {
		return Math.sqrt((this.position.getX() - d.position.getX()) * (this.position.getX() - d.position.getX()) + (this.position.getY() - d.position.getY()) * (this.position.getY() - d.position.getY()));
	}

	public boolean closeEnough(Drawable d) {
		return this.distanceTo(d) < this.MAXIMUM_CLOSE_DISTANCE;
	}

	/**
	 * Called when a user has clicked on the object
	 * 
	 * @return true if handled, false if should propagate further
	 */
	protected abstract boolean onClick();
	
	protected boolean onClickRight(){
		return false;
	}

	/**
	 * Called when a user has scrolled on the object
	 * 
	 * @param scroll
	 * 
	 * @return true if handled, false if should propagate further
	 */
	protected boolean onScroll(int scroll) {
		try {
			return this.scrollCallback.call(scroll);
		} catch (Exception e) {
			return false;
		}
	}

	final boolean clickHandler() {
		if (this.isMouseOver()) {
			return this.onClick();
		}
		return false;
	}
	
	final boolean clickRightHandler() {
		if (this.isMouseOver()) {
			return this.onClickRight();
		}
		return false;
	}

	protected boolean isMouseOver() {
		int x = Mouse.getX();
		int y = DrawThread.height - Mouse.getY();
		return x > this.getPosition().getX() - this.getHitboxSize().getX() / 2 && x < this.getPosition().getX() + this.getHitboxSize().getX() / 2 && y > this.getPosition().getY() - this.getHitboxSize().getY() / 2 && y < this.getPosition().getY() + this.getHitboxSize().getY() / 2;
	}

	public boolean scrollHandler(int scroll) {
		if (this.isMouseOver()) {
			return this.onScroll(scroll);
		}
		return false;
	}

}
