package com.beegroove.turrets;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Parameters.DIRECTION;

import de.matthiasmann.twlthemeeditor.datamodel.Param;

public class Simulation {
	public float FOV = 0;
	public transient SimulationListener listener;
	public StarShip starship;
	public Vector3 camera_position;
	public Array<Enemy> enemies = new Array<Enemy>();
	public int WaveNumber = 0;
	private Random rand;
	public int Score=0,Missed=0;

	public Simulation() {
		rand = new Random(System.currentTimeMillis());

		
		camera_position = new Vector3(Parameters.CAMERA_X, Parameters.CAMERA_Y,
				Parameters.CAMERA_Z);
		FOV = Parameters.CAMERA_FOV;

	}

	public void update(float delta) {
		starship.Update(delta);

		HUD.Instance().Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		if (enemies.size == 0) {
			enemies.addAll(EnemyForceFactory.Instance().LaunchMeteoriteWave());
		}
	}

	private void UpdateEnemyAndCollisionCheck(float delta) {

		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy k = (Enemy) iterator.next();
			k.Update(delta);
			
			if (starship.position.dst(k.position) < (k.size + starship.size)) {
				iterator.remove();
				starship.Energy--;
			}
			else if (k.position.x < -40) {
				iterator.remove();
				Missed++;
			} else {
				for (Turret t : starship.turrets) {
					for (Iterator<Shoot> iteratorShoot = t.shoots.iterator(); iteratorShoot
							.hasNext();) {
						Shoot s = (Shoot) iteratorShoot.next();
						if (s.position.dst(k.position) < k.size) {
							iteratorShoot.remove();
							iterator.remove();
							Score++;						
							}
					}
				}
			}
		}
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

	public void SetStarshipDirection(DIRECTION direction) {
		switch (direction) {
		case UP:
			// TODO
			break;
		case DOWN:
			// TODO
			break;
		case LEFT:
			// TODO
			break;
		case RIGHT:
			// TODO
			break;
		}
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
