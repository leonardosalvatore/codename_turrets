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

	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);
		if (fire) {
			if (0 == (System.currentTimeMillis() / 10)%33) {
				Shoot s = new Shoot();
				s.Create();
				s.position = this.position.cpy();
				s.speed = new Vector3(20, 0, 0);
				shoots.add(s);
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
