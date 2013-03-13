package com.beegroove.turrets;

import java.util.Hashtable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PhysicItem {

	public Hashtable<String, Float> mTag;
	public Vector3 mPosition;
	public Vector3 mSpeed;
	public Vector3 mDestination;
	public Vector3 mLastStep;
	public Vector3 mDirection;
	public float mYAangle;
	public float mSize;
	public int mEnergy;
	public float mLastDestDist;
	
	public PhysicItem()
	{
		mPosition = new Vector3();
		mPosition.set(Vector3.Zero);
		mSpeed = new Vector3();
		mSpeed.set(Vector3.Zero);
		mDestination = new Vector3();
		mDestination.set(Vector3.Zero);
		mLastStep = new Vector3();
		mLastStep.set(Vector3.Zero);
		mDirection = new Vector3();
		mDirection.set(Vector3.Zero);
	}

	public void Update(float deltaTime) {
		applyCurrentTask(deltaTime);

		// f=m*a;
		// v=a*t
		// s=v*t
		mLastStep.set(mSpeed.tmp().mul(deltaTime));
		mPosition.add(mLastStep);		
	}
	
	public void SetDestinationSpeed(Vector3 cpy) {
		mSpeed.set(cpy);
	}

	public void SetDestinationRelative(Vector3 relative) {
		mDestination.set(relative);
	}

	public void setDestination(Vector3 destination) {
		mDestination.set(destination);
		destination.x += Par.THUMB_CORRECTION;
		mSpeed.set(destination.tmp().sub(mPosition).mul(Par.SHIP_MAX_SPEED));
	}
	
	private void setDirection(Vector3 mValue) {
		mDirection.set(mValue);	
	}

	public void SetTarget(Vector3 target) {
		mYAangle = (float) -MathUtils.radiansToDegrees
				* MathUtils.atan2(target.z - mPosition.z, target.x
						- mPosition.x);
		Gdx.app.log("PhysicItem", String.format(
				"Target:%s Position:%s Angle:%f", target, mPosition, mYAangle));
	}

	public void Stop() {
		mSpeed.mul(0.90f);
	}

	@Override
	public String toString() {
		String ret = String.format(
				"Speed:%s\nLastStep:%s\nPosition: %s\nY_Angle:%f", mSpeed,
				mLastStep, mPosition, mYAangle);
		return ret;

	}

	class Task {
		public TASK_TYPE mType;
		public Vector3 mValue = new Vector3(Vector3.Zero);
		public int mDuration;
		public boolean mWait;
		public int mGotoBack;
		public int mRepeat;
		public boolean mLaunched;
		
		
	}

	enum TASK_TYPE
	{
		DIRECTION,
		DESTINATION,
		SPEED,
		ACTION_DELETE
	}
	
	private Array<Task> mTasks = new Array<Task>();
	private Task mCurrent;

	public void scheduleTask(TASK_TYPE type,Vector3 value, int decSec,
			boolean wait, int gotoBack, int repeat) {
		Task t = new Task();
		t.mType = type;
		t.mValue.set(value);		
		t.mDuration = decSec;
		t.mWait = wait;
		t.mGotoBack = gotoBack;
		t.mRepeat = repeat;
		mTasks.add(t);
	}

	public void applyCurrentTask(float deltaTime) {
		deltaTime *= 10;

		/*
		 * STOP ON DESTINANTION! float currentDistant =
		 * mDestination.dst(mPosition);
		 * 
		 * if(mLastDestDist != 0 && mLastDestDist - currentDistant < 0) { mSpeed
		 * = Vector3.Zero; } mLastDestDist = currentDistant;
		 */
		if (mCurrent != null) {
			mCurrent.mDuration -= deltaTime;
			if (mCurrent.mDuration <= 0) {
				mTasks.removeIndex(0);
				mCurrent = null;
			} else {
				return;
			}
		}

		if (mTasks.size > 0) {
			mCurrent = mTasks.first();
			mCurrent.mDuration -= deltaTime;
			
			switch(mCurrent.mType)
			{
			case ACTION_DELETE:
				break;
			case DESTINATION:
				setDestination(mCurrent.mValue);
				break;
			case DIRECTION:
				setDirection(mCurrent.mValue);
				break;
			case SPEED:
				mSpeed.set(mCurrent.mValue);
				break;
			default:
				break;
			
			}
			
		}
	}

	
}
