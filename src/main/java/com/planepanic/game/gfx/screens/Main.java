package com.planepanic.game.gfx.screens;

import com.planepanic.game.Config;
import com.planepanic.game.gfx.Callback;
import com.planepanic.game.gfx.CallbackP;
import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.ui.Align;
import com.planepanic.game.gfx.ui.Button;
import com.planepanic.game.gfx.ui.TextBox;
import com.planepanic.game.model.Vector2d;

/**
 * Main screen Where a user would change options before starting to play
 * 
 * @author Thomas
 * 
 */
public class Main extends Screen {

	private Button startGame;
	private TextBox title;
	private TextBox valueBox, exclusionNotice;
	private int value, exclusionZone = 610;

	public Main() {
		super();

		DrawThread draw = DrawThread.getInstance();
		this.startGame = new Button("Start Game").setCallback(new Callback<Boolean>() {
			@Override
			public Boolean call() {
				Main.this.exclusionZone = Integer.parseInt(Main.this.valueBox.getText()) / Config.SCALE;
				if (Main.this.exclusionZone < 31) {
					Main.this.exclusionZone = 31;
				}
				Game.setExclusionZone(Main.this.exclusionZone);
				DrawThread.getInstance().changeScreen(new Game());
				return true;
			}
		});

		this.exclusionNotice = (TextBox) new TextBox("Set exclusion zone size (meters)").setAlign(Align.MIDDLE).setColor(0xffffff).setPriority(0.9f);

		this.valueBox = (TextBox) new TextBox("610", true, true, true).setAlign(Align.MIDDLE).setColor(0xffffff).setPriority(0.9f).setScrollCallback(new CallbackP<Boolean, Integer>() {
			@Override
			public Boolean call(Integer param) {
				Main.this.value += param / 6;
				Main.this.valueBox.setText("" + Main.this.value);
				return true;
			}
		});

		draw.draw(this.exclusionNotice);
		draw.draw(this.valueBox);
		draw.draw(this.startGame);
		this.title = new TextBox("Plane Panic");
		draw.draw(this.title);

	}

	@Override
	public void resize() {
		this.title.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 - 150));
		this.startGame.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 + 150));
		this.valueBox.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2 + 50));
		this.exclusionNotice.setPosition(new Vector2d(DrawThread.width / 2, DrawThread.height / 2));
	}

}
