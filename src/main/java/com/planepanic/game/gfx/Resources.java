package com.planepanic.game.gfx;

import java.io.IOException;

import lombok.Getter;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.planepanic.game.Config;

/**
 * Resources/Textures available
 * 
 * @author Thomas Cheyney
 */
public enum Resources {
	TEST("achbg.png");

	@Getter private Texture texture;
	private String file;

	private Resources(String file) {
		this.file = file;
	}

	public void load() {
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.file));

			if (Config.DEBUG) {
				System.out.println("Texture loaded: " + this.texture);
				System.out.println(">> Image width: " + this.texture.getImageWidth());
				System.out.println(">> Image height: " + this.texture.getImageHeight());
				System.out.println(">> Texture width: " + this.texture.getTextureWidth());
				System.out.println(">> Texture height: " + this.texture.getTextureHeight());
				System.out.println(">> Texture ID: " + this.texture.getTextureID());
			}
		} catch (IOException e) {
			System.out.println("Failed to load texture '" + this.file + "'");
		}
	}
}
