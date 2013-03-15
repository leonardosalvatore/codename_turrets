package com.beegroove.turrets;

public class Enemy extends PhysicItem {

	public enum ETYPE {
		BONUS,
		METEORITE,
		SPUTNIK
	}
	
	public ETYPE mType;
	public float mYAngleSpeed;

	
	@Override
	public void Update(float deltaTime) {
		mHeading+=mYAngleSpeed;
		super.Update(deltaTime);
	}
	
}
