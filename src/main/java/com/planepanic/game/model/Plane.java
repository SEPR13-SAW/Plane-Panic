package com.planepanic.game.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import org.lwjgl.input.Mouse;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.Image;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.gfx.ui.ExclusionZone;
import com.planepanic.game.model.orders.Order;

/**
 * Plane class.
 * 
 * @author Jonathan
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
	@Getter private ExclusionZone ez;

	@Getter private final Queue<Order> orders = new ArrayDeque<>(64);

	public Plane(PlaneType type, int passengers, double fuel, double speed, Vector2d position, Resources sprite, int score, double altitude) {
		super(sprite, position);
		this.setPriority(-0.1f);
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;
		this.speed = speed;
		this.velocity = Vector2d.fromAngle(-Math.PI / 2).mul(this.getSpeed() * 10 / Config.FRAMERATE);
		this.score = score;
		this.ez = new ExclusionZone(position);
		this.altitude = altitude;
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

	@Override
	public boolean onClick() {
		if (Plane.selected != null) {
			Plane.selected.getEz().setSelected(false);
		}
		Plane.selected = this;
		Plane.selected.getEz().setSelected(true);
		return true;
	}

	public Order getCurrentOrder() {
		return this.orders.peek();
	}

	public void tick() {
		this.consumeFuel();
		this.decayScore();

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
		this.ez.draw2d();
		this.getEz().setPosition(this.getPosition());
		this.getEz().setViolated(false);

		this.setAngle(90 - (float) Math.toDegrees(this.getVelocity().getAngle()));
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

	public static Plane randomPlane(Random rng, Vector2d position) {
		int index = rng.nextInt(PlaneType.values().length);
		PlaneType type = PlaneType.values()[index];

		int passengers = type.getMaxPassengers() / 2 + rng.nextInt(type.getMaxPassengers() / 2);
		double fuel = type.getMaxFuel() / 2 + rng.nextDouble() * type.getMaxFuel() / 2;
		double speed = type.getMaxVelocity();
		double altitude = (type.getMaxAltitude() / 2 + rng.nextDouble() * type.getMaxAltitude() / 2) / Config.SCALE;

		int score = type.getScore();

		return new Plane(type, passengers, fuel, speed, position, Resources.PLANE, score, altitude);
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
	 * @return
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
		return plane.getPosition().distanceFrom(this.getPosition()) + (plane.getAltitude() - this.getAltitude()) * (plane.getAltitude() - this.getAltitude());
	}

	@Override
	protected boolean isMouseOver() {
		int x = Mouse.getX();
		int y = DrawThread.height - Mouse.getY();
		return new Vector2d(x, y).distanceFrom(this.getPosition()) < 625;
	}

}
