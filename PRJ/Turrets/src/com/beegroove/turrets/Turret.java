package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector3;

public class Turret extends PhysicItem {
	ArrayList<Shoot> shoots = new ArrayList<Shoot>();

	enum TTYPE {
		SINGLE_SMALL, DOUBLE_SMALL, SINGLE_MEDIUM, DOUBLE_CANNON, SINGLE_LARGE, DOUBLE_LARGE, TRIPLE_LARGE
	}

	public TTYPE type;

	@Override
	public void Create() {
		super.Create();
	}

	private long lastfire, firerate = 100;

	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);
		if (fire) {
			if (lastfire < System.currentTimeMillis() - firerate) {

				switch (type) {
				case DOUBLE_CANNON:
					break;
				case DOUBLE_LARGE:
					break;
				case DOUBLE_SMALL:
					shoots.add(WeaponFactory.NewBasicLeftShoot(this));
					shoots.add(WeaponFactory.NewBasicRightShoot(this));
					break;
				case SINGLE_LARGE:
					break;
				case SINGLE_MEDIUM:
					break;
				case SINGLE_SMALL:
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
			if (s.position.x > 25) {
				iterator.remove();
			}
		}

	}

	public boolean fire;

}
