package com.planepanic.game.gfx;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.screens.Screen;

/**
 * Thread in charge of the screen
 * 
 * @author Thomas Cheyney
 */
public class DrawThread extends Thread {

	public static DrawThread draw;

	public static DrawThread getInstance() {
		if (DrawThread.draw == null) {
			DrawThread.draw = new DrawThread();
			DrawThread.draw.start();
		}
		return DrawThread.draw;
	}

	@Getter private boolean running = true;
	private Map<RenderPriority, Set<Drawable>> drawObjects = new HashMap<RenderPriority, Set<Drawable>>();
	private boolean mouseWasUp = true;
	private Screen currentScreen;

	public static int width = 1280;
	public static int height = 720;

	private void init() {
		try {
			if (Config.FULLSCREEN) {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				DrawThread.width = (int) screenSize.getWidth();
				DrawThread.height = (int) screenSize.getHeight();
			} else {
				DrawThread.width = Config.WINDOW_WIDTH;
				DrawThread.height = Config.WINDOW_HEIGHT;
				Display.setDisplayMode(new DisplayMode(DrawThread.width, DrawThread.height));
			}
			Display.setResizable(true);
			Display.setTitle(Config.TITLE);
			Display.setFullscreen(Config.FULLSCREEN);
			Display.create();

			// Load resources
			for (Resources res : Resources.values()) {
				res.load();
			}

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glOrtho(0, DrawThread.width, DrawThread.height, 0, 1, -1);
			GL11.glClearColor(0, 0, 0, 0);
		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to create display.");
			System.exit(0);
		}
	}

	private void checkClose() {
		if (Display.isCloseRequested()) {
			this.running = false;
			Display.destroy();
		}
	}

	@Override
	public void run() {
		this.init();
		while (this.running) {
			if (Display.wasResized()) {
				DrawThread.width = Display.getWidth();
				DrawThread.height = Display.getHeight();

				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glViewport(0, 0, DrawThread.width, DrawThread.height);
				GL11.glOrtho(0, DrawThread.width, DrawThread.height, 0, 1, -1);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();

				this.currentScreen.resize();
			}
			this.currentScreen.tick();

			// Clear the canvas
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			if (Mouse.isButtonDown(0) && this.mouseWasUp) {
				for (int i = 4; i >= 0; i--) {
					RenderPriority priority = RenderPriority.getRenderPriorityFromId(i);
					if (this.drawObjects.containsKey(priority)) {
						for (Drawable obj : this.drawObjects.get(priority)) {
							if (obj.clickHandler()) {
								break;
							}
						}
					}
				}
			}
			this.mouseWasUp = !Mouse.isButtonDown(0);

			// Draw the objects
			for (int i = 0; i <= 4; i++) {
				RenderPriority priority = RenderPriority.getRenderPriorityFromId(i);
				if (this.drawObjects.containsKey(priority)) {
					for (Drawable obj : this.drawObjects.get(priority)) {
						obj.draw2d();
					}
				}
			}

			// Update the output
			Display.update();
			Display.sync(Config.FRAMERATE);

			// Check if we should end
			this.checkClose();
		}
	}

	public void draw(Drawable obj) {
		this.draw(obj, RenderPriority.Normal);
	}

	public void draw(Drawable obj, RenderPriority priority) {
		if (!this.drawObjects.containsKey(priority)) {
			this.drawObjects.put(priority, new HashSet<Drawable>());
		}
		this.drawObjects.get(priority).add(obj);
	}

	public boolean removeObject(Drawable obj) {
		boolean result = false;
		for (RenderPriority priority : this.drawObjects.keySet()) {
			result |= this.drawObjects.get(priority).remove(obj);
		}
		return result;
	}

	public void clearScreen() {
		this.drawObjects.clear();
	}

	public void changeScreen(Screen screen) {
		this.currentScreen = screen;
		this.currentScreen.resize();
	}

}
