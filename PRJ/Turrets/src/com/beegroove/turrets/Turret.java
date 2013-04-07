package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.Iterator;

public class Turret extends PhysicItem {
	ArrayList<Shoot> shoots = new ArrayList<Shoot>();

	enum TTYPE {
		SINGLE_SMALL, DOUBLE_SMALL, SINGLE_MEDIUM, DOUBLE_AUTOCANNON, SINGLE_LARGE, DOUBLE_LARGE, TRIPLE_LARGE
	}
	
	
	public boolean canRotate()
	{
		return mRotationDegree.y<mHeadingMax && 
			   mRotationDegree.y>mHeadingMin;
	}
	
	private boolean canFire()
	{
		return mFire && canRotate();
	}

	public TTYPE type;
	private boolean mIsLeft= true;
	private long lastfire, firerate = 200;
	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);
		
		if (canFire()) {
				
			if (lastfire < System.currentTimeMillis() - firerate) {
				
				HighscoreAndStats.sNumberOfShoot++;
				
				switch (type) {
				case DOUBLE_AUTOCANNON:
					firerate=100;
					mIsLeft=!mIsLeft;
					if(mIsLeft)
					{
						shoots.add(WeaponFactory.NewBasicLeftShoot(this));
					}
					else
					{
						shoots.add(WeaponFactory.NewBasicRightShoot(this));
					}
					break;
				case DOUBLE_LARGE:
					firerate=100;
					mIsLeft=!mIsLeft;
					if(mIsLeft)
					{
						shoots.add(WeaponFactory.NewBasicLeftShoot(this));
					}
					else
					{
						shoots.add(WeaponFactory.NewBasicRightShoot(this));
					}
					break;
				case DOUBLE_SMALL:
					firerate=100;
					mIsLeft=!mIsLeft;
					if(mIsLeft)
					{
						shoots.add(WeaponFactory.NewBasicLeftShoot(this));
					}
					else
					{
						shoots.add(WeaponFactory.NewBasicRightShoot(this));
					}
					break;
				case SINGLE_LARGE:
					firerate=200;
					shoots.add(WeaponFactory.NewBasicShoot(this));
					break;
				case SINGLE_MEDIUM:
					firerate=200;
					shoots.add(WeaponFactory.NewBasicShoot(this));
					break;
				case SINGLE_SMALL:
					firerate=180;
					shoots.add(WeaponFactory.NewBasicShoot(this));
				case TRIPLE_LARGE:
					break;
				default:
					break;

				}
				lastfire = System.currentTimeMillis();
			}
		}

		for (Iterator<Shoot> iterator = shoots.iterator(); iterator.hasNext();) {
			Shoot s = (Shoot) iterator.next();
			s.Update(deltaTime);
			if (s.mPosition.x > 28) {
				iterator.remove();
			}
		}

	}

	public boolean mFire;
	public boolean mSuperFire;

}
