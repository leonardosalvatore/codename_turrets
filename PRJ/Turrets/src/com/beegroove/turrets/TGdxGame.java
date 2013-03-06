package com.beegroove.turrets;

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
			if(currentScreen != null)
			{
				currentScreen.render(Gdx.graphics.getDeltaTime());
			}
			break;
		case PAUSE:
			
			break;
		case GAMEOVER:
			
			break;
		}


	}
	
	@Override
	public void create() {
		setScreen(new PlayScreen());
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp (int keycode) {
				if (keycode == Keys.ENTER && Gdx.app.getType() == ApplicationType.WebGL) {
					if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
				}
				return true;
			}
		});
		
		
		
	}
	

}
