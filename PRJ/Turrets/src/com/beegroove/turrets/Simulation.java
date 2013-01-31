package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class Simulation {
	public float FOV = 0;
	public transient SimulationListener listener;
	public StarShip starship;
	public Vector3 camera_position;

	public void update(float delta) {
		starship.Update(delta);
	}

	public Simulation() {
		starship = new StarShip();
		starship.Create();
		camera_position = new Vector3(Parameters.CAMERA_X, Parameters.CAMERA_Y,
				Parameters.CAMERA_Z);
		FOV = Parameters.CAMERA_FOV;
	}

	public void StopShip() {
		starship.StopShip();
	}

	public void rotateTurret(float angle) {
		for (Turret turret : starship.turrets) {
			turret.y_angle += angle;
		}
	}

	public void moveCameraDown(float camerastep) {
		camera_position.add(0, -camerastep, 0);
	}

	public void moveCameraUp(float camerastep) {
		camera_position.add(0, camerastep, 0);
	}

	public void FOVMinus(float cameraFovStep) {
		FOV -= cameraFovStep;
	}

	public void FOVPlus(float cameraFovStep) {
		FOV += cameraFovStep;
	}

	public void SetStarshipDestination(Vector3 v1) {
		starship.SetDestination(v1);
	}

	public void SetTurretTarget(Vector3 v1) {
		for (Turret turret : starship.turrets) {
			turret.SetTarget(v1);
		}
	}

	public void Fire(boolean b) {
		starship.Fire(b);
	}
}
