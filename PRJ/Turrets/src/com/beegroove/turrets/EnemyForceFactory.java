package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.Enemy.ETYPE;

public class EnemyForceFactory {

	private static EnemyForceFactory instance = null;

	protected EnemyForceFactory() {
		rand = new Random(System.currentTimeMillis());
	}

	public static EnemyForceFactory Instance() {
		if (instance == null) {
			instance = new EnemyForceFactory();
		}
		return instance;
	}


	class Task {
		Vector2 speed;
		Vector2 position;
		int duration;
		boolean wait;
		int gotoBack;
		int repeat;
		ETYPE type;
		boolean launched;
	}

	private Array<Task> tasks = new Array<EnemyForceFactory.Task>();
	private int current = 0;
	private long start_time;
	private int WaveNumber;
	private  Random rand;

	public void ScheduleTask(Vector2 s, Vector2 p, int mill, boolean wait,
			int gotoBack, int repeat, ETYPE type) {
		Task t = new Task();
		t.speed = s;
		t.position = p;
		t.duration = mill;
		t.wait = wait;
		t.gotoBack = gotoBack;
		t.repeat = repeat;
		t.type = type;
		tasks.add(t);
	}

	public void LaunchTask() {
		start_time = System.currentTimeMillis();
	}
	

	public Task getNext() {
		Task ret = tasks.get(current);

		if (ret != null) {
			if (start_time + ret.duration < System.currentTimeMillis()) {
				if (!ret.launched) {
					ret = tasks.get(current);
					ret.launched = true;
					current++;
				}
			}
		}

		return ret;
	}
	
	public Array<Enemy> getMeteoriteWave() {
		WaveNumber++;
		Array<Enemy> ret = new Array<Enemy>();
		HUD.Instance().NewMessageRoller(Par.MSG_NEW_ENEMY_WAVE);
		int number = Par.INITIAL_WAVE_NUMBER + WaveNumber*2;
		for (int i = 0; i < number; i++) {
			Enemy temp = new Enemy();
			temp.mPosition = new Vector3(40 + (rand.nextInt(30)) , //WAVE LENGTH
					0, //
					rand.nextInt(30) - 20); // WAVE WITDH
			temp.mSpeed = new Vector3(-(rand.nextInt(10+ WaveNumber) + 3), 0, 0);
			temp.mYAangle = (float) rand.nextInt(360);
			temp.mType = ETYPE.METEORITE;
			temp.mYAngleSpeed = (float) rand.nextInt(10)-5;
			temp.mSize = rand.nextInt((int) (2+WaveNumber*.5));
			temp.mEnergy = (int) (temp.mSize * 3);
			ret.add(temp);
		}
		
		return ret;
	}
	
	public void GenerateRandomFormation(Enemy.ETYPE type, int number)
	{
		for (int k=0;k<number;k++)
		{
			
		}
	
	}
}
