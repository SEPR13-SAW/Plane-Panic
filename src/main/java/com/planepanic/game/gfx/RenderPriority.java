package com.planepanic.game.gfx;

/**
 * @author Thomas Cheyney
 * @version 1.0
 */
public enum RenderPriority {

	/**
	 * Will render before all other objects
	 */
	Highest(0),
	/**
	 * Will render before most other objects
	 */
	High(1),
	/**
	 * Will render in line with most other objects
	 */
	Normal(2),
	/**
	 * Will render after most other objects
	 */
	Low(3),
	/**
	 * Will render after all other objects
	 */
	Lowest(4);

	private final int id;

	RenderPriority(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static RenderPriority getRenderPriorityFromId(int id) {
		for (RenderPriority rp : RenderPriority.values()) {
			if (rp.getId() == id) {
				return rp;
			}
		}
		return null;
	}
}
