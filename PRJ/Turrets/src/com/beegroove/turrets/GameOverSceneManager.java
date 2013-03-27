package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.beegroove.turrets.HUD.Message;

public class GameOverSceneManager extends OpenGLSceneManager {

	
	private final Message title = HUD.Instance().GetMainGameOverMessage();
	
	public void render(GameOverSimulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		viewMatrix.setToOrtho2D(0, 0, Par.VIEWPORT_MAX_X, Par.VIEWPORT_MAX_Y);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();

		fontMainTitle.draw(spriteBatch, title.msg, title.mPosition.x, title.mPosition.y);
		fontLarge.drawMultiLine(spriteBatch, String.format(Par.GAMEOVER_MESSAGE,
				HighscoreAndStats.sScore,HighscoreAndStats.sWave,HighscoreAndStats.sNumberHits,
				HighscoreAndStats.sNumberOfShoot,HighscoreAndStats.sAsteroidsDestroyed,HighscoreAndStats.sAsteroidsLost)
				, 100, Par.VIEWPORT_MAX_Y-300);
		
		
		spriteBatch.end();
	}

}
