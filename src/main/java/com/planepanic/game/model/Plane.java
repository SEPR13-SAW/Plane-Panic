package com.planepanic.game.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
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
	@Getter private final PlaneType type;
	@Getter private final int passengers;
	@Getter @Setter private int score;
	@Getter @Setter private double fuel;
	@Getter @Setter private double speed; // Simple linear speed, to avoid having to calculate length of the vector every tick
	@Getter @Setter private double altitude;
	@Getter @Setter private Vector2d velocity;
	@Getter @Setter private int scoreTickDelay = Config.FRAMERATE;
	@Getter @Setter private int gracePeriod = 30;
	@Getter private ExclusionZone ez;

	@Getter private final Queue<Order> orders = new ArrayDeque<>(64);

	public Plane(PlaneType type, int passengers, double fuel, double speed, Vector2d position, Resources sprite, int score, double altitude) {
		super(sprite, position);
		this.type = type;
		this.passengers = passengers;
		this.fuel = fuel;
		this.speed = speed;
		this.velocity = this.convertSpeedToVelocity(Math.PI / 2);
		this.score = score;
		this.ez = new ExclusionZone(position);
	}

	@Override
	public void draw2d() {
		super.draw2d();
		this.tick();
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClick() {
		return false;
	}

	public Order getCurrentOrder() {
		return this.orders.peek();
	}

	public void tick() {
		this.consumeFuel();
		this.decayScore();
		
		Order order = this.getCurrentOrder();
		if (order != null) {
			order.tick(this);
			if (order.isComplete(this)) {
				this.orders.poll();
			}
		}
		this.getPosition().applyChange(this.getVelocity());
		this.ez.setPosition(this.getPosition());
		this.ez.setViolated(false);
		this.setAngle((float) Math.toDegrees(this.getVelocity().getAngle()) + 90);
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
		double altitude = type.getMaxAltitude() / 2 + rng.nextDouble() * type.getMaxAltitude() / 2;
	
		int score = type.getScore();

		return new Plane(type, passengers, fuel, speed, position, Resources.PLANE, score, altitude);
	}
	
	// Calculates the fuel consumption in l/s scaling by how much of a max speed plane is flying
	public void consumeFuel() {
		this.setFuel(this.getFuel() - this.type.getFuelConsumption() / Config.FRAMERATE * (this.getSpeed() / this.type.getMaxVelocity()));
	};
	
	// Converts speed in m/s and starting angle, to a Cartesian vector
	// for simpler use in other calculations
	public Vector2d convertSpeedToVelocity(double angle){
		double x,y;
		x = this.getSpeed() * Math.cos(angle) / Config.FRAMERATE / 10;
		y = this.getSpeed() * Math.sin(angle) / Config.FRAMERATE / 10;
		if (x < 0.01)
			x = 0;
		if (y < 0.01)
			y = 0;
		return new Vector2d(x, y);
		
	
	}
	
	public void decayScore(){
		this.setScoreTickDelay(this.getScoreTickDelay()-1);
		
		if(this.getScoreTickDelay() == 0 && this.getGracePeriod() > 0){
			this.setGracePeriod(this.getGracePeriod()-1);
			this.setScoreTickDelay(Config.FRAMERATE);
		}
		
		if(this.getScore() > 0 && this.getScoreTickDelay() == 0 && this.getGracePeriod() == 0){
		this.setScore(this.getScore() - (10));
		this.setScoreTickDelay(Config.FRAMERATE);
		// System.out.println("Score: " + score); /* Used to Track Scores for Testing */
		}
	}	
}
