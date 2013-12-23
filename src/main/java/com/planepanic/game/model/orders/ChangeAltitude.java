package com.planepanic.game.model.orders;

import com.planepanic.game.Config;
import com.planepanic.game.model.Plane;

import lombok.Getter;

public class ChangeAltitude extends Order{

	@Getter double delta, speed, startAltitude, changeRate;
	
	public ChangeAltitude(double startAltitude, double speed, double delta){
		this.startAltitude = startAltitude;
		this.speed = speed;
		this.delta = delta;
	}
	
	public boolean isComplete(Plane plane) {
			return plane.getAltitude() == (this.getStartAltitude() + delta);
			}
	
	public void tick(Plane plane){
	//	plane.setVelocity(plane.convertSpeedToVelocity(3)); //This may be changed once I know what the angle is respective too and if it is degrees or radians.
		if(this.getDelta() < 0){
		plane.setAltitude(plane.getAltitude() - (plane.getSpeed() / Config.FRAMERATE));
		}else{
		plane.setAltitude(plane.getAltitude() + (plane.getSpeed() / Config.FRAMERATE));	
		}
	}
}