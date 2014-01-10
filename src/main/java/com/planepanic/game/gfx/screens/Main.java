package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.Callback;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.ui.Button;
import com.planepanic.game.gfx.ui.TextBox;
import com.planepanic.game.model.Vector2d;

public class Main extends Screen {

	private Button startGame;
	private TextBox title;

	public Main() {
		super();

		DrawThread draw = DrawThread.getInstance();

		this.startGame = new Button("Start Game").setCallback(new Callback<Boolean>() {
			
			@Override
			public Boolean call() {
				DrawThread.getInstance().changeScreen(new Game());
				return true;
			}
		});
		draw.draw(this.startGame);
		this.title = new TextBox("Plane Panic");
		draw.draw(this.title);
	}

	@Override
	public void resize() {
		this.title.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 - 150));
		this.startGame.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 + 95));
	}

}
