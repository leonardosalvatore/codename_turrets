package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Enemy.ETYPE;

public class EnemyForceFactory {
	private  Random rand;
	private static EnemyForceFactory instance = null;
	private int WaveNumber;

	protected EnemyForceFactory() {
		rand = new Random(System.currentTimeMillis());
	}

	public static EnemyForceFactory Instance() {
		if (instance == null) {
			instance = new EnemyForceFactory();
		}
		return instance;
	}

	
	public Array<Enemy> getMeteoriteWave(Vector3 mShipPosition) {
		WaveNumber++;
		Array<Enemy> ret = new Array<Enemy>();
		HUD.Instance().NewMessageRoller(Par.MSG_NEW_ENEMY_WAVE);
		int number = Par.INITIAL_WAVE_NUMBER + WaveNumber*2;
		for (int i = 0; i < number; i++) {
			Enemy temp = new Enemy();
			temp.mPosition = new Vector3(40 + (rand.nextInt(30)) , //WAVE LENGTH
					0, //
					rand.nextInt(30) - 20); // WAVE WITDH
			
			temp.mYAangle = (float) rand.nextInt(360);
			temp.mType = ETYPE.METEORITE;
			temp.mYAngleSpeed = (float) rand.nextInt(10)-5;
			temp.mSize = rand.nextInt((int) (2+WaveNumber*.5));
			temp.mEnergy = (int) (temp.mSize * 3);
	
			
			temp.scheduleTask(new Vector3(-(rand.nextInt(10+ WaveNumber) + 3), 0, -2 + rand.nextInt(4)), null,50+10 * rand.nextInt(10), true, 0, 0);
			temp.scheduleTask(new Vector3(-(rand.nextInt(10+ WaveNumber) + 3), 0, -2 + rand.nextInt(4)), null,50+10 * rand.nextInt(10), true, 0, 0);
			temp.scheduleTask(new Vector3(-(rand.nextInt(10+ WaveNumber) + 3), 0, -2 + rand.nextInt(4)), null,50+10 * rand.nextInt(10), true, 0, 0);	
			temp.scheduleTask(new Vector3(-(rand.nextInt(10+ WaveNumber) + 3), 0, -2 + rand.nextInt(4)), null,50+10 * rand.nextInt(10), true, 0, 0);	
			temp.scheduleTask(new Vector3(-(rand.nextInt(10+ WaveNumber) + 3), 0, -2 + rand.nextInt(4)), null,50+10 * rand.nextInt(10), true, 0, 0);	
			temp.scheduleTask(null, mShipPosition,100, true, 0, 0);
			
			ret.add(temp);
		}
		
		
		return ret;
	}
	
	public void GenerateRandomFormation(Enemy.ETYPE type, int number)
	{
		for (int k=0;k<number;k++)
		{
			//TODO use the Physic item tasks... 
		}
	
	}
}
