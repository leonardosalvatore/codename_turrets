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
	
}
