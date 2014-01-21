package com.planepanic.game.gfx;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.planepanic.game.model.Vector2d;

/**
 * A simple drawable that draws a texture
 * 
 * @author Jonathan, Thomas
 */
public class Image extends Drawable {
	/**
	 * Set to false in unit tests to prevent texture loading.
	 */
	public static boolean LOAD_TEXTURES = true;

	private Texture texture;
	@Getter @Setter(AccessLevel.PROTECTED) private float angle = 0;

	public Image(Resources res) {
		this(res, new Vector2d());
	}

	public Image(Resources res, Vector2d position) {
		super(position);

		if (!LOAD_TEXTURES) return;

		this.texture = res.getTexture();
		if (this.texture != null) {
			this.setHitboxSize(new Vector2d(this.texture.getImageWidth(), this.texture.getImageHeight()));
		}
	}

	@Override
	public void draw2d() {
		Color.white.bind();
		this.texture.bind();
		DrawUtil.drawImg((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) this.getHitboxSize().getX(), (float) this.getHitboxSize().getY(), this.texture.getTextureWidth(), this.texture.getTextureHeight(), this.angle, this.getPriority());
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}

}
