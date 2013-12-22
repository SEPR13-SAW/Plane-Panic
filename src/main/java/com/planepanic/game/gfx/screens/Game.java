package com.planepanic.game.gfx.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.RenderPriority;
import com.planepanic.game.gfx.ui.ExclusionZone;
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
	@Getter @Setter int ticks = 0, maxSpawnInterval = 5 * Config.FRAMERATE, minSpawnInterval = 4 * Config.FRAMERATE, maxTicks = this.maxSpawnInterval;
	private List<EntryPoint> entryPointList = new ArrayList<>();
	private List<Plane> planeList = new ArrayList<>();
	@Getter private List<ExclusionZone> exclusionZoneList = new ArrayList<>();
	@Getter @Setter ExclusionZone ez;
	@Getter private final static int exclusionZone = 3050 / 21; //Exclusion in meters divided by how much meters one pixel represents. End version should have two depending on altitude

	public Game() {
		super();

		DrawThread draw = DrawThread.getInstance();

		EntryPoint entry = new EntryPoint(new Vector2d(50, 50));
		this.entryPointList.add(entry);
		draw.draw(entry, RenderPriority.High);
		EntryPoint entry2 = new EntryPoint(new Vector2d(50, 200));
		this.entryPointList.add(entry);
		draw.draw(entry2, RenderPriority.High);
		createEntryPoint(new Vector2d(50,500));
		createEntryPoint(new Vector2d(500,500));
		createEntryPoint(new Vector2d(500,50));
		
		for (int i = 0; i < 6; i++) {
			Waypoint wp = new Waypoint(new Vector2d(200 + 75 * i, 400), "" + (char) (65 + i));
			draw.draw(wp, RenderPriority.High);
		}

		Plane plane = entry2.addPlane();
		this.planeList.add(plane);
		plane.getOrders().add(new AbsoluteHeading(0));
		plane.getOrders().add(new AbsoluteHeading(Math.PI / 2));
		plane.getOrders().add(new RelativeHeading(plane.getAngle(), Math.PI / 2));
		draw.draw(plane, RenderPriority.Low);
		plane = entry.addPlane();
		this.planeList.add(plane);
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
	
	public void createEntryPoint(Vector2d position){
		EntryPoint entry = new EntryPoint(position);
		this.entryPointList.add(entry);
		DrawThread draw = DrawThread.getInstance();
		draw.draw(entry, RenderPriority.High);
	}
	
	
	@Override
	public void resize() {
		this.radar.setPosition(new Vector2d((DrawThread.width - 500) / 2, DrawThread.height / 2));
		this.radar.onResize();
	}
	
	// loops through all the planes and checks whether
	// the distance between any two is bigger than exclusion zone
	public void exclusionZoneDetection(){
		for(int i = 0; i < this.planeList.size()-1; i++)
			for(int o = i+1; o < this.planeList.size(); o++){
				if(distanceBetweenPoints(this.planeList.get(i).getPosition(), this.planeList.get(o).getPosition()) < Game.exclusionZone*Game.exclusionZone){
					if(!checkIfExists(this.planeList.get(i), this.planeList.get(o)))
						createNewExclusionZones(this.planeList.get(i), this.planeList.get(o));
				};
			};
		this.removeExclusionZones();	
	};
	
	//checks if an exclusion zone with between these two planes already exists.
	public boolean checkIfExists(Plane plane, Plane plane2){
		for(ExclusionZone ez : this.exclusionZoneList)
			if(plane == ez.getPlane() && plane2 == ez.getPlane2())
				return true;
		return false;
	}
	
	// creates new visual exclusion zones
	public void createNewExclusionZones(Plane plane, Plane plane2){
		DrawThread draw = DrawThread.getInstance();
		ez = new ExclusionZone(this, plane.getPosition(), plane, plane2);
		draw.draw(ez, RenderPriority.High);
		this.exclusionZoneList.add(ez);
		ez = new ExclusionZone(this, plane2.getPosition(), plane2, plane);
		draw.draw(ez, RenderPriority.High);
		this.exclusionZoneList.add(ez);		
	}
	
	// removes visual exclusion zones after they are no longer violated
	public void removeExclusionZones(){
		for(int i = 0; i < this.exclusionZoneList.size(); i++){
			if(Game.getExclusionZone() * Game.getExclusionZone() < this.distanceBetweenPoints(this.exclusionZoneList.get(i).getPlane().getPosition(), this.exclusionZoneList.get(i).getPlane2().getPosition())){
				DrawThread draw = DrawThread.getInstance();
				draw.removeObject(this.exclusionZoneList.get(i));
				this.exclusionZoneList.remove(i);
			}
		}
	}
	
	// calculates the squared distance between two given points,
	// at the moment works in 2d, but can easily be extended to work in 3d
	public double distanceBetweenPoints(Vector2d location, Vector2d location2){
		return (((location.getX()-location2.getX())*(location.getX()-location2.getX()))+((location.getY()-location2.getY())*(location.getY()-location2.getY()))); 
	}

}
