package com.beegroove.turrets;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Par.DIRECTION;

import de.matthiasmann.twlthemeeditor.datamodel.Param;

public class Simulation {
	public transient SimulationListener listener;
	public StarShip starship;
	public CameraMan mCameraMan;
	public Array<Enemy> enemies = new Array<Enemy>();
	public int WaveNumber = 0;
	private Random rand;
	public int Score = 0, Missed = 0;

	public Simulation() {
		rand = new Random(System.currentTimeMillis());

		mCameraMan  = new CameraMan();
	}

	public void update(float delta) {
		starship.Update(delta);

		HUD.Instance().Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		if (null == enemies.first()) {
			enemies.addAll(EnemyForceFactory.Instance().getMeteoriteWave());
		}
	}

	private void UpdateEnemyAndCollisionCheck(float delta) {

		for (Iterator<Enemy> iteratorEnemy = enemies.iterator(); iteratorEnemy
				.hasNext();) {
			Enemy k = (Enemy) iteratorEnemy.next();
			k.Update(delta);

			if (starship.mPosition.dst(k.mPosition) < (k.mSize + starship.mSize)) {
				starship.mEnergy--;
			} else if (k.mPosition.x < -30) {
				iteratorEnemy.remove();
				Missed++;
			} else {
				for (Turret t : starship.turrets) {
					for (Iterator<Shoot> iteratorShoot = t.shoots.iterator(); iteratorShoot
							.hasNext();) {
						Shoot s = (Shoot) iteratorShoot.next();
						if (s.mPosition.dst(k.mPosition) < k.mSize) {
							iteratorShoot.remove();
							k.mEnergy--;
							if(k.mEnergy == 0)
							{
								iteratorEnemy.remove();
								Score++;
							}
							break;
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
			turret.mYAangle += angle;
		}
	}

	public void moveCameraDown(float camerastep) {
		mCameraMan.mPosition.add(0, -camerastep, 0);
	}

	public void moveCameraUp(float camerastep) {
		mCameraMan.mPosition.add(0, camerastep, 0);
	}

	public void FOVMinus(float cameraFovStep) {
		mCameraMan.FOV -= cameraFovStep;
	}

	public void FOVPlus(float cameraFovStep) {
		mCameraMan.FOV += cameraFovStep;
	}

	public void SetStarshipDestination(Vector3 v1) {
		mCameraMan.mPosition.set(Par.CAMERA_X, Par.CAMERA_Y,
				Par.CAMERA_Z+v1.z/6);
		starship.SetDestination(v1);
	}

	public void SetStarshipDirection(DIRECTION direction) {
		switch (direction) {
		case UP:
			starship.SetDestinationRelative(Vector3.Y);
			break;
		case DOWN:
			starship.SetDestinationRelative(Vector3.Y.mul(-1));
			break;
		case LEFT:
			starship.SetDestinationRelative(Vector3.X);
			break;
		case RIGHT:
			starship.SetDestinationRelative(Vector3.X.mul(-1));
			break;
		}
	}

	public void SetTurretTarget(Vector3 v1) {
		for (Turret turret : starship.turrets) {
			turret.SetTarget(v1);
		}
	}

	public void Fire(boolean b, boolean superFire) {
		starship.Fire(b,superFire);
	}

}
