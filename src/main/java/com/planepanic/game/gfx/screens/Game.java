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
import com.planepanic.game.gfx.ui.Timer;
import com.planepanic.game.model.Airport;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.ExitPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;
import com.planepanic.game.model.orders.AbsoluteHeading;
import com.planepanic.game.model.orders.ChangeSpeed;
import com.planepanic.game.model.orders.FlyBy;
import com.planepanic.game.model.orders.FlyOver;
import com.planepanic.game.model.orders.RelativeHeading;

public class Game extends Screen {

	@Getter private OrderPanel orderpanel;
	@Getter private Timer timer;
	private Radar radar;
	@Getter @Setter int maxSpawnInterval = 5, minSpawnInterval = 4, spawnInterval = this.maxSpawnInterval;
	private List<EntryPoint> entryPointList = new ArrayList<>();
	@Getter private List<Plane> planeList = new ArrayList<>();
	@Getter private List<ExclusionZone> exclusionZoneList = new ArrayList<>();
	@Getter private List<Waypoint> waypointList = new ArrayList<>();
	@Getter @Setter ExclusionZone ez;
	/**
	 * Exclusion in meters divided by how much meters one pixel represents.
	 * Final version should have two depending on altitude
	 */
	@Getter private final static int exclusionZone = 610 / Config.SCALE;
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
			this.waypointList.add(new Waypoint(new Vector2d(200 + 75 * i, 400), "" + (char) (65 + i)));
			draw.draw(this.waypointList.get(i));
		}

		Plane plane = entry2.addPlane();
		this.planeList.add(plane);
		plane.getOrders().add(new AbsoluteHeading(plane, 0));
		plane.getOrders().add(new AbsoluteHeading(plane, -Math.PI / 2));
		plane.getOrders().add(new RelativeHeading(plane, Math.PI / 2));
		plane.getOrders().add(new ChangeSpeed(plane, 100));
		plane.getOrders().add(new FlyOver(plane, this.waypointList.get(0), this.waypointList.get(2)));
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
		this.timer = new Timer(new Vector2d(325, 0));
		draw.draw(this.timer);
		ExitPoint exit = new ExitPoint(new Vector2d(700, 300));
		draw.draw(exit);
		this.orderpanel = new OrderPanel(new Vector2d(1100, 360));
		draw.draw(this.orderpanel);
	}

	/*
	 * Spawns planes every spawnInterval seconds which is minSpawnInterval <
	 * spawnInterval < maxSpawnInterval Spawns the first plane immediately
	 */
	public void spawnPlane() {
		if (this.getTimer().getSeconds() % this.getSpawnInterval() == 0 && this.getTimer().getTicks() == 0) {
			int index = this.random.nextInt(this.entryPointList.size());
			Plane plane = this.entryPointList.get(index).addPlane();
			DrawThread draw = DrawThread.getInstance();
			draw.draw(plane);
			draw.draw(plane.getEz());
			this.planeList.add(plane);
			this.setSpawnInterval(this.getMinSpawnInterval() + this.random.nextInt(this.getMaxSpawnInterval() - this.getMinSpawnInterval()));
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
		this.timer.tick();
		this.giveOrder();
	}

	/**
	 * If a plane and two different waypoints are selected gives the plane a new
	 * FlyOver order through those waypoints
	 */

	public void giveOrder() {
		if (Waypoint.getVia() != null) {
			if (Waypoint.getTarget() != null) {
				if (Plane.getSelected() != null) {
					if (Waypoint.isFlyBy()) {
						Plane.getSelected().getOrders().add(new FlyBy(Plane.getSelected(), Waypoint.getVia(), Waypoint.getTarget()));
					} else {
						Plane.getSelected().getOrders().add(new FlyOver(Plane.getSelected(), Waypoint.getVia(), Waypoint.getTarget()));
					}
					Waypoint.setVia(null);
					Waypoint.setTarget(null);
				}
			}
		}
	}

	/**
	 * Loops through all the planes and checks whether the distance between any
	 * two is bigger than exclusion zone
	 */
	public void exclusionZoneDetection() {
		for (int i = 0; i < this.planeList.size() - 1; i++) {
			for (int o = i + 1; o < this.planeList.size(); o++) {
				if (this.planeList.get(i).distanceFrom(this.planeList.get(o)) <= Game.exclusionZone * Game.exclusionZone) {
					this.planeList.get(i).getEz().setViolated(true);
					this.planeList.get(o).getEz().setViolated(true);
					if(this.planeList.get(i).distanceFrom(this.planeList.get(o)) <= (Game.exclusionZone * Game.exclusionZone)*0.5 ) {
						
						DrawThread.getInstance().changeScreen(new com.planepanic.game.gfx.screens.GameOver());
						
					};
				};
			};
		};
	};

	@Override
	public void resize() {
		this.orderpanel.setPosition(new Vector2d(DrawThread.width - 250, 360));
		this.orderpanel.onMove();
	}

}
