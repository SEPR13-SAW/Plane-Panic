package com.planepanic.game.gfx;

import org.lwjgl.opengl.GL11;

/**
 * Helpers to get things done in OpenGL
 * @author Thomas Cheyney
 */
public class DrawUtil {
	
	public static float getRed(int color) {
		return ((color >> 16) & 0x000000FF) / 255f;
	}
	
	public static float getGreen(int color) {
		return ((color >> 8) & 0x000000FF) / 255f;
	}
	
	public static float getBlue(int color) {
		return (color & 0x000000FF) / 255f;
	}
	
	public static void setColor(int color) {
		GL11.glColor3f(getRed(color),getGreen(color),getBlue(color));
	}
	
	public static void drawSquare(int x, int y, int w, int h) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x,y);
		GL11.glVertex2f(x+w,y);
		GL11.glVertex2f(x+w,y+h);
		GL11.glVertex2f(x,y+h);
		GL11.glEnd();
	}
	
	public static void drawImg(int x, int y, int w, int h) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x,y);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x+w,y);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x+w,y+h);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(x,y+h);
		GL11.glEnd();
	}
	
}