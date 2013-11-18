package uk.co.thomasc.seprsaw.gfx;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import uk.co.thomasc.seprsaw.Config;

/**
 * Thread in charge of the screen
 * @author Thomas Cheyney
 */
public class DrawThread extends Thread {

	@Getter private boolean running = true;
	private Set<Drawable> drawObjects = new HashSet<Drawable>();
	
	private void init() {
		try {
			DisplayMode chosenMode = new DisplayMode(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
			Display.setDisplayMode(chosenMode);
			Display.setTitle(Config.TITLE);
			Display.setFullscreen(false);
			Display.create();
			
			// Load resources
			for (Resources res : Resources.values()) {
				res.load();
			}
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glOrtho(0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, 0, 1, -1);
			GL11.glClearColor(0,0,0,0);
		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to create display.");
			System.exit(0);
		}
	}
	
	private void checkClose() {
		if (Display.isCloseRequested()) {
			running = false;
			Display.destroy();
		}
	}
	
	@Override
	public void run() {
		init();
		while (running) {
			// Clear the canvas
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			
			// Draw the objects
			for (Drawable obj : drawObjects) {
				obj.draw2d();
			}
			
			// Update the output
			Display.update();
			Display.sync(Config.FRAMERATE);
			
			// Check if we should end
			checkClose();
		}
	}

	public void draw(Image img) {
		drawObjects.add(img);
	}

}
