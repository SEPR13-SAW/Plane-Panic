package com.planepanic.game.gfx.ui;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.Vector2d;

/**
 * Maintains the sidebar
 * 
 * @author Jonathan, Mantas, Steven, Thomas
 * 
 */
public class OrderPanel extends Drawable {

	private Controls controls;
	private OrderList orderList;
	private PlaneInfo planeinfo;

	public OrderPanel(Vector2d position) {

		super(position, new Vector2d(384, 720));

		this.controls = new Controls();
		this.controls.setOrderState(0);
		DrawThread.getInstance().draw(this.orderList = new OrderList());
		this.orderList.setPosition(position).setPriority(0.3f);
		this.planeinfo = new PlaneInfo(this);
		this.setPriority(0.4f);
	}

	@Override
	public void draw2d() {
		DrawUtil.setColor(0x89714F);
		DrawUtil.drawSquare((float) this.getPosition().getX(), 75, (float) this.getHitboxSize().getX(), 150, true, this.getPriority());
		DrawUtil.drawSquare((float) this.getPosition().getX(), DrawThread.height - 64, (float) this.getHitboxSize().getX(), 128, true, this.getPriority());
		DrawUtil.setColor(0x000000);
		DrawUtil.drawSquare((float) this.getPosition().getX(), 150, 384, 1, true, this.getPriority() + 0.01f);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	public void onMove() {
		this.orderList.setPosition(this.getPosition().sub(new Vector2d(0, 15))).setHitboxSize(new Vector2d(500, DrawThread.height - 330));
	}

	@Override
	public boolean onClick() {
		// TODO Auto-generated method stub
		return false;
	}

	public void tick() {
		this.planeinfo.tick();
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}

}
