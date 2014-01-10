package com.planepanic.game.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector2dTest {

	@Test
	public void getAngleTest() {
		Vector2d vector;
		
		vector = new Vector2d(-1,  0);
		assertEquals(-Math.PI, vector.getAngle(), 0.01);
		
		vector = new Vector2d( 0,  1);
		assertEquals(-Math.PI / 2, vector.getAngle(), 0.01);
		
		vector = new Vector2d( 1,  1);
		assertEquals(-Math.PI / 4, vector.getAngle(), 0.01);
		
		vector = new Vector2d(-1, -1);
		assertEquals(3 * Math.PI / 4, vector.getAngle(), 0.01);
	}
	
	@Test
	public void fromAngleTest() {
		assertEquals(-Math.PI, Vector2d.fromAngle(-Math.PI).getAngle(), 0.01);
		
		assertEquals(-Math.PI / 2, Vector2d.fromAngle(-Math.PI / 2).getAngle(), 0.01);
		
		assertEquals(3 * Math.PI / 4, Vector2d.fromAngle(3 * Math.PI / 4).getAngle(), 0.01);
		
		assertEquals(-Math.PI / 4, Vector2d.fromAngle(-Math.PI / 4).getAngle(), 0.01);
	}

}
