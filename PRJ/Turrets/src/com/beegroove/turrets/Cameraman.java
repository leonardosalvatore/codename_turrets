package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;
public class Cameraman extends PhysicItem {
	public float FOV;
	public float mAngle;
	
	public Cameraman() {
		
		mPosition.set(Par.CAMERA_INITIAL_POSITION);
		mDirection.set(Par.CAMERA_INITIAL_DIRECTION);
		FOV = Par.CAMERA_FOV;
		
	}	
}
