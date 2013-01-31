package com.beegroove.turrets;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class PhysicItem {

	Hashtable<String, Float> Tag;
	public Vector3 position = Vector3.Zero;
	protected Vector3 speed = Vector3.Zero;
	public Vector3 lastStep = Vector3.Zero;
	public float y_angle;

	public PhysicItem() {
	}

	public void Create() {
	}

	public void Update(float deltaTime) {
		// s=v*t
		lastStep = speed.cpy().mul(deltaTime);
		position.add(lastStep);
	}
	

	public void SetDestination(Vector3 destination) {
		destination.x += Parameters.THUMB_CORRECTION;
		speed = destination.sub(position).mul(Parameters.SHIP_MAX_SPEED);
	}
	
	public void SetTarget(Vector3 target) {
		y_angle= (float) - MathUtils.radiansToDegrees * MathUtils.atan2(target.z - position.z ,target.x - position.x);
		Gdx.app.log("PhysicItem", String.format("Target:%s Position:%s Angle:%f",target,position,y_angle));
	}
	
	public void Stop()
	{
		speed.mul(0.90f);
	}

	@Override
	public String toString() {
		String ret = String
				.format("Position: %s \nSpeed:%s \n\nY_Angle:%f",
						position, speed, y_angle);
		return ret;

	}

}
