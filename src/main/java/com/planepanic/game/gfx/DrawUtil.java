package com.planepanic.game.gfx;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.planepanic.game.model.Vector2d;

/**
 * Helpers to get things done in OpenGL
 * 
 * @author Thomas Cheyney
 */
public class DrawUtil {

	private static TrueTypeFont ttf;

	static {
		Font font = new Font("Verdana", Font.BOLD, 32);
		DrawUtil.ttf = new TrueTypeFont(font, true);
	}

	public static float getRed(int color) {
		return (color >> 16 & 0x000000FF) / 255f;
	}

	public static float getGreen(int color) {
		return (color >> 8 & 0x000000FF) / 255f;
	}

	public static float getBlue(int color) {
		return (color & 0x000000FF) / 255f;
	}

	public static void setColor(int color) {
		GL11.glColor3f(DrawUtil.getRed(color), DrawUtil.getGreen(color), DrawUtil.getBlue(color));
	}

	public static void drawSquare(float x, float y, float w, float h) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + w, y);
		GL11.glVertex2f(x + w, y + h);
		GL11.glVertex2f(x, y + h);
		GL11.glEnd();
	}

	public static void drawImg(float x, float y, float w, float h) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + w, y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + w, y + h);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + h);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public static Vector2d getSize(String text) {
		return new Vector2d(DrawUtil.ttf.getWidth(text), DrawUtil.ttf.getHeight(text));
	}

	public static void drawString(float x, float y, String text) {
		DrawUtil.drawString(x, y, text, 0xFFFFFF);
	}

	public static void drawString(float x, float y, String text, int color) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		DrawUtil.ttf.drawString(x, y, text, new Color(color));
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
