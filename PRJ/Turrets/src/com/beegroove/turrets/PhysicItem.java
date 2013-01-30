package com.beegroove.turrets;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class PhysicItem {

	Hashtable<String, Float> Tag;
	public Vector3 position = Vector3.Zero;
	protected Vector3 speed = Vector3.Zero;
	public Vector3 lastStep = Vector3.Zero;
	private Vector3 destination = Vector3.Zero;
	public float mass = 1;
	public float y_angle;

	public PhysicItem() {
	}

	public void Create() {
	}

	public void MoveTo(Vector3 newdestination)
	{
		destination = newdestination;
	}
	
	public void ApplyForce(Vector3 force) {
		Vector3 acceleration = Vector3.Zero;
		// a=f/m
		acceleration = force.div(mass);
		// v=a*t
		
		speed.add(acceleration.mul(0.3f));

		if (speed.len() >= Parameters.SHIP_MAX_SPEED) {
			speed.nor().mul(Parameters.SHIP_MAX_SPEED);
		}
	}
	
	public void Update(float deltaTime) {
		// s=v*t
		lastStep = speed.cpy().mul(deltaTime);
		position.add(lastStep);
	}
	
	public void Stop()
	{
		speed.mul(0.90f);
	}

	@Override
	public String toString() {
		String ret = String
				.format("Position: %s \nSpeed:%s \nMass:%f \nY_Angle:%f",
						position, speed, mass, y_angle);

		return ret;

	}

}
