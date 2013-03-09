package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;
public class Cameraman extends PhysicItem {
	public float FOV;
	public float mAngle;
	
	public Cameraman() {
		
		mPosition = new Vector3(Par.CAMERA_X, Par.CAMERA_Y, Par.CAMERA_Z);
		mDirection = new Vector3(Par.CAMERA_DIRECTION_X,
				Par.CAMERA_DIRECTION_Y, Par.CAMERA_DIRECTION_Z);
		FOV = Par.CAMERA_FOV;
		
	}	
}
