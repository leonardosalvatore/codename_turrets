package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Asteroid.ETYPE;
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

	public Array<Asteroid> getMeteoriteWave(Vector3 mShipPosition) {
		mWaveNumber++;
		Array<Asteroid> ret = new Array<Asteroid>();
		int number = Par.INITIAL_WAVE_NUMBER;

		for (int i = 0; i < number + mWaveNumber/4; i++) {
			Asteroid temp = new Asteroid();
			temp.mSize = rand.nextInt(3)+1;
			temp.mEnergy = (int) (temp.mSize*temp.mSize);
			temp.mPosition = new Vector3(40 + (rand.nextInt(100)), // WAVE
					// LENGTH
					0, //
					rand.nextInt(30) - 20); // WAVE WITDH

			temp.mRotation.set(Vector3.Y,(float) rand.nextInt(360));
			temp.mRotationSpeed.set(Vector3.Y,(float) (rand.nextInt(10) - 5));
			temp.mType = ETYPE.METEORITE;
			temp.scheduleTask(TASK_TYPE.SPEED, new Vector3(
					-(rand.nextInt(17)+mWaveNumber/3), 0, -2 + rand.nextInt(4)),
					50 + 10 * rand.nextInt(10)+10, true, 0, 0);
			ret.add(temp);
		}
		
		for (int i = 0; i < mWaveNumber ; i++) {
			Asteroid temp = new Asteroid();
			temp.mSize = Math.min(rand.nextInt(mWaveNumber) ,9);
			temp.mEnergy = (int) (temp.mSize*temp.mSize*3);
			temp.mPosition = new Vector3(40 + (rand.nextInt(100)), // WAVE
					// LENGTH
					0, //
					rand.nextInt(30) - 20); // WAVE WITDH

			temp.mRotation.set(Vector3.Y,(float) rand.nextInt(360));
			temp.mRotationSpeed.set(Vector3.Y,(float) (rand.nextInt(3) - 3.1f));
			
			temp.mType = ETYPE.METEORITE;
			temp.scheduleTask(TASK_TYPE.SPEED, new Vector3(
					-(rand.nextInt(3) +1.7f), 0, -2 + rand.nextInt(4)),
					50 + 10 * rand.nextInt(10), true, 0, 0);
			ret.add(temp);
		}

		return ret;
	}

	public void GenerateRandomFormation(Asteroid.ETYPE type, int number) {
		for (int k = 0; k < number; k++) {
			// TODO use the Physic item tasks...
		}

	}
}
