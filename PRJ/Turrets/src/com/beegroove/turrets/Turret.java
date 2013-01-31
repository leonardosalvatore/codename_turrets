package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector3;

public class Turret extends PhysicItem {
	ArrayList<Shoot> shoots = new ArrayList<Shoot>();
	
	@Override
	public void Create() {
		super.Create();
		position = Parameters.INITIAL_TURRET_POSITION;
	}

	private long lastfire,firerate=300;
	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);
		if (fire) {
				if(lastfire < System.currentTimeMillis() - firerate )
				{
					shoots.add(WeaponFactory.NewBasicShoot(this));
					lastfire = System.currentTimeMillis();
				}
		}
		
		
		for (Iterator<Shoot> iterator = shoots.iterator(); iterator.hasNext();) {
			Shoot s = (Shoot) iterator.next();
			s.Update(deltaTime);
			if(s.position.x > 25)
			{
				iterator.remove();
			}
		}

	}

	public boolean fire;

	
}
