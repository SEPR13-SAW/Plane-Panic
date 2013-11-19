package com.planepanic.game;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;

/**
 * Example main
 * @author Thomas Cheyney
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		DrawThread draw = new DrawThread();
		
		draw.start();
		Thread.sleep(1000);
		Image img = new Image(Resources.TEST);
		draw.draw(img);
	}
	
}