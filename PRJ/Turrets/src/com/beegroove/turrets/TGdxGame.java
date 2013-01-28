package com.beegroove.turrets;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TGdxGame extends Game {

	
	@Override
	public GenericScreen getScreen () {
		return (GenericScreen)super.getScreen();
	}
	
	@Override 
	public void render()
	{
		GenericScreen currentScreen = getScreen();
		if(currentScreen == null)
		{
			setScreen(new PlayScreen());			
		}
		currentScreen.render(Gdx.graphics.getDeltaTime());
		
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
