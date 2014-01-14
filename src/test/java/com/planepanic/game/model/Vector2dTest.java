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
	
	@Test
	public void arithmaticTest() {
		Vector2d vector = new Vector2d(1, -2);
		
		assertEquals(new Vector2d(7, 7), vector.add(new Vector2d(6, 9)));
		
		assertEquals(new Vector2d(-5, -11), vector.sub(new Vector2d(6, 9)));
		
		assertEquals(new Vector2d(4, -8), vector.mul(4));
	}
	
	@Test
	public void distanceTest() {
		Vector2d vector = new Vector2d(1, -2);
		Vector2d vector2 = new Vector2d(4, 2);
		
		assertEquals(25, vector.distanceFrom(vector2), 0.1);
	}

}
