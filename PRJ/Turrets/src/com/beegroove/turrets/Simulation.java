package com.beegroove.turrets;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Par.DIRECTION;

public class Simulation {
	public transient SimulationListener listener;
	public StarShip starship;
	public Cameraman mCameraMan;
	public Array<Enemy> enemies = new Array<Enemy>();
	public int WaveNumber = 0;
	private Random rand;
	public int Score = 0, Missed = 0;

	public Simulation() {
		rand = new Random(System.currentTimeMillis());

		mCameraMan = new Cameraman();

		//mCameraMan.scheduleTask(null, Vector3.Zero, 300, false, 0, 0);
		//mCameraMan.scheduleTask(null, new Vector3(Par.CAMERA_X,Par.CAMERA_DIRECTION_Y,Par.CAMERA_DIRECTION_Z), 300, false, 0, 0);
		//mCameraMan.scheduleTask(Vector3.Zero,null, 1000, false, 0, 0);
	}

	public void update(float delta) {
		
		mCameraMan.Update(delta);
		starship.Update(delta);
		
		HUD.Instance().Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		if (null == enemies.first()) {
			enemies.addAll(EnemyForceFactory.Instance().getMeteoriteWave(starship.mPosition));
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
							if (k.mEnergy == 0) {
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

	public void moveCameraAddX(float camerastep, boolean direction) {
		if (direction) {
			mCameraMan.mPosition.add(camerastep, 0, 0);
		} else {
			mCameraMan.mDirection.add(camerastep, 0, 0);
		}
	}

	public void moveCameraAddY(float camerastep, boolean direction) {
		if (direction) {
			mCameraMan.mPosition.add(0, camerastep, 0);
		} else {
			mCameraMan.mDirection.add(0, camerastep, 0);
		}
	}

	public void moveCameraAddZ(float camerastep, boolean direction) {
		if (direction) {
			mCameraMan.mPosition.add(0, 0, camerastep);
		} else {
			mCameraMan.mDirection.add(0, 0, camerastep);
		}
	}
	
	public void moveCameraRotate(float f) {
		mCameraMan.mAngle = f;
	}

	public void FOVMinus(float cameraFovStep) {
		mCameraMan.FOV -= cameraFovStep;
	}

	public void FOVPlus(float cameraFovStep) {
		mCameraMan.FOV += cameraFovStep;
	}

	public void SetStarshipDestination(Vector3 v1) {
		starship.setDestination(v1);
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
		starship.Fire(b, superFire);
	}



}
