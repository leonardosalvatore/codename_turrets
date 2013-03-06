package com.beegroove.turrets;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class PhysicItem {

	Hashtable<String, Float> mTag;
	public Vector3 mPosition = Vector3.Zero;
	protected Vector3 mSpeed = Vector3.Zero;
	public Vector3 mLastStep = Vector3.Zero;
	public Vector3 mDirection = Vector3.Zero;
	public float mYAangle;
	public float mSize;
	public int mEnergy;
	
	public PhysicItem() {
	}

	public void Create() {
	}

	public void Update(float deltaTime) {
		// s=v*t
		mLastStep = mSpeed.cpy().mul(deltaTime);
		mPosition.add(mLastStep);
	}
	
	public void SetDestinationRelative(Vector3 relative)
	{
		//TODO uhmmm....
		mSpeed = relative;
	}
	
	public void SetDestination(Vector3 destination) {
		destination.x += Par.THUMB_CORRECTION;
		mSpeed = destination.sub(mPosition).mul(Par.SHIP_MAX_SPEED);
	}
	
	public void SetTarget(Vector3 target) {
		mYAangle= (float) - MathUtils.radiansToDegrees * MathUtils.atan2(target.z - mPosition.z ,target.x - mPosition.x);
		Gdx.app.log("PhysicItem", String.format("Target:%s Position:%s Angle:%f",target,mPosition,mYAangle));
	}
	
	public void Stop()
	{
		mSpeed.mul(0.90f);
	}

	@Override
	public String toString() {
		String ret = String
				.format("Speed:%s\nLastStep:%s\nPosition: %s\nY_Angle:%f",
						mSpeed,mLastStep,mPosition,mYAangle);
		return ret;

	}

}
