package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class Enemy extends PhysicItem {

	public enum ETYPE {
		BONUS,
		METEORITE,
		SPUTNIK
	}
	
	public ETYPE mType;
	public float mYAngleSpeed;

	public Enemy()
	{
		
	}
	
	public Enemy(Enemy s) {
		mType = s.mType;
		mYAngleSpeed = s.mYAngleSpeed;
		mPosition=new Vector3();
		mPosition.set(s.mPosition);
		mSpeed=new Vector3();
		mSpeed.set(s.mSpeed);
		mDestination=new Vector3();
		mDestination.set(s.mDestination);
		mLastStep=new Vector3();
		mLastStep.set(s.mLastStep);
		mDirection=new Vector3();
		mDirection.set(s.mDirection);
		mScreenPosition=new Vector3();
		mScreenPosition.set(s.mScreenPosition);
	}


	@Override
	public void Update(float deltaTime) {
		mHeading+=mYAngleSpeed;
		super.Update(deltaTime);
	}
	
}
