package com.planepanic.game.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import org.lwjgl.input.Mouse;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.gfx.screens.Game;
import com.planepanic.game.model.orders.FlyBy;
import com.planepanic.game.model.orders.FlyOver;
import com.planepanic.game.model.orders.Order;

/**
 * Plane class
 * 
 * @author Ben, Jonathan, Mantas, Thomas
 */
public final class Plane extends Image {

	@Getter private static Plane selected;

	@Getter private final PlaneType type;
	@Getter private final int passengers;
	@Getter @Setter private int score;
	@Getter @Setter private double fuel;
	/**
	 * Simple linear speed, to avoid having to calculate length of the vector
	 * every tick
	 */
	@Getter @Setter private double speed;
	@Getter @Setter private double altitude;
	@Getter @Setter private Vector2d velocity;
	@Getter @Setter private int scoreTickDelay = Config.FRAMERATE;
	@Getter @Setter private int gracePeriod = 30;
	@Getter @Setter private boolean violated = false;
	@Getter private final Game game;

	@Getter private final Queue<Order> orders = new ArrayDeque<Order>(64);

	private Random random = new Random();

	public Plane(Game game, PlaneType type, int passengers, double fuel, double speed, Vector2d position, Resources sprite, int score, double altitude) {
		super(sprite, position);
		this.setPriority(-0.1f);
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;
		this.speed = speed;
		this.velocity = Vector2d.fromAngle(-Math.PI / 2).mul(this.getSpeed() * 10 / Config.FRAMERATE);
		this.score = score;
		this.altitude = altitude;
		this.game = game;
	}

	@Override
	public void draw2d() {
		this.setPriority((float) -(0.1 - this.getAltitude() / 50000));
		super.draw2d();
		this.tick();
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	public void checkOutOfBounds() {
		if ((this.getPosition().getX() < 0
				|| this.getPosition().getX() > 896
				|| this.getPosition().getY() < 0
				|| this.getPosition().getY() > 720)
				&& this.orders.size() == 0) {
			List<Waypoint> temp = new ArrayList<Waypoint>();
			temp.addAll(this.game.getWaypointList());

			Waypoint waypointA = this.game.getWaypointList().get(this.random.nextInt(this.game.getWaypointList().size()));

			temp.remove(waypointA);

			Waypoint waypointB = temp.get(this.random.nextInt(temp.size()));

			if (waypointA != waypointB) {
				if (this.random.nextInt(2) == 0) {
					this.addOrder(new FlyBy(this, waypointA, waypointB));
				} else {
					this.addOrder(new FlyOver(this, waypointA, waypointB));
				}

			}
		}
	}

	@Override
	public boolean onClick() {
		if (Plane.selected != this) {
			Waypoint.setVia(null);
		}

		Plane.selected = this;

		return true;
	}

	public Order getCurrentOrder() {
		return this.orders.peek();
	}

	public void tick() {
		this.consumeFuel();
		this.decayScore();
		this.checkOutOfBounds();

		Order order = this.getCurrentOrder();
		if (order != null) {
			order.tick();
			if (order.isComplete()) {
				this.orders.poll();
				if (this.orders.size() > 0) {
					this.getCurrentOrder().start();
				}
			}
		}
		this.getPosition().applyChange(this.getVelocity());
		if (this.violated) {
			DrawUtil.setColor(0xff0000);
			DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), Game.getExclusionZone(), false, this.getPriority() + 0.01f);
		} else if (Plane.selected == this) {
			DrawUtil.setColor(0x00ff00);
			DrawUtil.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), Game.getExclusionZone(), false, this.getPriority() + 0.01f);
		}
		this.violated = false;

		this.setAngle(90 - (float) Math.toDegrees(this.getVelocity().getAngle()));
	}

	public void addOrder(Order order) {
		this.orders.add(order);
		if (this.orders.size() == 1) {
			this.getCurrentOrder().start();
		}
	}

	public static Plane randomPlane(Game game, Random rng, Vector2d position) {
		int index = rng.nextInt(PlaneType.values().length);
		PlaneType type = PlaneType.values()[index];

		int passengers = type.getMaxPassengers() / 2 + rng.nextInt(type.getMaxPassengers() / 2);
		double fuel = type.getMaxFuel() / 2 + rng.nextDouble() * type.getMaxFuel() / 2;
		double speed = type.getMaxVelocity() / 2 + rng.nextDouble() * type.getMaxVelocity() / 2;
		double altitude = type.getMaxAltitude() * 0.7 + rng.nextDouble() * type.getMaxAltitude() * 0.3;

		int score = type.getScore();

		return new Plane(game, type, passengers, fuel, speed, position, Resources.PLANE, score, altitude);
	}

	/**
	 * Calculates the fuel consumption in l/s scaling by how much of a max speed
	 * plane is flying
	 */
	public void consumeFuel() {
		this.setFuel(this.getFuel() - this.type.getFuelConsumption() / Config.FRAMERATE * (this.getSpeed() / this.type.getMaxVelocity()));
	};

	/**
	 * Converts speed in m/s and starting angle, to a Cartesian vector
	 * 
	 * @param angle for simpler use in other calculations
	 * @return A vector representing the plane's velocity
	 */
	public Vector2d convertSpeedToVelocity(double angle) {
		return Vector2d.fromAngle(angle).mul(this.getSpeed() * 10 / Config.FRAMERATE);
	}

	public void decayScore() {
		this.setScoreTickDelay(this.getScoreTickDelay() - 1);

		if (this.getScoreTickDelay() == 0 && this.getGracePeriod() > 0) {
			this.setGracePeriod(this.getGracePeriod() - 1);
			this.setScoreTickDelay(Config.FRAMERATE);
		}

		if (this.getScore() > 0 && this.getScoreTickDelay() == 0 && this.getGracePeriod() == 0) {
			this.setScore(this.getScore() - 10);
			this.setScoreTickDelay(Config.FRAMERATE);
			// System.out.println("Score: " + score); /* Used to Track Scores
			// for Testing */
		}
	}

	/**
	 * Calculates the squared distance between two planes in 3D
	 * 
	 * @param plane The other plane
	 * @return The distance between the points
	 */
	public double distanceFrom(Plane plane) {
		return plane.getPosition().distanceFrom(this.getPosition()) + (plane.getAltitude() - this.getAltitude()) * (plane.getAltitude() - this.getAltitude()) / Config.SCALE;
	}

	@Override
	protected boolean isMouseOver() {
		int x = Mouse.getX();
		int y = DrawThread.height - Mouse.getY();
		return new Vector2d(x, y).distanceFrom(this.getPosition()) < 625;
	}

}
