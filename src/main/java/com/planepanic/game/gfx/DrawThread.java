package com.planepanic.game.gfx;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
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
	private List<Drawable> drawObjects = new ArrayList<Drawable>();
	private Queue<Drawable> temp = new PriorityQueue<Drawable>(11, new Comparator<Drawable>() {
		@Override
		public int compare(Drawable arg0, Drawable arg1) {
			return arg1.getPriority() > arg0.getPriority() ? -1 : arg1.getPriority() < arg0.getPriority() ? 1 : 0;
		}
	});
	private Queue<Drawable> tempReverse = new PriorityQueue<Drawable>(11, new Comparator<Drawable>() {
		@Override
		public int compare(Drawable arg0, Drawable arg1) {
			return arg1.getPriority() > arg0.getPriority() ? 1 : arg1.getPriority() < arg0.getPriority() ? -1 : 0;
		}
	});
	private boolean mouseWasUp = true;

	/*
	 * For right click handling
	 */
	private boolean mouseRightWasUp = true;
	@Getter private Screen currentScreen;

	public static int width = 1280;
	public static int height = 720;

	@Getter @Setter private static Drawable focus = null;

	private final boolean[] wasKeyDown = new boolean[256];
	private final int[] keys = new int[] {
			Keyboard.KEY_0, Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, Keyboard.KEY_4, 
			Keyboard.KEY_5, Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9, 

			Keyboard.KEY_A, Keyboard.KEY_B, Keyboard.KEY_C, Keyboard.KEY_D, Keyboard.KEY_E, 
			Keyboard.KEY_E, Keyboard.KEY_F, Keyboard.KEY_G, Keyboard.KEY_H, Keyboard.KEY_I, 
			Keyboard.KEY_J, Keyboard.KEY_K, Keyboard.KEY_L, Keyboard.KEY_M, Keyboard.KEY_N, 
			Keyboard.KEY_O, Keyboard.KEY_P, Keyboard.KEY_Q, Keyboard.KEY_R, Keyboard.KEY_S, 
			Keyboard.KEY_T, Keyboard.KEY_U, Keyboard.KEY_V, Keyboard.KEY_W, Keyboard.KEY_X, 
			Keyboard.KEY_Y, Keyboard.KEY_Z,

			Keyboard.KEY_BACK, Keyboard.KEY_DELETE,
			Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT,
	};

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
			GL11.glEnable(GL11.GL_DEPTH_TEST);
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
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			if (Mouse.isButtonDown(0) && this.mouseWasUp) {
				this.tempReverse.addAll(this.drawObjects);
				Drawable obj;
				while ((obj = this.tempReverse.poll()) != null) {
					if (obj.clickHandler()) {
						focus = obj;
						break;
					}
				}
				this.tempReverse.clear();
			}
			this.mouseWasUp = !Mouse.isButtonDown(0);

			if (Mouse.isButtonDown(1) && this.mouseRightWasUp) {
				this.tempReverse.addAll(this.drawObjects);
				Drawable obj;
				while ((obj = this.tempReverse.poll()) != null) {
					if (obj.clickRightHandler()) {
						break;
					}
				}
				this.tempReverse.clear();
			}
			this.mouseRightWasUp = !Mouse.isButtonDown(1);

			int scroll = Mouse.getDWheel();
			if (scroll != 0) {
				this.tempReverse.addAll(this.drawObjects);
				Drawable obj;
				while ((obj = this.tempReverse.poll()) != null) {
					if (obj.scrollHandler(scroll)) {
						break;
					}
				}
				this.tempReverse.clear();
			}

			for (int key : keys) {
				if (Keyboard.isKeyDown(key) && !wasKeyDown[key]) {
					wasKeyDown[key] = true;
					if (focus != null) {
						focus.keyPress(key);
					}
				} else if (wasKeyDown[key] && !Keyboard.isKeyDown(key)) {
					wasKeyDown[key] = false;
				}
			}

			// Draw the objects
			this.temp.addAll(this.drawObjects);
			Drawable obj;
			while ((obj = this.temp.poll()) != null) {
				obj.draw2d();
			}

			// Update the output
			Display.update();
			Display.sync(Config.FRAMERATE);

			// Check if we should end
			this.checkClose();
		}
	}

	public void draw(Drawable obj) {
		this.drawObjects.add(obj);
	}

	public boolean removeObject(Drawable obj) {
		return this.drawObjects.remove(obj);
	}

	public void clearScreen() {
		this.drawObjects.clear();
	}

	public void changeScreen(Screen screen) {
		this.currentScreen = screen;
		this.currentScreen.resize();
	}

}
