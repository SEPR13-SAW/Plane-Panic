package com.planepanic.game.gfx.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.RenderPriority;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.gfx.ui.Button;
import com.planepanic.game.gfx.ui.ExclusionZone;
import com.planepanic.game.gfx.ui.OrderButtons;
import com.planepanic.game.gfx.ui.Radar;
import com.planepanic.game.gfx.ui.TextBox;
import com.planepanic.game.model.Airport;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.OrderPanel;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.Waypoint;
import com.planepanic.game.model.orders.AbsoluteHeading;
import com.planepanic.game.model.orders.ChangeSpeed;
import com.planepanic.game.model.orders.RelativeHeading;

public class Game extends Screen {

	Radar radar;
	@Getter @Setter int ticks = 0, maxSpawnInterval = 5 * Config.FRAMERATE, minSpawnInterval = 4 * Config.FRAMERATE, maxTicks = this.maxSpawnInterval;
	private List<EntryPoint> entryPointList = new ArrayList<>();
	private List<Plane> planeList = new ArrayList<>();
	@Getter private List<ExclusionZone> exclusionZoneList = new ArrayList<>();
	@Getter @Setter ExclusionZone ez;
	@Getter private final static int exclusionZone = 3050 / 21; //Exclusion in meters divided by how much meters one pixel represents. End version should have two depending on altitude
	@Getter @Setter public int orderState = 0;	
	@Setter TextBox fuelDisplay;
	
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
		plane.getOrders().add(new ChangeSpeed(plane.getSpeed(), 100));
		draw.draw(plane, RenderPriority.Low);
		draw.draw(plane.getEz(), RenderPriority.High);
		plane = entry.addPlane();
		this.planeList.add(plane);
		draw.draw(plane, RenderPriority.Low);
		draw.draw(plane.getEz(), RenderPriority.High);
		this.radar = new Radar(this);
		draw.draw(this.radar, RenderPriority.Highest);
		Airport airport = new Airport(new Vector2d(400, Config.WINDOW_HEIGHT / 2));
		draw.draw(airport, RenderPriority.Low);
		
		
		this.orderPanel();
		
	}

	public void spawnPlane(Random rng) {
		if (this.getTicks() == this.getMaxTicks()) {
			int index = rng.nextInt(this.entryPointList.size());
			Plane plane = this.entryPointList.get(index).addPlane();
			DrawThread draw = DrawThread.getInstance();
			draw.draw(plane, RenderPriority.Low);
			draw.draw(plane.getEz(), RenderPriority.High);
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
		for(int i = 0; i < this.planeList.size()-1; i++){
			for(int o = i+1; o < this.planeList.size(); o++){
				if(distanceBetweenPoints(this.planeList.get(i).getPosition(), this.planeList.get(o).getPosition()) < Game.exclusionZone*Game.exclusionZone){
					this.planeList.get(i).getEz().setViolated(true);
					this.planeList.get(o).getEz().setViolated(true);
				};
			};
		};
	};
	
	// calculates the squared distance between two given points,
	// at the moment works in 2d, but can easily be extended to work in 3d
	public double distanceBetweenPoints(Vector2d location, Vector2d location2){
		return (((location.getX()-location2.getX())*(location.getX()-location2.getX()))+((location.getY()-location2.getY())*(location.getY()-location2.getY()))); 
	}

	public void orderPanel() {
		DrawThread draw = DrawThread.getInstance();

		
		OrderButtons direction = (OrderButtons) new OrderButtons(1000, 525, Resources.DIRECTION).setCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Change Direction!");
				orderState = 1;
			}
		});
		
		OrderButtons altitude = (OrderButtons) new OrderButtons(1150, 525, Resources.ALTITUDE).setCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Change Altitude!");
				orderState = 2;
			}
		});
		
		OrderButtons heading = (OrderButtons) new OrderButtons(1000, 600, Resources.HEADING).setCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Change Heading!");
				orderState = 3;
			}
		});
		
		OrderButtons speed = (OrderButtons) new OrderButtons(1150, 600, Resources.SPEED).setCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Change Speed!");
				orderState = 3;
			}
		});
		
		OrderButtons land = (OrderButtons) new OrderButtons(1150, 675, Resources.LAND).setCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Land!");
				orderState = 0;
			}
		});
		
		OrderButtons takeoff = (OrderButtons) new OrderButtons(1000, 675, Resources.TAKEOFF).setCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Take off!");
				orderState = 0;
			}
		});
		
		Button left = (Button) new Button("<-").setCallback(new Runnable(){
			@Override
			public void run(){
				System.out.println("Turns left by number inputted");
				orderState = 0;
			}
		}).setHitboxSize(new Vector2d(50,50)).setPosition(new Vector2d(1100, 600));
		
		Button right = (Button) new Button("->").setCallback(new Runnable(){
			@Override
			public void run(){
				System.out.println("Turns right by number inputted");
				orderState = 0;
			}
		}).setHitboxSize(new Vector2d(50,50)).setPosition((new Vector2d(1175, 600)));
		
		Button set = (Button) new Button("Set").setCallback(new Runnable(){
			@Override
			public void run(){
				System.out.println("Confirms new heading!");
				orderState = 0;
			}
		}).setHitboxSize(new Vector2d(50,50)).setPosition((new Vector2d(1175, 600)));
		
		Button up = (Button) new Button("Up").setCallback(new Runnable(){
			@Override
			public void run(){
				System.out.println("Increases altitude by inputted amount");
				orderState = 0;
			}
		}).setHitboxSize(new Vector2d(50,50)).setPosition(new Vector2d(1130, 560));
		
		Button down = (Button) new Button("Down").setCallback(new Runnable(){
			@Override
			public void run(){
				System.out.println("Decreases altitude by inputted amount");
				orderState = 0;
			}
		}).setHitboxSize(new Vector2d(50,50)).setPosition(new Vector2d(1130, 630));
		
		Button back = (Button) new Button("Back").setCallback(new Runnable(){
			@Override
			public void run(){
				System.out.println("Cancels selected order");
				orderState = 0;
			}
		}).setHitboxSize(new Vector2d(50,50)).setPosition(new Vector2d(900, 500));;
		
		
		//Displaying Fuel
		Plane temp= this.planeList.get(1); //Needs to get the plane that has been clicked on
		String currentFuel = String.valueOf(temp.getFuel());
		this.fuelDisplay = (TextBox) new TextBox(currentFuel).setColor(0x000000).setPosition(new Vector2d(850,25));
		draw.draw(fuelDisplay, RenderPriority.Normal);

			
		if (orderState == 0){
			draw.draw(direction, RenderPriority.Normal);
			draw.draw(altitude, RenderPriority.Normal);
			draw.draw(heading, RenderPriority.Normal);
			draw.draw(land, RenderPriority.Normal);
			draw.draw(takeoff, RenderPriority.Normal);
			draw.draw(speed, RenderPriority.Normal);
			}
		else if (orderState == 1){
			//Changing direction
			draw.draw(left, RenderPriority.Normal);
			draw.draw(right, RenderPriority.Normal);
			draw.draw(back, RenderPriority.Normal);
		}
		else if (orderState == 2){
			//Changing heading
			draw.draw(set, RenderPriority.Normal);
			draw.draw(back, RenderPriority.Normal);
		}
		else if (orderState == 3){
			//Changing altitude
			draw.draw(up, RenderPriority.Normal);
			draw.draw(down, RenderPriority.Normal);
			draw.draw(back, RenderPriority.Normal);
		}

		OrderPanel orderpanel = new OrderPanel(new Vector2d(1100, 360));
		draw.draw(orderpanel, RenderPriority.Highest);
		System.out.println(orderState);
		
	}
	
}
