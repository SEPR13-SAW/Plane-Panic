package com.planepanic.game.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.planepanic.game.model.Vector2d;

/**
 * A simple drawable
 * 
 * @author Thomas Cheyney
 */
public class Image extends Drawable {

	private Texture texture;

	public Image(Resources res) {
		this.texture = res.getTexture();
		this.setHitboxSize(new Vector2d(this.texture.getTextureWidth(), this.texture.getTextureHeight()));
	}

	@Override
	public void draw2d() {
		Color.white.bind();
		this.texture.bind();
		DrawUtil.drawImg((float) this.position.getX(), (float) this.position.getY(), (float) this.getHitboxSize().getX(), (float) this.getHitboxSize().getY());
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
