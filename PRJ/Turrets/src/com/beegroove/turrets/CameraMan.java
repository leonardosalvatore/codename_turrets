package com.beegroove.turrets;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.beegroove.turrets.PhysicItem;
public class CameraMan extends PhysicItem {
	public static float FOV;

	public CameraMan() {
		
		mPosition = new Vector3(Par.CAMERA_X, Par.CAMERA_Y, Par.CAMERA_Z);
		mDirection = new Vector3(Par.CAMERA_DIRECTION_X,
				Par.CAMERA_DIRECTION_Y, Par.CAMERA_DIRECTION_Z);
		FOV = Par.CAMERA_FOV;

	}	
}
