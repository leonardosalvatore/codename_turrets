package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.List;

import sun.text.normalizer.UProperty;

import com.badlogic.gdx.math.Vector3;

public class StarShip extends PhysicItem {

	enum STYPE
 	{
		BASIC,
		BASIC_DOUBLE, 
		STANDARD,
		STANDARD_DOUBLE,
		ADVANCED_DOUBLE,
		GUNSIHP_DOUBLE, 
		BATTLECRUISER
	}
	
	ArrayList<Turret> turrets = new ArrayList<Turret>();
	private boolean mFiring = false;
	public STYPE type;
	private boolean mSuperFire = false;
	public int mEnergy_Initial;


	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);

		for (Turret turret : turrets) {
			turret.mFire = mFiring;
			turret.mSuperFire = mSuperFire;
			turret.mPosition.add(mLastStep);
			turret.Update(deltaTime);
		}
		
	}

	public void Fire(boolean b, boolean sFire)
	{
		mFiring = b;
		mSuperFire = sFire;
	}
	
	public void StopShip() {

		Stop(); 
		
		for (Turret turret : turrets) {
			turret.Stop();
		}
	}

	
	}
