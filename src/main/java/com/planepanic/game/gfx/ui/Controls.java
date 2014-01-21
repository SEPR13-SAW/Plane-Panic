package com.planepanic.game.gfx.ui;

import java.util.ArrayList;
import java.util.List;

import com.planepanic.game.gfx.Callback;
import com.planepanic.game.gfx.CallbackP;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.gfx.Resources;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.orders.AbsoluteHeading;
import com.planepanic.game.model.orders.ChangeAltitude;
import com.planepanic.game.model.orders.ChangeSpeed;
import com.planepanic.game.model.orders.Order;
import com.planepanic.game.model.orders.RelativeHeading;

/**
 * Maintains the state of the controls displayed at the bottom of the Order
 * Panel
 * 
 * @author Jonathan, Mantas, Steven, Thomas
 * 
 */
public class Controls {

	private int orderState = 0;

	private List<Drawable> controls = new ArrayList<Drawable>();
	private ImageButton direction;
	private ImageButton altitude;
	private ImageButton heading;
	private ImageButton speed;
	private ImageButton land;
	private ImageButton takeoff;
	private ImageButton left;
	private ImageButton right;
	private ImageButton set;
	private ImageButton up;
	private ImageButton down;
	private ImageButton upSpeed;
	private ImageButton downSpeed;
	private ImageButton back;
	private TextBox valueBox;
	private int value;

	public Controls() {
		this.controls.add(this.valueBox = (TextBox) new TextBox("0", true, true).setAlign(Align.RIGHT).setColor(0x000000).setPriority(0.9f).setPosition(new Vector2d(1050, 650)).setScrollCallback(new CallbackP<Boolean, Integer>() {
			@Override
			public Boolean call(Integer param) {
				Controls.this.value += param / 6;
				Controls.this.valueBox.setText("" + Controls.this.value);
				return true;
			}
		}));

		this.controls.add(this.direction = new ImageButton(1024, 656, Resources.DIRECTION, new Vector2d(128, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Direction!");
				Controls.this.setOrderState(1);
				return true;
			}
		}));

		this.controls.add(this.altitude = new ImageButton(1152, 656, Resources.ALTITUDE, new Vector2d(128, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Altitude!");
				Controls.this.setOrderState(5);
				return true;
			}
		}));

		this.controls.add(this.heading = new ImageButton(1280, 656, Resources.HEADING, new Vector2d(128, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Heading!");
				Controls.this.setOrderState(4);
				return true;
			}
		}));

		this.controls.add(this.speed = new ImageButton(1024, 720, Resources.SPEED, new Vector2d(128, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Speed!");
				Controls.this.setOrderState(3);
				return true;
			}
		}));

		this.controls.add(this.land = new ImageButton(1152, 720, Resources.LAND, new Vector2d(128, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Land!");
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.takeoff = new ImageButton(1280, 720, Resources.TAKEOFF, new Vector2d(128, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Take off!");
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.left = new ImageButton(1180, 690, Resources.LEFT, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Turns left by number inputted");
				Controls.this.checkAdd(new RelativeHeading(Plane.getSelected(), Controls.this.readValueBox() * Math.PI / 180));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.right = new ImageButton(1244, 690, Resources.RIGHT, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Turns right by number inputted");
				Controls.this.checkAdd(new RelativeHeading(Plane.getSelected(), -Controls.this.readValueBox() * Math.PI / 180));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.set = new ImageButton(1312, 720, Resources.SET, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Set!");
				Controls.this.checkAdd(new AbsoluteHeading(Plane.getSelected(), (Controls.this.readValueBox() * Math.PI / 180) + Math.PI/2));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.up = new ImageButton(1200, 656, Resources.UP, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Increases altitude by inputted amount");
				Controls.this.checkAdd(new ChangeAltitude(Plane.getSelected(), Controls.this.readValueBox()));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.down = new ImageButton(1200, 720, Resources.DOWN, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Decreases altitude by inputted amount");
				Controls.this.checkAdd(new ChangeAltitude(Plane.getSelected(), -Controls.this.readValueBox()));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.upSpeed = new ImageButton(1200, 656, Resources.UP, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Increases speed by inputted amount");
				Controls.this.checkAdd(new ChangeSpeed(Plane.getSelected(), Controls.this.readValueBox()));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.downSpeed = new ImageButton(1200, 720, Resources.DOWN, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Decreases speed by inputted amount");
				Controls.this.checkAdd(new ChangeSpeed(Plane.getSelected(), -Controls.this.readValueBox()));
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.back = new ImageButton(1312, 656, Resources.BACK, new Vector2d(64, 64)).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Back!");
				Controls.this.setOrderState(0);
				return true;
			}
		}));

	}

	public void setOrderState(int state) {
		DrawThread draw = DrawThread.getInstance();
		for (Drawable button : this.controls) {
			draw.removeObject(button);
		}

		this.orderState = state;
		switch (this.orderState) {
			case 0:
				draw.draw(this.direction);
				draw.draw(this.altitude);
				draw.draw(this.heading);
				draw.draw(this.land);
				draw.draw(this.takeoff);
				draw.draw(this.speed);
				break;
			case 1:
				// Changing direction
				draw.draw(this.valueBox);
				draw.draw(this.left);
				draw.draw(this.right);
				draw.draw(this.back);
				break;
			case 2:
				// Relative heading
				draw.draw(this.valueBox);
				draw.draw(this.back);
				break;
			case 4:
				// Absolute heading
				draw.draw(this.valueBox);
				draw.draw(this.set);
				draw.draw(this.back);
				break;
			case 3:
				// Changing speed
				draw.draw(this.valueBox);
				draw.draw(this.upSpeed);
				draw.draw(this.downSpeed);
				draw.draw(this.back);
				break;
			case 5:
				// Change Altitude
				draw.draw(this.valueBox);
				draw.draw(this.up);
				draw.draw(this.down);
				draw.draw(this.back);
		}

	}

	public int readValueBox() {
		try {
			return Integer.parseInt(Controls.this.valueBox.getText());
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public void checkAdd(Order order) {
		if (Plane.getSelected() != null) {
			Plane.getSelected().addOrder(order);
			this.resetTextBox();
		}
	}

	public void resetTextBox() {
		this.valueBox.setText("0");
	}

}
