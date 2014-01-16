package com.planepanic.game.gfx.screens;

import com.planepanic.game.gfx.Callback;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.ui.Button;
import com.planepanic.game.gfx.ui.TextBox;
import com.planepanic.game.model.Vector2d;

/**
 * Game Over screen
 * Shows when you lose
 * 
 * @author Ben
 *
 */
public class GameOver extends Screen {

	private Button returnToMain;
	private TextBox gameOver;

	public GameOver() {
		super();

		DrawThread draw = DrawThread.getInstance();

		this.returnToMain = new Button("Back to Title").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				DrawThread.getInstance().changeScreen(new Main());
				return true;
			}
		});
		draw.draw(this.returnToMain);
		this.gameOver = new TextBox("PLANE CRASH!!");
		draw.draw(this.gameOver);
	}

	@Override
	public void resize() {
		this.gameOver.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 - 150));
		this.returnToMain.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 + 95));
	}

}
