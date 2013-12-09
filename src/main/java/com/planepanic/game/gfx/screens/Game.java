package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.orders.AbsoluteHeading;

public class Game extends Screen {

	public Game() {
		super();

		DrawThread draw = DrawThread.getInstance();

		EntryPoint entry = new EntryPoint(new Vector2d(50, 50));
		draw.draw(entry);
		Plane plane = entry.addPlane();
		plane.getOrders().add(new AbsoluteHeading(0));
		plane.getOrders().add(new AbsoluteHeading(Math.PI / 2));
		draw.draw(plane);
	}

	@Override
	public void resize() {
		// TODO Auto-generated method stub

	}

}
