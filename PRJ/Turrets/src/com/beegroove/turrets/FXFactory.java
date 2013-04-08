package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.math.Vector3;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;

public class FXFactory {
	private static Random rand;
	static {
		rand = new Random(System.currentTimeMillis());
	}

	static PhysicItem shootStart(PhysicItem shoot) {
		PhysicItem tmp = new PhysicItem(
				(PhysicItem) shoot);
		tmp.mPosition.x -= tmp.mSize;	
		tmp.mSize = 0.1f;
		
		tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(0.03f, 0, 0), 25,
				false, 0, 0);
		tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(0.03f, 0, 0), 25,
				false, 0, 0);
		tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false,
				0, 0);
		return tmp;
	}

	static PhysicItem asteroidDamaged(PhysicItem shoot,float par) {
		PhysicItem tmp = new PhysicItem(
				(PhysicItem) shoot);
		tmp.mPosition.x -= tmp.mSize;	
		tmp.mSize = 0.1f;
		
		tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(0.03f, 0, 0), 25,
				false, 0, 0);
		tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(0.03f, 0, 0), 25,
				false, 0, 0);
		tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false,
				0, 0);
		return tmp;
	}

	static PhysicItem asteroidDestroyed(PhysicItem shoot, float par) {
		PhysicItem tmp = new PhysicItem(
				(PhysicItem) shoot);
		tmp.mScreenPosition.x += (rand.nextFloat() - .5f)
				* par * 40;
		tmp.mScreenPosition.y += (rand.nextFloat() - .5f)
				* par * 40;
		tmp.mSize = 0.1f;
		tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(0.1f, 0, 0),
				25 + (int) ((rand.nextFloat() - .4f) * par * 40), false, 0, 0);
		tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(0.05f, 0, 0),
				25 + (int) ((rand.nextFloat() - .4f) * par * 70), false, 0, 0);
		tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false,
				0, 0);
		return tmp;
	}

}
