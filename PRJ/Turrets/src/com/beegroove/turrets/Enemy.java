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
	
	public ETYPE mType;
	public float mYAngleSpeed;

	
	@Override
	public void Update(float deltaTime) {
		mYAangle+=mYAngleSpeed;
		super.Update(deltaTime);
	}
	
}
