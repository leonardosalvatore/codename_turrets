package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Enemy.ETYPE;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;

public class WaveFactory {
	private Random rand;
	private static WaveFactory instance = null;
	public static int mWaveNumber;

	protected WaveFactory() {
		rand = new Random(System.currentTimeMillis());
	}

	public static WaveFactory Instance() {
		if (instance == null) {
			instance = new WaveFactory();
		}
		return instance;
	}

	public Array<Enemy> getMeteoriteWave(Vector3 mShipPosition) {
		mWaveNumber++;
		Array<Enemy> ret = new Array<Enemy>();
		HUD.Instance().NewMessageRoller(Par.MSG_NEW_ENEMY_WAVE);
		int number = Par.INITIAL_WAVE_NUMBER;
		for (int i = 0; i < number; i++) {
			Enemy temp = new Enemy();
			temp.mPosition = new Vector3(40 + (rand.nextInt(100)), // WAVE LENGTH
					0, //
					rand.nextInt(30) - 20); // WAVE WITDH

			if (rand.nextInt(50) == 0) {
				temp.mSize = 0.7f;
				temp.mType = ETYPE.SPUTNIK;
				temp.mHeading = (float) rand.nextInt(360);
				temp.mYAngleSpeed = (float) rand.nextInt(10) - 5;
				temp.scheduleTask(TASK_TYPE.SPEED,
						new Vector3(-(rand.nextInt(10 + mWaveNumber) + 3), 0, -2
								+ rand.nextInt(4)),
						50 + 10 * rand.nextInt(10), true, 0, 0);
				temp.scheduleTask(TASK_TYPE.SPEED,
						new Vector3(-(rand.nextInt(10 + mWaveNumber) + 3), 0, -2
								+ rand.nextInt(4)),
						50 + 10 * rand.nextInt(10), true, 0, 0);
				temp.scheduleTask(TASK_TYPE.SPEED,
						new Vector3(-(rand.nextInt(10 + mWaveNumber) + 3), 0, -2
								+ rand.nextInt(4)),
						50 + 10 * rand.nextInt(10), true, 0, 0);
				temp.scheduleTask(TASK_TYPE.SPEED,
						new Vector3(-(rand.nextInt(10 + mWaveNumber) + 3), 0, -2
								+ rand.nextInt(4)),
						50 + 10 * rand.nextInt(10), true, 0, 0);
				temp.scheduleTask(TASK_TYPE.SPEED,
						new Vector3(-(rand.nextInt(10 + mWaveNumber) + 3), 0, -2
								+ rand.nextInt(4)),
						50 + 10 * rand.nextInt(10), true, 0, 0);
			} else {
				temp.mHeading = (float) rand.nextInt(360);
				temp.mYAngleSpeed = (float) rand.nextInt(10) - 5;
				temp.mSize = rand.nextInt((int) (2 + mWaveNumber * .4));
				temp.mEnergy = (int) (temp.mSize * 3);
				temp.mType = ETYPE.METEORITE;
				temp.scheduleTask(TASK_TYPE.SPEED,
						new Vector3(-(rand.nextInt(10 + mWaveNumber) + 3), 0, -2
								+ rand.nextInt(4)),
						50 + 10 * rand.nextInt(10), true, 0, 0);
			}

			ret.add(temp);
		}

		return ret;
	}

	public void GenerateRandomFormation(Enemy.ETYPE type, int number) {
		for (int k = 0; k < number; k++) {
			// TODO use the Physic item tasks...
		}

	}
}
