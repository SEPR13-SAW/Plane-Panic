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
import com.planepanic.game.model.orders.RelativeHeading;

public class Controls {

	private int orderState = 0;

	private List<Drawable> controls = new ArrayList<Drawable>();
	private OrderButton direction;
	private OrderButton altitude;
	private OrderButton heading;
	private OrderButton speed;
	private OrderButton land;
	private OrderButton takeoff;
	private Button left;
	private Button right;
	private Button set;
	private Button up;
	private Button down;
	private Button back;
	private TextBox valueBox;
	private int value;

	public Controls() {
		this.controls.add(this.valueBox = (TextBox) new TextBox("0").setAlign(Align.RIGHT).setColor(0x000000).setPriority(0.9f).setPosition(new Vector2d(900, 600)).setScrollCallback(new CallbackP<Boolean, Integer>() {
			@Override
			public Boolean call(Integer param) {
				Controls.this.value += param / 6;
				Controls.this.valueBox.setText("" + Controls.this.value);
				return true;
			}
		}));

		this.controls.add(this.direction = new OrderButton(945, 620, Resources.DIRECTION).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Direction!");
				Controls.this.setOrderState(1);
				return true;
			}
		}));

		this.controls.add(this.altitude = new OrderButton(1095, 620, Resources.ALTITUDE).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Altitude!");
				Controls.this.setOrderState(2);
				return true;
			}
		}));

		this.controls.add(this.heading = new OrderButton(1245, 620, Resources.HEADING).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Heading!");
				Controls.this.setOrderState(4);
				return true;
			}
		}));

		this.controls.add(this.speed = new OrderButton(945, 695, Resources.SPEED).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Change Speed!");
				Controls.this.setOrderState(3);
				return true;
			}
		}));

		this.controls.add(this.land = new OrderButton(1095, 695, Resources.LAND).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Land!");
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.takeoff = new OrderButton(1245, 695, Resources.TAKEOFF).setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Take off!");
				Controls.this.setOrderState(0);
				return true;
			}
		}));

		this.controls.add(this.left = (Button) new Button("<-").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Turns left by number inputted");
				Plane.getSelected().addOrder(new RelativeHeading(Plane.getSelected(), Math.toRadians(Controls.this.value)));
				Controls.this.setOrderState(0);
				return true;
			}
		}).setHitboxSize(new Vector2d(50, 50)).setPosition(new Vector2d(1100, 600)).setPriority(0.5f));

		this.controls.add(this.right = (Button) new Button("->").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Turns right by number inputted");
				Controls.this.setOrderState(0);
				return true;
			}
		}).setHitboxSize(new Vector2d(50, 50)).setPosition(new Vector2d(1175, 600)).setPriority(0.5f));

		this.controls.add(this.set = (Button) new Button("Set").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Confirms new heading!");
				Plane.getSelected().addOrder(new AbsoluteHeading(Plane.getSelected(), Math.toRadians(Controls.this.value - 90)));
				Controls.this.setOrderState(0);
				return true;
			}
		}).setHitboxSize(new Vector2d(100, 50)).setPosition(new Vector2d(1175, 600)).setPriority(0.5f));

		this.controls.add(this.up = (Button) new Button("Up").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Increases altitude by inputted amount");
				Controls.this.setOrderState(0);
				return true;
			}
		}).setHitboxSize(new Vector2d(100, 50)).setPosition(new Vector2d(1130, 560)).setPriority(0.5f));

		this.controls.add(this.down = (Button) new Button("Down").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Decreases altitude by inputted amount");
				Controls.this.setOrderState(0);
				return true;
			}
		}).setHitboxSize(new Vector2d(100, 50)).setPosition(new Vector2d(1130, 630)).setPriority(0.5f));

		this.controls.add(this.back = (Button) new Button("Back").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				System.out.println("Cancels selected order");
				Controls.this.setOrderState(0);
				return true;
			}
		}).setHitboxSize(new Vector2d(100, 50)).setPosition(new Vector2d(1130, 680)).setPriority(0.5f));
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
			case 4:
				// Absolute heading
				draw.draw(this.valueBox);
				draw.draw(this.set);
				draw.draw(this.back);
				break;
			case 3:
				// Changing altitude
				draw.draw(this.valueBox);
				draw.draw(this.up);
				draw.draw(this.down);
				draw.draw(this.back);
				break;
		}
	}

}
