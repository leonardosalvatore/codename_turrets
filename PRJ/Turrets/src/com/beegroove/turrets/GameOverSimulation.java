package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;
import com.beegroove.turrets.StateMachine.STATE;

public class GameOverSimulation extends Simulation {

	public void update(float delta) {

		if(StateMachine.GetTimeInCurrentState()>Par.GAMEOVER_MIN_DURATION)
		{
			if(Gdx.input.isTouched())
			{
				HighscoreAndStats.SaveIfIs();
				HighscoreAndStats.Clear();
				StateMachine.SetNextState(STATE.PLAY,new PlayScreen(Par.Level_1));
			}
		}

	}
	
	
//	public boolean gameAnimationOverLauncher = false;
//	public void launchGameOverAnimation()
//	{
//		if(gameAnimationOverLauncher)
//		{
//			return;
//		}
//		gameAnimationOverLauncher=true;
//		
//		starship.Fire(false, false);
//		starship.SetDestinationSpeed(new Vector3(0, 15, 0));
//		
//		for (Enemy en : enemies) {
//			en.SetDestinationSpeed(new Vector3(0, 15, 0));
//		}
//		
//		for(int k=0;k<=WaveFactory.mWaveNumber*5;k++)
//		{
//			PhysicItem tmp = new PhysicItem();
//			tmp.mScreenPosition.x = rand.nextInt((int) Par.VIEWPORT_MAX_X);
//			tmp.mScreenPosition.y = rand.nextInt((int) Par.VIEWPORT_MAX_Y);
//			tmp.mSize=0.1f;
//			tmp.scheduleTask(TASK_TYPE.SLEEP, new Vector3(0.00f, 0, 0), 150, false, 0, 0);
//			tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(rand.nextFloat()/3+0.1f, 0, 0), 50, false, 0, 0);
//			tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(rand.nextFloat()/3+0.1f, 0, 0), 400, false, 0, 0);
//			tmp.scheduleTask(TASK_TYPE.DELETE, new Vector3(0.1f, 0, 0), 0, false, 0, 0);
//			explosions.add(tmp);
//		}
//	}


}
