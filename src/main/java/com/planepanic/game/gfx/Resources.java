package com.planepanic.game.gfx;

import java.io.IOException;

import lombok.Getter;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.planepanic.game.Config;


/**
 * Resources/Textures available
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
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/" + file));
			
			if (Config.DEBUG) {
				System.out.println("Texture loaded: "+texture);
				System.out.println(">> Image width: "+texture.getImageWidth());
				System.out.println(">> Image height: "+texture.getImageHeight());
				System.out.println(">> Texture width: "+texture.getTextureWidth());
				System.out.println(">> Texture height: "+texture.getTextureHeight());
				System.out.println(">> Texture ID: "+texture.getTextureID());
			}
		} catch (IOException e) {
			System.out.println("Failed to load texture '" + file + "'");
		}
	}
}