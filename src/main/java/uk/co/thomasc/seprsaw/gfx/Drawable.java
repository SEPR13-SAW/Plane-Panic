package uk.co.thomasc.seprsaw.gfx;

import lombok.Getter;
import lombok.Setter;

/**
 * An object that can be draw to the screen
 * @author Thomas Cheyney
 */
public abstract class Drawable {
	
	@Getter @Setter int x, y;
	
	/**
	 * Draw on 2d view
	 */
	abstract void draw2d();
	
	/**
	 * Draw on 2.5/3d view
	 */
	abstract void draw3d();
	
}