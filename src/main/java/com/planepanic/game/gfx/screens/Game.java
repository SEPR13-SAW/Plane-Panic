package com.planepanic.game.gfx.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
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
import com.planepanic.game.model.orders.LeaveAirspace;
import com.planepanic.game.model.orders.RelativeHeading;

/**
 * Game screen Does most of the setup and glue logic
 * 
 * @author Ben, Jonathan, Mantas, Steven, Thomas
 * 
 */
public class Game extends Screen {

	private final int[][] waypointLocations = { { 50, 50 }, { 300, 50 }, { 600, 50 }, { 600, 300 }, { 600, 600 }, { 300, 600 }, { 50, 600 }, { 50, 300 } };
	@Getter private OrderPanel orderpanel;
	@Getter private Timer timer;
	private Radar radar;
	@Getter @Setter int maxSpawnInterval = 10, minSpawnInterval = 6, spawnInterval = this.maxSpawnInterval;
	private List<EntryPoint> entryPointList = new ArrayList<EntryPoint>();
	@Getter private List<Plane> planeList = new ArrayList<Plane>();
	@Getter private List<Waypoint> waypointList = new ArrayList<Waypoint>();
	@Getter private List<ExitPoint> exitPointList = new ArrayList<ExitPoint>();
	/**
	 * Exclusion in meters divided by how much meters one pixel represents.
	 * Final version should have two depending on altitude
	 */
	@Getter private final static int exclusionZone = 610 / Config.SCALE;
	private Random random = new Random();

	public Game() {
		super();

		DrawThread draw = DrawThread.getInstance();

		EntryPoint entry = this.createEntryPoint(new Vector2d(50, 50));
		EntryPoint entry2 = this.createEntryPoint(new Vector2d(50, 200));
		this.createEntryPoint(new Vector2d(50, 500));
		this.createEntryPoint(new Vector2d(500, 500));
		this.createEntryPoint(new Vector2d(500, 50));

		for (int i = 0; i < 8; i++) {
			this.waypointList.add(new Waypoint(new Vector2d(this.waypointLocations[i][0], this.waypointLocations[i][1]), "" + (char) (65 + i)));
			draw.draw(this.waypointList.get(i));
		}

		Plane plane = entry2.addPlane(this);
		this.planeList.add(plane);
		plane.getOrders().add(new AbsoluteHeading(plane, 0));
		plane.getOrders().add(new AbsoluteHeading(plane, -Math.PI / 2));
		plane.getOrders().add(new RelativeHeading(plane, Math.PI / 2));
		plane.getOrders().add(new ChangeSpeed(plane, 100));
		plane.getOrders().add(new FlyOver(plane, this.waypointList.get(0), this.waypointList.get(2)));
		plane.getOrders().add(new RelativeHeading(plane, Math.PI / 2));
		draw.draw(plane);
		plane = entry.addPlane(this);
		this.planeList.add(plane);
		draw.draw(plane);
		this.radar = new Radar();
		draw.draw(this.radar);
		Airport airport = new Airport(new Vector2d(448, Config.WINDOW_HEIGHT / 2));
		draw.draw(airport);
		this.timer = new Timer(new Vector2d(448, 30));
		draw.draw(this.timer);
		ExitPoint exit = new ExitPoint(new Vector2d(890, 300), "e0", this);
		draw.draw(exit);
		this.exitPointList.add(exit);
		exit = new ExitPoint(new Vector2d(350, 700), "e1", this);
		draw.draw(exit);
		this.exitPointList.add(exit);
		this.orderpanel = new OrderPanel(new Vector2d(896, 360));
		draw.draw(this.orderpanel);
	}

	/**
	 * Spawns planes every spawnInterval seconds which is minSpawnInterval <
	 * spawnInterval < maxSpawnInterval Spawns the first plane immediately
	 */
	public void spawnPlane() {
		if (this.getTimer().getSeconds() % this.getSpawnInterval() == 0 && this.getTimer().getTicks() == 0 && this.planeList.size() < 10) {
			int index = this.random.nextInt(this.entryPointList.size());
			Plane plane = this.entryPointList.get(index).addPlane(this);
			DrawThread draw = DrawThread.getInstance();
			draw.draw(plane);
			this.generateFlightPlan(plane);
			this.planeList.add(plane);
			this.setSpawnInterval(this.getMinSpawnInterval() + this.random.nextInt(this.getMaxSpawnInterval() - this.getMinSpawnInterval()));
		}
	};

	public void generateFlightPlan(Plane plane) {
		List<Waypoint> waypoints = new ArrayList<Waypoint>();
		waypoints.addAll(this.getWaypointList());
		while (this.random.nextInt(100) > 15 && waypoints.size() > 1) {
			Waypoint i = waypoints.get(this.random.nextInt(waypoints.size())), o = waypoints.get(this.random.nextInt(waypoints.size()));
			if (i != o) {
				if (this.random.nextInt(2) == 1) {
					plane.addOrder(new FlyBy(plane, i, o));
				} else {
					plane.addOrder(new FlyOver(plane, i, o));
				}
				waypoints.remove(o);
				waypoints.remove(i);
			}
		}
		plane.addOrder(new LeaveAirspace(plane, this.getExitPointList().get(this.random.nextInt(this.getExitPointList().size()))));

	}

	public EntryPoint createEntryPoint(Vector2d position) {
		EntryPoint entry = new EntryPoint(position);
		this.entryPointList.add(entry);
		DrawThread draw = DrawThread.getInstance();
		draw.draw(entry);
		return entry;
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
		if (ExitPoint.getExitPoint() != null) {
			Plane.getSelected().addOrder(new LeaveAirspace(Plane.getSelected(), ExitPoint.getExitPoint()));
			ExitPoint.setExitPoint(null);
		}

		if (Waypoint.getVia() != null) {
			if (Waypoint.getTarget() != null) {
				if (Plane.getSelected() != null) {
					if (Waypoint.isFlyBy()) {
						Plane.getSelected().addOrder(new FlyBy(Plane.getSelected(), Waypoint.getVia(), Waypoint.getTarget()));
					} else {
						Plane.getSelected().addOrder(new FlyOver(Plane.getSelected(), Waypoint.getVia(), Waypoint.getTarget()));
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
		double distance;
		for (int i = 0; i < this.planeList.size() - 1; i++) {
			for (int o = i + 1; o < this.planeList.size(); o++) {
				distance = this.planeList.get(i).distanceFrom(this.planeList.get(o));
				if (distance <= Game.exclusionZone * Game.exclusionZone) {
					this.planeList.get(i).setViolated(true);
					this.planeList.get(o).setViolated(true);
					if (distance <= Game.exclusionZone * Game.exclusionZone * 0.1) {
						DrawThread.getInstance().changeScreen(new com.planepanic.game.gfx.screens.GameOver());

					};
				};
			};
		};
	};

	@Override
	public void resize() {
		this.orderpanel.setPosition(new Vector2d(DrawThread.width - 192, 360));
		this.orderpanel.onMove();
	}

}
