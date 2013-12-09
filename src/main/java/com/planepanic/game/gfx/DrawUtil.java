package com.planepanic.game.gfx;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

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

	private static Map<Integer, TrueTypeFont> ttf = new HashMap<Integer, TrueTypeFont>();

	private static TrueTypeFont getFont(int size) {
		if (!ttf.containsKey(size)) {
			Font font = new Font("Verdana", Font.BOLD, size);
			DrawUtil.ttf.put(size, new TrueTypeFont(font, true));
		}
		return ttf.get(size);
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
		DrawUtil.drawSquare(x, y, w, h, true);
	}

	public static void drawSquare(float x, float y, float w, float h, boolean filled) {
		GL11.glBegin(filled ? GL11.GL_QUADS : GL11.GL_LINE_LOOP);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + w, y);
		GL11.glVertex2f(x + w, y + h);
		GL11.glVertex2f(x, y + h);
		GL11.glEnd();
	}

	public static void drawCircle(float x, float y, float w, float h) {
		DrawUtil.drawSquare(x, y, w, h, true);
	}

	public static void drawCircle(float x, float y, float r, boolean filled) {
		GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_LOOP);
		for (int i = 0; i < 360; i += 5) {
			GL11.glVertex2f((float) (x + Math.sin(Math.toRadians(i)) * r), (float) (y + Math.cos(Math.toRadians(i)) * r));
		}
		GL11.glEnd();
	}

	public static void rotate(float r) {
		GL11.glPushMatrix();
		GL11.glRotatef(r, 0, 0, 1);
	}

	public static void pop() {
		GL11.glPopMatrix();
	}

	public static void drawImg(float x, float y, float w, float h, float tw, float th, float r) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(r, 0, 0, 1);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(-w / 2, -h / 2);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(-w / 2 + tw, -h / 2);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(-w / 2 + tw, -h / 2 + th);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(-w / 2, -h / 2 + th);

		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public static Vector2d getSize(String text) {
		return getSize(text, 32);
	}
	
	public static Vector2d getSize(String text, int size) {
		return new Vector2d(DrawUtil.getFont(size).getWidth(text), DrawUtil.getFont(size).getHeight(text));
	}

	public static void drawString(float x, float y, String text) {
		DrawUtil.drawString(x, y, text, 0xFFFFFF);
	}
	
	public static void drawString(float x, float y, String text, int color) {
		DrawUtil.drawString(x, y, text, color, 32);
	}

	public static void drawString(float x, float y, String text, int color, int size) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		DrawUtil.getFont(size).drawString(x, y, text, new Color(color));
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
