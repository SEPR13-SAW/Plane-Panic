package com.planepanic.game.gfx.ui;

import java.util.Iterator;

import com.planepanic.game.gfx.DrawThread;
import com.planepanic.game.gfx.DrawUtil;
import com.planepanic.game.gfx.Drawable;
import com.planepanic.game.model.Plane;
import com.planepanic.game.model.Vector2d;
import com.planepanic.game.model.orders.Order;

public class OrderList extends Drawable {

	private int offset = 50;

	@Override
	public void draw2d() {
		this.setHitboxSize(new Vector2d(DrawThread.width - 896, DrawThread.height - 330));
		Plane plane;
		int y = (int) (this.getPosition().getY() - this.getHitboxSize().getY() / 2) + this.offset;
		if ((plane = Plane.getSelected()) != null) {
			Iterator<Order> orders = plane.getOrders().iterator();
			Order order;
			while (orders.hasNext()) {
				order = orders.next();
				this.drawOrder(y, order);
				y += 100;
			}
		}

	}

	private void drawOrder(int y, Order order) {
		DrawUtil.setColor(0xFFFF99);
		DrawUtil.drawSquare((float) (this.getPosition().getX()), y, (float) this.getHitboxSize().getX(), 99, true, this.getPriority());
		DrawUtil.setColor(0x000000);
		DrawUtil.drawSquare((float) (this.getPosition().getX()), y + 50, (float) this.getHitboxSize().getX() , 1, true, this.getPriority());
		DrawUtil.drawString((float) (this.getPosition().getX() - this.getHitboxSize().getX() / 2) + 10, y - 40, order.getHumanReadable(), 0x000000, 24, this.getPriority() + 0.01f);
	}

	@Override
	public void draw3d() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean onScroll(int scroll) {
		this.offset = Math.min(Math.max(Math.min((int) (this.getHitboxSize().getY() - Plane.getSelected().getOrders().size() * 100 + 50), 50), this.offset + scroll / 12), 50);
		return true;
	}

	@Override
	protected boolean onClick() {
		Plane.getSelected().getOrders().clear();
		return true;
	}

	@Override
	protected boolean keyPress(int key) {
		// TODO Auto-generated method stub
		return false;
	}

}
