package com.beegroove.turrets;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Enemy.ETYPE;
import com.beegroove.turrets.Par.DIRECTION;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;

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

		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,-30,0), 100, false, 0, 0);
		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,30,0), 100, false, 0, 0);
		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,0,0), 100, false, 0, 0);
		//mCameraMan.scheduleTask(TASK_TYPE.DESTINATION,Par.CAMERA_INITIAL_POSITION, 0, false, 0, 0);
	}

	public void update(float delta) {

		mCameraMan.Update(delta);
		starship.Update(delta);

		HUD.Instance().Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		if (enemies.size < 3) {
			enemies.addAll(WaveFactory.Instance().getMeteoriteWave(
					starship.mPosition));
		}
	}

	private void UpdateEnemyAndCollisionCheck(float delta) {

		for (Iterator<Enemy> iteratorEnemy = enemies.iterator(); iteratorEnemy
				.hasNext();) {
			Enemy k = (Enemy) iteratorEnemy.next();
			k.Update(delta);

			if (starship.mPosition.dst(k.mPosition) < (k.mSize + starship.mSize)) {
				if (k.mType == ETYPE.METEORITE) {
					starship.mEnergy--;
				} else if (k.mType == ETYPE.SPUTNIK) {
					iteratorEnemy.remove();
				}
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
							if (k.mEnergy <= 0) {
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


	public void SetStarshipSpeed(Vector3 speed) {
		starship.SetDestinationSpeed(speed);
	}

	
	public void SetStarshipDirection(DIRECTION direction) {
		switch (direction) {
		case UP:
			starship.SetDestinationSpeed(Vector3.Z.cpy().mul(-Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case DOWN:
			starship.SetDestinationSpeed(Vector3.Z.cpy().mul(Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case LEFT:
			starship.SetDestinationSpeed(Vector3.X.cpy().mul(-Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case RIGHT:
			starship.SetDestinationSpeed(Vector3.X.cpy().mul(Par.SHIP_MAX_SPEED_KEYBOARD));
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
