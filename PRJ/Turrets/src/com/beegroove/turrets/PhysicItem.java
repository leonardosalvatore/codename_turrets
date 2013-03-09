package com.beegroove.turrets;

import java.util.Hashtable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PhysicItem {

	Hashtable<String, Float> mTag;
	public Vector3 mPosition = Vector3.Zero;
	public Vector3 mSpeed = Vector3.Zero;
	public Vector3 mDestination = Vector3.Zero;
	public Vector3 mLastStep = Vector3.Zero;
	public Vector3 mDirection = Vector3.Zero;
	public float mYAangle;
	public float mSize;
	public int mEnergy;
	public float mLastDestDist;
	
	public void Create()
	{
		
	}

	public void Update(float deltaTime) {
		applyCurrentTask(deltaTime);

		
		// s=v*t
		mLastStep = mSpeed.cpy().mul(deltaTime);
		mPosition.add(mLastStep);
		
	}

	public void SetDestinationRelative(Vector3 relative) {
		// TODO uhmmm....
		setDestination(mPosition.cpy().add(relative));
	}

	public void setDestination(Vector3 destination) {
		mDestination = destination.cpy();
		destination.x += Par.THUMB_CORRECTION;
		mSpeed = destination.sub(mPosition).mul(Par.SHIP_MAX_SPEED);
		
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
		public Vector3 mSpeed;
		public int mDuration;
		public boolean mWait;
		public int mGotoBack;
		public int mRepeat;
		public boolean mLaunched;
		public Vector3 mDestination;
	}

	private Array<Task> mTasks = new Array<Task>();
	private Task mCurrent;

	public void scheduleTask(Vector3 speed, Vector3 destination, int decSec,
			boolean wait, int gotoBack, int repeat) {
		Task t = new Task();
		if(speed!=null)
		{
		t.mSpeed = speed.cpy();
		}
		if(destination!=null)
		{
		t.mDestination = destination.cpy();
		}
		t.mDuration = decSec;
		t.mWait = wait;
		t.mGotoBack = gotoBack;
		t.mRepeat = repeat;
		mTasks.add(t);
	}

	public void applyCurrentTask(float deltaTime) {
		deltaTime *= 10;

		
		/*
		 *  STOP ON DESTINANTION!
		 * float currentDistant = mDestination.dst(mPosition);

		if(mLastDestDist != 0 && mLastDestDist - currentDistant < 0)
		{
			mSpeed = Vector3.Zero;
		}
		mLastDestDist = currentDistant;
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
			if (mCurrent.mSpeed != null) {
				mSpeed = mCurrent.mSpeed;
			}
			else
			{
				mSpeed = Vector3.Zero;
			}

			if (mCurrent.mDestination != null) {
				setDestination(mCurrent.mDestination);
			}
		}
	}
}
