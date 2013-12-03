package com.planepanic.game.gfx;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.lwjgl.input.Mouse;

import com.planepanic.game.Config;
import com.planepanic.game.model.Vector2d;

/**
 * An abstract class used to represent objects which can be drawn to the screen.
 * 
 * @author Thomas Cheyney, Jonathan
 */
public abstract class Drawable {
	private final double MAXIMUM_CLOSE_DISTANCE = 0.001;

	@Accessors(chain = true) @Getter @Setter protected Vector2d position;
	/**
	 * Used to determine click and hover states
	 */
	@Accessors(chain = true) @Getter @Setter private Vector2d hitboxSize;

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

	final boolean clickHandler() {
		if (this.isMouseOver()) {
			return this.onClick();
		}
		return false;
	}

	protected boolean isMouseOver() {
		int x = Mouse.getX();
		int y = Config.WINDOW_HEIGHT - Mouse.getY();
		return x > this.getPosition().getX() && x < this.getPosition().getX() + this.getHitboxSize().getX() && y > this.getPosition().getY() && y < this.getPosition().getY() + this.getHitboxSize().getY();
	}

}
