package com.planepanic.game;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.ui.Button;
import com.planepanic.game.gfx.ui.TextBox;
import com.planepanic.game.model.EntryPoint;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.orders.AbsoluteHeading;

/**
 * Example main
 * 
 * @author Thomas Cheyney
 */
public class Main {

	public static void main(String[] args) throws Exception {
		new Main();
	}

	DrawThread draw;

	public Main() {
		this.draw = new DrawThread();
		this.draw.start();
		this.showMainScreen();
	}

	public void showMainScreen() {
		Button startGame = (Button) new Button("Start Game").setCallback(new Runnable() {
			@Override
			public void run() {
				Main.this.draw.clearScreen();

				EntryPoint entry = new EntryPoint(new Vector2d(50, 50));
				Main.this.draw.draw(entry);
				Plane plane = entry.addPlane();
				plane.getOrders().add(new AbsoluteHeading(0));
				plane.getOrders().add(new AbsoluteHeading(Math.PI / 2));
				Main.this.draw.draw(plane);
			}
		}).setPosition(new Vector2d(490, 300));
		this.draw.draw(startGame);
		TextBox title = (TextBox) new TextBox("Plane Panic").setPosition(new Vector2d(536, 100));
		this.draw.draw(title);
	}

}
