package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector3;

public class Turret extends PhysicItem {
	ArrayList<Shoot> shoots = new ArrayList<Shoot>();

	enum TTYPE {
		SINGLE_SMALL, DOUBLE_SMALL, SINGLE_MEDIUM, DOUBLE_AUTOCANNON, SINGLE_LARGE, DOUBLE_LARGE, TRIPLE_LARGE
	}

	public TTYPE type;
	private boolean mIsLeft= true;
	private long lastfire, firerate = 200;
	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);
		
		if (mFire && 
				(mHeading<mHeadingMax && mHeading>mHeadingMin)) {
			if (lastfire < System.currentTimeMillis() - firerate) {
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
					firerate=200;
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
			if (s.mPosition.x > 27) {
				iterator.remove();
			}
		}

	}

	public boolean mFire;
	public boolean mSuperFire;

}
