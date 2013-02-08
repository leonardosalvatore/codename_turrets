package com.beegroove.turrets;

import java.util.ArrayList;
import java.util.List;

import sun.text.normalizer.UProperty;

import com.badlogic.gdx.math.Vector3;

public class StarShip extends PhysicItem {

	enum STYPE
 	{
		BASIC,
		STANDARD,
		ADVANCED,
		PRO, 
		BASIC_DOUBLE
	}
	
	ArrayList<Turret> turrets = new ArrayList<Turret>();
	private boolean firing;
	public STYPE type;
	
	@Override
	public void Create() {
		super.Create();
		
	}
	

	@Override
	public void Update(float deltaTime) {
		super.Update(deltaTime);

		for (Turret turret : turrets) {
			turret.fire = firing;
			turret.position.add(lastStep);
			turret.Update(deltaTime);
		}

	}

	public void Fire(boolean b)
	{
		firing = b;
	}
	
	public void StopShip() {

		Stop(); 
		
		for (Turret turret : turrets) {
			turret.Stop();
		}
	}
	}
