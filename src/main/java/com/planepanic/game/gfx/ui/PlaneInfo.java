package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;

public class PlaneInfo {

	private TextBox altitudeDisplay;
	private TextBox speedDisplay;
	private TextBox fuelDisplay;

	public PlaneInfo(OrderPanel panel) {
		DrawThread draw = DrawThread.getInstance();
		draw.draw(new TextBox("Altitude").setColor(0x000000).setAlign(Align.RIGHT).setPosition(new Vector2d(1100, 25)).setPriority(0.9f));
		draw.draw(new TextBox("Speed").setColor(0x000000).setAlign(Align.RIGHT).setPosition(new Vector2d(1100, 70)).setPriority(0.9f));
		draw.draw(new TextBox("Fuel").setColor(0x000000).setAlign(Align.RIGHT).setPosition(new Vector2d(1100, 115)).setPriority(0.9f));

		draw.draw(this.altitudeDisplay = (TextBox) new TextBox("0").setColor(0x000000).setAlign(Align.LEFT).setPosition(new Vector2d(1150, 25)).setPriority(0.9f));
		draw.draw(this.speedDisplay = (TextBox) new TextBox("0").setColor(0x000000).setAlign(Align.LEFT).setPosition(new Vector2d(1150, 70)).setPriority(0.9f));
		draw.draw(this.fuelDisplay = (TextBox) new TextBox("0").setColor(0x000000).setAlign(Align.LEFT).setPosition(new Vector2d(1150, 115)).setPriority(0.9f));
	}

	public void tick() {
		Plane plane;
		if ((plane = Plane.getSelected()) != null) {
			String altitude = String.valueOf((int) plane.getAltitude());
			this.altitudeDisplay.setText(altitude);
			String speed = String.valueOf((int) plane.getSpeed());
			this.speedDisplay.setText(speed);
			String fuel = String.valueOf((float) (plane.getFuel() / 181965 * 100));
			fuel = fuel.substring(0, 2);
			this.fuelDisplay.setText(fuel + "%");
		}
	}

}
