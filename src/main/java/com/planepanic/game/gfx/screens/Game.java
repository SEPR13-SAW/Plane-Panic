package com.planepanic.game.gfx.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.RenderPriority;
import com.planepanic.game.gfx.ui.Radar;
import com.planepanic.game.model.Airport;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;
import com.planepanic.game.model.orders.AbsoluteHeading;
import com.planepanic.game.model.orders.RelativeHeading;

public class Game extends Screen {

	Radar radar;
	@Getter @Setter int ticks = 0, maxSpawnInterval = 20 * Config.FRAMERATE, minSpawnInterval = 10 * Config.FRAMERATE, maxTicks = this.maxSpawnInterval;
	private List<EntryPoint> entryPointList = new ArrayList<>();
	private List<Plane> planeList = new ArrayList<>();

	public Game() {
		super();

		DrawThread draw = DrawThread.getInstance();

		EntryPoint entry = new EntryPoint(new Vector2d(50, 50));
		this.entryPointList.add(entry);
		draw.draw(entry, RenderPriority.High);

		for (int i = 0; i < 6; i++) {
			Waypoint wp = new Waypoint(new Vector2d(200 + 75 * i, 400), "" + (char) (65 + i));
			draw.draw(wp, RenderPriority.High);
		}

		Plane plane = entry.addPlane();
		this.planeList.add(plane);
		plane.getOrders().add(new AbsoluteHeading(0));
		plane.getOrders().add(new AbsoluteHeading(Math.PI / 2));
		plane.getOrders().add(new RelativeHeading(plane.getAngle(), Math.PI / 2));
		draw.draw(plane, RenderPriority.Low);
		this.radar = new Radar(this);
		draw.draw(this.radar, RenderPriority.Highest);
		Airport airport = new Airport(new Vector2d(400, Config.WINDOW_HEIGHT / 2));
		draw.draw(airport, RenderPriority.Low);

	}

	public void spawnPlane(Random rng) {
		if (this.getTicks() == this.getMaxTicks()) {
			int index = rng.nextInt(this.entryPointList.size());
			Plane plane = this.entryPointList.get(index).addPlane();
			DrawThread draw = DrawThread.getInstance();
			draw.draw(plane, RenderPriority.Low);
			this.planeList.add(plane);
			this.setMaxTicks(this.getMinSpawnInterval() + rng.nextInt(this.getMaxSpawnInterval() - this.getMinSpawnInterval()));
			this.setTicks(0);
		} else {
			this.setTicks(this.getTicks() + 1);
		}
	};

	@Override
	public void resize() {
		this.radar.setPosition(new Vector2d((DrawThread.width - 500) / 2, DrawThread.height / 2));
		this.radar.onResize();
	}

}
