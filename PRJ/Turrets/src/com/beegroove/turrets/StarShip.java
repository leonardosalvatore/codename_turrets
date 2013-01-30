package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.List;

import sun.text.normalizer.UProperty;

import com.badlogic.gdx.math.Vector3;

public class StarShip extends PhysicItem {

	ArrayList<Turret> turrets = new ArrayList<Turret>();
	
	@Override
	public void Create() {
		super.Create();
		position = Parameters.INITIAL_SHIP_POSITION;
		turrets.add(new Turret());

		for (Turret turret : turrets) {
			turret.Create();
		}
	}
	
	

	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);

		for (Turret turret : turrets) {
			turret.fire = true;
			turret.Update(deltaTime);
		}

	}

	
	
	public void StopShip() {

		Stop(); 
		
		for (Turret turret : turrets) {
			turret.Stop();
		}
	}


	}
