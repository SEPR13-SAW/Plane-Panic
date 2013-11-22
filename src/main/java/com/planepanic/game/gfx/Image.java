package com.planepanic.game.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

/**
 * A simple drawable
 * @author Thomas Cheyney
 */
public class Image extends Drawable {

	private Texture texture;
	
	public Image(Resources res) {
		texture = res.getTexture();
	}
	
	@Override
	public void draw2d() {
		Color.white.bind();
		texture.bind();
		DrawUtil.drawImg((int) position.getX(), (int) position.getY(), texture.getTextureWidth(), texture.getTextureHeight());
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub
		
	}
	
}