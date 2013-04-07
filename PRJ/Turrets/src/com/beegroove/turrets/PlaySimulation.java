package com.beegroove.turrets;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Enemy.ETYPE;
import com.beegroove.turrets.Par.DIRECTION;

public class PlaySimulation extends Simulation {
	public StarShip starship;
	public Array<Enemy> enemies = new Array<Enemy>();
	public Array<Enemy> enemiesToAdd = new Array<Enemy>();

	public Array<PhysicItem> explosions = new Array<PhysicItem>();
	public int Score = 0;
	public int mCountDown = Par.INITIAL_COUNTDOWN;
	protected Sound explosionSound;
	private Sound laserSound;

	public PlaySimulation() {
		super();

		explosionSound = Gdx.audio.newSound(Gdx.files
				.internal("data/explosion.wav"));
		laserSound = Gdx.audio.newSound(Gdx.files.internal("data/laser.wav"));

		// mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,-30,0), 100,
		// false, 0, 0);
		// mCameraMan.scheduleTask(TASK_TYPE.DESTINATION,Par.CAMERA_INITIAL_POSITION,
		// 0, false, 0, 0);
		//
		// mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,6,0), 100,
		// false, 0, 0);
		// mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,-6,0), 100,
		// false, 0, 0);
		// mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,0,0), 100,
		// false, 0, 0);
	}

	public void update(float delta) {
		mCountDown -= delta;

		mCameraMan.Update(delta);
		starship.Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		HUD.Instance().Update(delta);

		if (enemies.size < Par.INITIAL_WAVE_NUMBER / 2) {
			enemies.addAll(WaveFactory.Instance().getMeteoriteWave(
					starship.mPosition));
		}

	}

	private void UpdateEnemyAndCollisionCheck(float delta) {
		enemiesToAdd.clear();

		for (Iterator<Enemy> iteratorEnemy = enemies.iterator(); iteratorEnemy
				.hasNext();) {
			Enemy currentEnemy = (Enemy) iteratorEnemy.next();
			currentEnemy.Update(delta);

			if (starship.mPosition.dst(currentEnemy.mPosition) < (currentEnemy.mSize + starship.mSize)) {
				if (currentEnemy.mType == ETYPE.METEORITE) {
					starship.mEnergy -= currentEnemy.mEnergy;
					currentEnemy.mSpeed.add((currentEnemy.mPosition.cpy()
							.sub(starship.mPosition))
							.div(currentEnemy.mSize * 4));

					if (starship.turrets.size() > 1) {
						starship.turrets.get(0).mEnergy -= currentEnemy.mEnergy;
						if (starship.turrets.get(0).mEnergy <= 0) {
							starship.turrets.remove(0);
						}
					}
				}
			} else {
				for (Turret t : starship.turrets) {
					for (Iterator<Shoot> iteratorShoot = t.shoots.iterator(); iteratorShoot
							.hasNext();) {
						Shoot shoot = (Shoot) iteratorShoot.next();
						if (shoot.mPosition.dst(currentEnemy.mPosition) < currentEnemy.mSize + 0.5f) {
							currentEnemy.mEnergy--;

							if (currentEnemy.mEnergy > 0) {
								// DAMAGED
								HighscoreAndStats.sNumberHits++;
								explosions.add(FXFactory.asteroidDamaged(shoot,
										currentEnemy.mSize));
							} else {
								// DESTROYED
								if (Par.SETTINGS_AUDIO) {
									explosionSound.play();
								}
								HUD.Instance().NewMessage(
										"+" + (int) currentEnemy.mSize,
										shoot.mScreenPosition);
								Score += currentEnemy.mSize;

								for (int ka = 1; ka < currentEnemy.mSize + 2; ka++) {

									explosions.add(FXFactory.asteroidDestroyed(
											shoot, currentEnemy.mSize));

									if (currentEnemy.mSize > 1f) {
										enemiesToAdd.add(currentEnemy
												.getPiece());
									}
								}
							}
							iteratorShoot.remove();
						}
					}
				}
			}

			if (currentEnemy.mPosition.x < Par.PLAYABLE_PLANE_X_LIMIT
					|| currentEnemy.mPosition.z < Par.PLAYABLE_PLANE_Z_MIN_LIMIT
					|| currentEnemy.mPosition.z > Par.PLAYABLE_PLANE_Z_MAX_LIMIT
					|| currentEnemy.mPosition.y > Par.PLAYABLE_PLANE_Y_LIMIT) {
				iteratorEnemy.remove();
				HighscoreAndStats.sAsteroidsLost++;
			} else if (currentEnemy.mEnergy <= 0) {
				HighscoreAndStats.sAsteroidsDestroyed++;
				iteratorEnemy.remove();
			}
		}

		for (Iterator<PhysicItem> iteratorExplosion = explosions.iterator(); iteratorExplosion
				.hasNext();) {
			PhysicItem item = (PhysicItem) iteratorExplosion.next();
			item.Update(delta);
			if (item.mToRemove) {
				iteratorExplosion.remove();
			}
		}

		enemies.addAll(enemiesToAdd);
		enemiesToAdd.clear();
	}

	public void StopShip() {
		starship.StopShip();
	}

	public void rotateTurret(float Xangle, float Yangle, float Zangle) {
		for (Turret turret : starship.turrets) {
			if (turret.canRotate()) {
				turret.mRotationDegree.x += Xangle;
				turret.mRotationDegree.y += Yangle;
				turret.mRotationDegree.z += Zangle;

				turret.mRotation.mul(new Quaternion().setEulerAngles(Yangle,
						Zangle, Xangle));
			}
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
			starship.SetDestinationSpeed(Vector3.Z.cpy().mul(
					-Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case DOWN:
			starship.SetDestinationSpeed(Vector3.Z.cpy().mul(
					Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case LEFT:
			starship.SetDestinationSpeed(Vector3.X.cpy().mul(
					-Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case RIGHT:
			starship.SetDestinationSpeed(Vector3.X.cpy().mul(
					Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		}
	}

	public void SetTurretTarget(Vector3 v1) {
		for (Turret turret : starship.turrets) {
			turret.SetTarget(v1);
		}
	}

	private boolean isLaserSoundPlay = false;

	private void laserSoundPlay(boolean play) {
		if (Par.SETTINGS_AUDIO) {
			if (play) {
				if (!isLaserSoundPlay) {
					laserSound.loop(0.3f);
				}
			} else {
				laserSound.stop();
			}
		}
		isLaserSoundPlay = play;
	}

	public void Fire(boolean b, boolean superFire) {

		starship.Fire(b, superFire);
		laserSoundPlay(b);
	}

	public GAME isGameOver() {
		if (starship.mEnergy <= 0) {
			laserSoundPlay(false);
			return GAME.OVER_ENERGY;
		} else if (mCountDown <= 0) {
			laserSoundPlay(false);
			return GAME.OVER_COUNTDOWN;
		} else {
			return GAME.PLAY;
		}

	}

}
