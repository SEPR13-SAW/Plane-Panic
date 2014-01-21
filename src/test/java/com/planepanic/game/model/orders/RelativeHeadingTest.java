package com.planepanic.game.model.orders;

import static org.junit.Assert.*;

import org.junit.Test;

import com.planepanic.game.gfx.Image;
import com.planepanic.game.model.Plane;

public final class RelativeHeadingTest {

	@Test
	public void testOrder() {
		double angle = -Math.PI/3;
		Image.LOAD_TEXTURES = false;

		Plane plane = new Plane();
		RelativeHeading order = new RelativeHeading(plane, angle);

		while (!order.isComplete()) {
			order.tick();
		}

		assertTrue(plane.getVelocity().angleCloseEnough(angle));
	}

}
