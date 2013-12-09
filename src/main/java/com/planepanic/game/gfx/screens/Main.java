package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.ui.Button;
import com.planepanic.game.gfx.ui.TextBox;
import com.planepanic.game.model.Vector2d;

public class Main extends Screen {

	Button startGame;
	TextBox title;

	public Main() {
		super();

		DrawThread draw = DrawThread.getInstance();

		this.startGame = (Button) new Button("Start Game").setCallback(new Runnable() {
			@Override
			public void run() {
				DrawThread.getInstance().changeScreen(new Game());
			}
		}).setPosition(new Vector2d(490, 300));
		draw.draw(this.startGame);
		this.title = (TextBox) new TextBox("Plane Panic").setPosition(new Vector2d(536, 100));
		draw.draw(this.title);
	}

	@Override
	public void resize() {
		this.title.setPosition(new Vector2d(DrawThread.width / 2 - 104, DrawThread.height / 2 - 150));
		this.startGame.setPosition(new Vector2d(DrawThread.width / 2 - 150, DrawThread.height / 2 + 50));
	}

}
