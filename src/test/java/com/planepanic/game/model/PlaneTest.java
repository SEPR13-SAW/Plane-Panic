package com.planepanic.game.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.planepanic.game.gfx.Resources;
import com.planepanic.game.gfx.screens.Game;

public final class PlaneTest {

	@Test
	public void checkOutOfBoundsTest() {
		Plane plane = new Plane(new Game(), PlaneType.BOEING_747_400, 0, 1000, 0, new Vector2d(-10, -10), Resources.PLANE, 0, 0);
		plane.checkOutOfBounds();
		assertEquals(plane.getOrders().size(), 1);
	}

}
