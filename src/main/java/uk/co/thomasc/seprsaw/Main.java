package uk.co.thomasc.seprsaw;

import uk.co.thomasc.seprsaw.gfx.DrawThread;
import uk.co.thomasc.seprsaw.gfx.Image;
import uk.co.thomasc.seprsaw.gfx.Resources;

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