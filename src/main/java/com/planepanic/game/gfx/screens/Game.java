package com.planepanic.game.gfx.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.ui.ExclusionZone;
import com.planepanic.game.gfx.ui.OrderPanel;
import com.planepanic.game.gfx.ui.Radar;
import com.planepanic.game.model.Airport;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;
import com.planepanic.game.model.orders.AbsoluteHeading;
import com.planepanic.game.model.orders.ChangeSpeed;
import com.planepanic.game.model.orders.RelativeHeading;

public class Game extends Screen {

	@Getter private OrderPanel orderpanel;
	private Radar radar;
	@Getter @Setter int ticks = 0, maxSpawnInterval = 5 * Config.FRAMERATE, minSpawnInterval = 4 * Config.FRAMERATE, maxTicks = this.maxSpawnInterval;
	private List<EntryPoint> entryPointList = new ArrayList<>();
	private List<Plane> planeList = new ArrayList<>();
	@Getter private List<ExclusionZone> exclusionZoneList = new ArrayList<>();
	@Getter @Setter ExclusionZone ez;
	/**
	 * Exclusion in meters divided by how much meters one pixel represents.
	 * Final version should have two depending on altitude
	 */
	@Getter private final static int exclusionZone = 305 / Config.SCALE;
	private Random random = new Random();

	public Game() {
		super();

		DrawThread draw = DrawThread.getInstance();

		EntryPoint entry = new EntryPoint(new Vector2d(50, 50));
		this.entryPointList.add(entry);
		draw.draw(entry);
		EntryPoint entry2 = new EntryPoint(new Vector2d(50, 200));
		this.entryPointList.add(entry);
		draw.draw(entry2);
		this.createEntryPoint(new Vector2d(50, 500));
		this.createEntryPoint(new Vector2d(500, 500));
		this.createEntryPoint(new Vector2d(500, 50));

		for (int i = 0; i < 6; i++) {
			Waypoint wp = new Waypoint(new Vector2d(200 + 75 * i, 400), "" + (char) (65 + i));
			draw.draw(wp);
		}

		Plane plane = entry2.addPlane();
		this.planeList.add(plane);
		plane.getOrders().add(new AbsoluteHeading(0));
		plane.getOrders().add(new AbsoluteHeading(Math.PI / 2));
		plane.getOrders().add(new RelativeHeading(plane.getAngle(), Math.PI / 2));
		plane.getOrders().add(new ChangeSpeed(plane.getSpeed(), 100));
		draw.draw(plane);
		draw.draw(plane.getEz());
		plane = entry.addPlane();
		this.planeList.add(plane);
		draw.draw(plane);
		draw.draw(plane.getEz());
		this.radar = new Radar();
		draw.draw(this.radar);
		Airport airport = new Airport(new Vector2d(400, Config.WINDOW_HEIGHT / 2));
		draw.draw(airport);

		this.orderpanel = new OrderPanel(new Vector2d(1100, 360));
		draw.draw(this.orderpanel);
	}

	public void spawnPlane() {
		if (this.getTicks() == this.getMaxTicks()) {
			int index = this.random.nextInt(this.entryPointList.size());
			Plane plane = this.entryPointList.get(index).addPlane();
			DrawThread draw = DrawThread.getInstance();
			draw.draw(plane);
			draw.draw(plane.getEz());
			this.planeList.add(plane);
			this.setMaxTicks(this.getMinSpawnInterval() + this.random.nextInt(this.getMaxSpawnInterval() - this.getMinSpawnInterval()));
			this.setTicks(0);
		} else {
			this.setTicks(this.getTicks() + 1);
		}
	};

	public void createEntryPoint(Vector2d position) {
		EntryPoint entry = new EntryPoint(position);
		this.entryPointList.add(entry);
		DrawThread draw = DrawThread.getInstance();
		draw.draw(entry);
	}

	@Override
	public void tick() {
		this.exclusionZoneDetection();
		this.spawnPlane();
		// Update Fuel Counter
		this.orderpanel.tick();
	}

	/**
	 * Loops through all the planes and checks whether the distance between any
	 * two is bigger than exclusion zone
	 */
	public void exclusionZoneDetection() {
		for (int i = 0; i < this.planeList.size() - 1; i++) {
			for (int o = i + 1; o < this.planeList.size(); o++) {
				if (this.planeList.get(i).distanceFrom(this.planeList.get(o)) < Game.exclusionZone * Game.exclusionZone) {
					this.planeList.get(i).getEz().setViolated(true);
					this.planeList.get(o).getEz().setViolated(true);
				};
			};
		};
	};

	@Override
	public void resize() {
		this.orderpanel.setPosition(new Vector2d(DrawThread.width - 250, 360));
	}

}
