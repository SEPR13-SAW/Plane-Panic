package com.planepanic.game.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.planepanic.game.model.orders.Vector2d;

/**
 * A simple drawable
 * @author Thomas Cheyney
 */
public class Image extends Drawable {

	private Texture texture;
	
	public Image(Resources res) {
		texture = res.getTexture();
		setHitboxSize(new Vector2d(texture.getTextureWidth(), texture.getTextureHeight()));
	}
	
	@Override
	public void draw2d() {
		Color.white.bind();
		texture.bind();
		DrawUtil.drawImg((float) position.getX(), (float) position.getY(), (float) getHitboxSize().getX(), (float) getHitboxSize().getY());
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onClick() {
		return false;
	}
	
}