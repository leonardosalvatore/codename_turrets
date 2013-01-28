package com.beegroove.turrets;

public class SpaceCraftFactory {
	static StarShip NewBasicSpaceShip()
	{
		//TODO complete... read from file
		StarShip ret = new StarShip();
		ret.turrets.add(new Turret());
		return ret;
		
	}
	
}
