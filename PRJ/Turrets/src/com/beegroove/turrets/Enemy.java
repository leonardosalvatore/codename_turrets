package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class Enemy extends PhysicItem {

	public enum ETYPE {
		BONUS,
		METEORITE
	}
	
	public ETYPE mType;

	public Enemy()	{}
	
	public Enemy(Enemy s) {
		mType = s.mType;
		mRotationSpeed.set(s.mRotationSpeed);
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
		mRotation.set(s.mRotation);
		mRotationSpeed.set(s.mRotationSpeed);
	}


	public Enemy getPiece()
	{
		Enemy tmpE = new Enemy(this);
		tmpE.mSize = Math.min(
				mSize / 2, 1);
		tmpE.mEnergy = (int) (3 * tmpE.mSize);
		tmpE.mRotation.set(Vector3.Y,(float) rand
				.nextInt(360));
		tmpE.mRotationSpeed.set(Vector3.Y, (float) rand
				.nextInt(10) - 5);

		tmpE.scheduleTask(
				TASK_TYPE.SPEED,
				new Vector3(
						mSpeed.x
								+ (rand.nextInt(8) - 4),
						mSpeed.y,
						mSpeed.z
								+ rand.nextInt(10)
								- 5), 20,
				false, 0, 0);
		
		return tmpE;

	}
	
}
