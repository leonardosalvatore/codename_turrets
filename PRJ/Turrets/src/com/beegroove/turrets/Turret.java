package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class Turret extends PhysicItem{
	
	
	@Override
	public void Create()
	{
		super.Create();
		position = Parameters.INITIAL_TURRET_POSITION;
	}
}
