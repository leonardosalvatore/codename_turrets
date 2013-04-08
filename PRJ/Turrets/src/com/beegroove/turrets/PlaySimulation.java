package com.beegroove.turrets;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Asteroid.ETYPE;
import com.beegroove.turrets.Par.DIRECTION;

public class PlaySimulation extends Simulation {
	public SpaceShip spaceship;
	public Array<Asteroid> enemies = new Array<Asteroid>();
	public Array<Asteroid> enemiesToAdd = new Array<Asteroid>();
	
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
	}

	public void update(float delta) {
		mCountDown -= delta;

		mCameraMan.Update(delta);
		spaceship.Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		HUD.Instance().Update(delta);

		if (enemies.size < Par.INITIAL_WAVE_NUMBER / 2) {
			enemies.addAll(WaveFactory.Instance().getMeteoriteWave(
					spaceship.mPosition));
		}

	}

	private void UpdateEnemyAndCollisionCheck(float delta) {
		enemiesToAdd.clear();
		int lastEnergy = spaceship.mEnergy;

		for (Iterator<Asteroid> iteratorEnemy = enemies.iterator(); iteratorEnemy
				.hasNext();) {
			Asteroid currentEnemy = (Asteroid) iteratorEnemy.next();
			currentEnemy.Update(delta);

			if (spaceship.mPosition.dst(currentEnemy.mPosition) < (currentEnemy.mSize + spaceship.mSize)) {
				if (currentEnemy.mType == ETYPE.METEORITE) {
					spaceship.mEnergy -= currentEnemy.mEnergy;
					currentEnemy.mSpeed.add((currentEnemy.mPosition.cpy()
							.sub(spaceship.mPosition))
							.div(currentEnemy.mSize * 4));

					if (spaceship.turrets.size() > 1) {
						spaceship.turrets.get(0).mEnergy -= currentEnemy.mEnergy;
						if (spaceship.turrets.get(0).mEnergy <= 0) {
							spaceship.turrets.remove(0);
						}
					}
				}
			} else {
				for (Turret t : spaceship.turrets) {
					for (Iterator<Shoot> iteratorShoot = t.shoots.iterator(); iteratorShoot
							.hasNext();) {
						Shoot shoot = (Shoot) iteratorShoot.next();
						if (shoot.mPosition.dst(currentEnemy.mPosition) < currentEnemy.mSize + Par.SHOOT_CORRECTION) {
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
		
		spaceship.isHit = lastEnergy != spaceship.mEnergy;
		if (spaceship.isHit) {
			if (Par.SETTINGS_VIBRA) {
				Gdx.input.vibrate(Par.VIBRATION_SPACESHIP_HIT_DURATION);
			}
		}

	}

	public void StopShip() {
		spaceship.StopShip();
	}

	public void rotateTurret(float Xangle, float Yangle, float Zangle) {
		for (Turret turret : spaceship.turrets) {
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
		spaceship.setDestination(v1);
	}

	public void SetStarshipSpeed(Vector3 speed) {
		spaceship.SetDestinationSpeed(speed);
	}

	public void SetStarshipDirection(DIRECTION direction) {
		switch (direction) {
		case UP:
			spaceship.SetDestinationSpeed(Vector3.Z.cpy().mul(
					-Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case DOWN:
			spaceship.SetDestinationSpeed(Vector3.Z.cpy().mul(
					Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case LEFT:
			spaceship.SetDestinationSpeed(Vector3.X.cpy().mul(
					-Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		case RIGHT:
			spaceship.SetDestinationSpeed(Vector3.X.cpy().mul(
					Par.SHIP_MAX_SPEED_KEYBOARD));
			break;
		}
	}

	public void SetTurretTarget(Vector3 v1) {
		for (Turret turret : spaceship.turrets) {
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

		spaceship.Fire(b, superFire);
		laserSoundPlay(b);
	}

	public GAME isGameOver() {
		if (spaceship.mEnergy <= 0) {
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
