package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.beegroove.turrets.HUD.Message;

public class GameOverSceneManager extends OpenGLSceneManager {

	
	private final Message title = HUD.Instance().GetMainGameOverMessage();
	private Texture backgroundTexture;
	
	public GameOverSceneManager()
	{
		backgroundTexture = new Texture(
				Gdx.files.internal("data/gameoverbackground.png"));
	}
	
	public void render(GameOverSimulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		viewMatrix.setToOrtho2D(0, 0, Par.VIEWPORT_MAX_X, Par.VIEWPORT_MAX_Y);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();

		renderBackground();
		if(HighscoreAndStats.isHighScore())
		{
			fontMainTitle.draw(spriteBatch, "HIGHSCORE!", title.mPosition.x, title.mPosition.y);
		}
		else
		{
			fontMainTitle.draw(spriteBatch, title.msg, title.mPosition.x, title.mPosition.y);
		}
		
		fontLarge.drawMultiLine(spriteBatch, String.format(Par.GAMEOVER_MESSAGE,
				HighscoreAndStats.LoadHighScore(),
				HighscoreAndStats.sScore,HighscoreAndStats.sWave,HighscoreAndStats.sNumberHits,
				HighscoreAndStats.sNumberOfShoot,HighscoreAndStats.sAsteroidsDestroyed,HighscoreAndStats.sAsteroidsLost)
				, 100, Par.VIEWPORT_MAX_Y-300);
		
		spriteBatch.end();
	}
	
	// TODO Move to an external class and generate scenery
	private int backgroundscroolingX = 0;
	private Color backgroundColor = Color.WHITE;
	private void renderBackground() {

		// backgroundscroolingX += 1;
		if (backgroundscroolingX >= Par.VIEWPORT_MAX_X)
			backgroundscroolingX = 0;

		viewMatrix.setToOrtho2D(0, 0, Par.VIEWPORT_MAX_X, Par.VIEWPORT_MAX_Y);
		spriteBatch.setProjectionMatrix(viewMatrix);

		spriteBatch.setColor(backgroundColor);

		spriteBatch.draw(backgroundTexture, Par.VIEWPORT_MAX_X
				- backgroundscroolingX, 0);
		spriteBatch.draw(backgroundTexture, -backgroundscroolingX, 0);

	}
}
