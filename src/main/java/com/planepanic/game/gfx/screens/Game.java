package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.RenderPriority;
import com.planepanic.game.gfx.ui.Radar;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;
import com.planepanic.game.model.orders.AbsoluteHeading;

public class Game extends Screen {
	
	Radar radar;

	public Game() {
		super();

		DrawThread draw = DrawThread.getInstance();

		EntryPoint entry = new EntryPoint(new Vector2d(50, 50));
		draw.draw(entry, RenderPriority.High);
		
		for (int i = 0; i < 6; i++) {
			Waypoint wp = new Waypoint(new Vector2d(200 + 75 * i, 400), "" + (char) (65 + i));
			draw.draw(wp, RenderPriority.High);
		}
		
		Plane plane = entry.addPlane();
		plane.getOrders().add(new AbsoluteHeading(0));
		plane.getOrders().add(new AbsoluteHeading(Math.PI / 2));
		draw.draw(plane, RenderPriority.Low);
		
		radar = new Radar();
		draw.draw(radar, RenderPriority.Highest);
	}

	@Override
	public void resize() {
		radar.setPosition(new Vector2d((DrawThread.width - 500) / 2, DrawThread.height / 2));
		radar.onResize();
	}

}
