package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;
import com.beegroove.turrets.StateMachine.STATE;

public class IntroSimulation extends Simulation {
	public PhysicItem moon;
	public Array<Enemy> meteorites = new Array<Enemy>();

	public IntroSimulation() {
		super();

		HighscoreAndStats.Clear();
		
		moon = new PhysicItem();

		moon.mPosition.set(Par.MOON_POSITION);

		for (int k = 0; k <= Par.MOON_METEORITES_NUMBER; k++) {
			Enemy tmp = new Enemy();

			tmp.mPosition.set(Par.MOON_POSITION);

			tmp.scheduleTask(TASK_TYPE.SLEEP, Vector3.Zero,
					(int) Par.MOON_METEORITES_SLEEP, false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.SPEED,
					new Vector3((rand.nextFloat() - 0.5f)*Par.MOON_METEORITES_SPEED,
							(rand.nextFloat() - 0.5f)*Par.MOON_METEORITES_SPEED, 
							(rand.nextFloat() - 0.5f)*Par.MOON_METEORITES_SPEED),
					(int) Par.MOON_EXPLOSION, false, 0, 0);
			meteorites.add(tmp);
		}
	}

	public void update(float delta) {

		if(StateMachine.GetTimeInCurrentState()>Par.INTRO_MIN_DURATION)
		{
			if(Gdx.input.isTouched())
			{
				StateMachine.SetNextState(STATE.PLAY,new PlayScreen(Par.Level_1));
			}
		}
		
		HUD.Instance().Update(delta);
		mCameraMan.Update(delta);
		moon.Update(delta);
		moon.mHeading += Par.MOON_ROTATION_SPEED;


		for (Enemy en : meteorites) {
			en.Update(delta);
		}

	}
}
