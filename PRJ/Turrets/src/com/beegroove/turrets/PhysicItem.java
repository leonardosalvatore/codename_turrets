package com.beegroove.turrets;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class PhysicItem {

	Hashtable<String, Float> Tag;
	public Vector3 position = Vector3.Zero;
	protected Vector3 speed = Vector3.Zero;
	protected Vector3 acceleration = Vector3.Zero;
	public Vector3 force = Vector3.Zero;
	public Vector3 lastStep = Vector3.Zero;
	public float mass = 1;
	public float y_angle;

	public PhysicItem() {
	}

	public void Create() {
	}

	
	public void Update(float deltaTime) {
		if(force.isZero())
		{
			return;
		}
		// a=f/m
		acceleration = force.cpy().div(mass);
		// v=a*t
		speed.add(acceleration.cpy().mul(deltaTime));

	//	if (speed.len() >= Parameters.SHIP_MAX_SPEED) {
	//		speed.nor().mul(Parameters.SHIP_MAX_SPEED);
	//	}

		// s=v*t
		lastStep = speed.cpy().mul(deltaTime);
		position.add(lastStep);
	}
	
	public void Stop()
	{
		Gdx.app.log("T", "stop");
		
		force = Vector3.Zero;
		acceleration = Vector3.Zero;
		speed = Vector3.Zero;
		lastStep = Vector3.Zero;
	}

	@Override
	public String toString() {
		String ret = String
				.format("Position: %s \nSpeed:%s \nAcceleration:%s \nForce:%s\nMass:%f \nY_Angle:%f",
						position, speed, acceleration, force, mass, y_angle);

		return ret;

	}

}
