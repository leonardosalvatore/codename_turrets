package com.beegroove.turrets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.beegroove.turrets.StateMachine.STATE;

public class TGdxGame extends Game {
	
	public TGdxGame() {
	}
	
	@Override
	public void create() {
		StateMachine.SetGame(this);
		StateMachine.SetNextState(STATE.INTRO, new IntroScreen());
		
	}
	
	@Override
	public GenericScreen getScreen () {
		return (GenericScreen)super.getScreen();
	}
	
	
	int mLevel=0;
	@Override 
	public void render()
	{
		GenericScreen currentScreen = getScreen();
		
		switch (StateMachine.GetCurrentState()) {
		case INTRO:
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
