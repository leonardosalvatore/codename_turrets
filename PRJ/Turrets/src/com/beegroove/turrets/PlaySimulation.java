package com.beegroove.turrets;

import java.util.Iterator;
import java.util.Random;

import sun.awt.X11.MWMConstants;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Enemy.ETYPE;
import com.beegroove.turrets.HUD.Message;
import com.beegroove.turrets.Par.DIRECTION;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;

public class PlaySimulation extends Simulation {
	public StarShip starship;
	public Array<Enemy> enemies = new Array<Enemy>();
	public Array<Enemy> enemiesToAdd = new Array<Enemy>();

	public Array<PhysicItem> explosions = new Array<PhysicItem>();
	public int Score = 0, Missed = 0;
	public int mCountDown = Par.INITIAL_COUNTDOWN;

	public PlaySimulation() {
		super();
		
//		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,-30,0), 100, false, 0, 0);
//		mCameraMan.scheduleTask(TASK_TYPE.DESTINATION,Par.CAMERA_INITIAL_POSITION, 0, false, 0, 0);
//
//		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,6,0), 100, false, 0, 0);
//		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,-6,0), 100, false, 0, 0);
//		mCameraMan.scheduleTask(TASK_TYPE.SPEED,new Vector3(0f,0,0), 100, false, 0, 0);
	}

	public void update(float delta) {
		mCountDown-=delta;
		
		mCameraMan.Update(delta);
		starship.Update(delta);

		UpdateEnemyAndCollisionCheck(delta);

		HUD.Instance().Update(delta);

		if (enemies.size < Par.INITIAL_WAVE_NUMBER/2) {
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
					starship.mEnergy-=currentEnemy.mEnergy;
					currentEnemy.mSpeed.add((currentEnemy.mPosition.cpy().sub(starship.mPosition)).div(currentEnemy.mSize*4));
				}
			}  else {
				for (Turret t : starship.turrets) {
					for (Iterator<Shoot> iteratorShoot = t.shoots.iterator(); iteratorShoot
							.hasNext();) {
						Shoot shoot = (Shoot) iteratorShoot.next();
						if (shoot.mPosition.dst(currentEnemy.mPosition) < currentEnemy.mSize) {
							currentEnemy.mEnergy--;
							
							if(currentEnemy.mEnergy>0)
							{
								//DAMAGED
								PhysicItem tmp = new PhysicItem((PhysicItem)shoot);
								tmp.mPosition.x-=tmp.mSize;
								tmp.mSize=0.1f;
								tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(0.03f, 0, 0), 25, false, 0, 0);
								tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(0.03f, 0, 0), 25, false, 0, 0);
								tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false, 0, 0);
								explosions.add(tmp);
								
							}
							else
							{
								//DESTROYED
								HUD.Instance().NewMessage("+"+(int)currentEnemy.mSize,shoot.mScreenPosition);
								Score+=currentEnemy.mSize;
								
								for(int ka=1;ka<currentEnemy.mSize+2;ka++)
								{
									PhysicItem tmp = new PhysicItem((PhysicItem)shoot);
									tmp.mScreenPosition.y -= currentEnemy.mSize*10;
									tmp.mScreenPosition.x += (rand.nextFloat()-.5f)*currentEnemy.mSize*40;
									tmp.mScreenPosition.y += (rand.nextFloat()-.5f)*currentEnemy.mSize*40;
									tmp.mSize=0.1f;
									tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(0.1f, 0, 0), 
											25+(int) ((rand.nextFloat()-.4f)*currentEnemy.mSize*40), false, 0, 0);
									tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(0.05f, 0, 0), 
											25+(int) ((rand.nextFloat()-.4f)*currentEnemy.mSize*70), false, 0, 0);
									tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false, 0, 0);
									explosions.add(tmp);			
												
									if(currentEnemy.mSize>1f)
									{
									Enemy tmpE = new Enemy(currentEnemy);
									tmpE.mSize = currentEnemy.mSize/2;
									tmpE.mEnergy = (int) (3*tmpE.mSize);
									tmpE.mHeading = (float) rand.nextInt(360);
									tmpE.mYAngleSpeed = (float) rand.nextInt(10) - 5;
									
									tmpE.scheduleTask(TASK_TYPE.SPEED, new Vector3(
											currentEnemy.mSpeed.x+(rand.nextInt(8)-4), 
											currentEnemy.mSpeed.y,
											currentEnemy.mSpeed.z+rand.nextInt(10)-5
											), 20, false, 0, 0);

									enemiesToAdd.add(tmpE);
									}
								}
							}
							iteratorShoot.remove();
						}
					}
				}
			}
			
			if (currentEnemy.mPosition.x < -30 || 
					currentEnemy.mPosition.z < -25 ||
					currentEnemy.mPosition.z > 20 ||
					currentEnemy.mPosition.y > 60) {
				iteratorEnemy.remove();
				Missed++;
			}
			else if (currentEnemy.mEnergy <= 0) {
				iteratorEnemy.remove();								
			}
		}
		
		for (Iterator<PhysicItem> iteratorExplosion = explosions.iterator(); iteratorExplosion.hasNext();)
		{
			PhysicItem item = (PhysicItem) iteratorExplosion.next();
			item.Update(delta);
			if(item.mToRemove)
			{
				iteratorExplosion.remove();
			}
		}
		
		enemies.addAll(enemiesToAdd);
		enemiesToAdd.clear();
	}

	public void StopShip() {
		starship.StopShip();
	}

	public void rotateTurret(float angle) {
		for (Turret turret : starship.turrets) {
			turret.mHeading += angle;
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

	
	public boolean gameAnimationOverLauncher = false;
	public void launchGameOverAnimation()
	{
		if(gameAnimationOverLauncher)
		{
			return;
		}
		gameAnimationOverLauncher=true;
		
		starship.Fire(false, false);
		starship.SetDestinationSpeed(new Vector3(0, 15, 0));
		
		for (Enemy en : enemies) {
			en.SetDestinationSpeed(new Vector3(0, 15, 0));
		}
		
		for(int k=0;k<=WaveFactory.mWaveNumber*10;k++)
		{
			PhysicItem tmp = new PhysicItem();
			tmp.mScreenPosition.x = rand.nextInt((int) Par.VIEWPORT_MAX_X);
			tmp.mScreenPosition.y = rand.nextInt((int) Par.VIEWPORT_MAX_Y);
			tmp.mSize=0.1f;
			tmp.scheduleTask(TASK_TYPE.SLEEP, new Vector3(0.00f, 0, 0), 150, false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(rand.nextFloat()/3+0.1f, 0, 0), 50, false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(rand.nextFloat()/3+0.1f, 0, 0), 400, false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false, 0, 0);
			explosions.add(tmp);
		}
	}
	public boolean isGameOver() {
		return starship.mEnergy <= 0 || mCountDown  <= 0;
	}

}
