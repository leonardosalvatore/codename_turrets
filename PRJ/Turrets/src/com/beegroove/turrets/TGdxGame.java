package com.beegroove.turrets;

import java.util.Hashtable;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.beegroove.turrets.StateMachine.STATE;

public class TGdxGame extends Game {
	
	
	public TGdxGame() {
		StateMachine.SetNextState(STATE.INTRO);
	}
	
	@Override
	public void create() {
		setScreen(new IntroScreen());
	}
	
	@Override
	public GenericScreen getScreen () {
		return (GenericScreen)super.getScreen();
	}
	
	@Override 
	public void render()
	{
		GenericScreen currentScreen = getScreen();
		
		switch (StateMachine.GetCurrentState()) {
		case INTRO:
			
			if(StateMachine.GetTimeInCurrentState() > Par.STATE_INTRO_DURATION)
			{
				StateMachine.SetNextState(STATE.PLAY);
				setScreen(new PlayScreen());
			}
			break;
		case PLAY:
			break;
		case PAUSE:
			
			break;
		case GAMEOVER:
			
			break;
		}

		if(currentScreen != null)
		{
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}
	}
}
