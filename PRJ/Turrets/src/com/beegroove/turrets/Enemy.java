package com.beegroove.turrets;

public class Enemy extends PhysicItem {

	public enum ETYPE {
		BONUS,
		METEORITE,
		SCOUT,
		FIGHTER,
		TRANSPORT,
		INTERCEPTOR,
		BOMBER,
		DESTROYER
	}
	
	public ETYPE type;
	public float y_angle_speed;

	
	@Override
	public void Update(float deltaTime) {
		y_angle+=y_angle_speed;
		super.Update(deltaTime);
	}
	
}
