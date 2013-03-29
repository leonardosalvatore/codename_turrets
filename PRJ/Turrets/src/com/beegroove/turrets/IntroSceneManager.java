package com.beegroove.turrets;

import java.util.Random;

import sun.security.provider.certpath.Vertex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.beegroove.turrets.HUD.Message;

public class IntroSceneManager extends OpenGLSceneManager{

	private StillModel moonMesh;
	private StillModel meteroriteMesh;
	private Texture backgroundTexture;

	public IntroSceneManager() {
		try {

			moonMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Moon.obj"));

			meteroriteMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Meteorite.obj"));

			backgroundTexture = new Texture(
					Gdx.files.internal("data/introbackground.png"));

		} catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}

	public void render(IntroSimulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		renderBackground();

		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_CULL_FACE);
		setProjectionAndCamera(simulation.mCameraMan);

		/* Shader selection */
		currentShader = lightTexShader;

		if (StateMachine.GetTimeInCurrentState() < Par.MOON_DURATION) {
			
			renderMoon(simulation.moon);
		}
		
		renderMeteorites(simulation.meteorites);

		gl.glDisable(GL20.GL_CULL_FACE);
		gl.glDisable(GL20.GL_DEPTH_TEST);

		renderSprite(HUD.Instance(), simulation);

	}



	Vector3 lightDirection = new Vector3(0, 1, 1);
	public void renderMoon(PhysicItem moon) {
		currentShader.begin();

		transform.set(mCamera.combined);
		transform.scale(Par.MOON_SCALE, Par.MOON_SCALE, Par.MOON_SCALE);

		transform.translate(moon.mPosition.x, moon.mPosition.y,
				moon.mPosition.z);

		transform.rotate(Vector3.Z, moon.mHeading);
		transform.rotate(Vector3.X, moon.mHeading);

		shaderSetup(transform, lightDirection, 1f, 1f, 1f);

		moonMesh.render(currentShader);

		currentShader.end();
	}

	private void renderMeteorites(Array<Enemy> meteorites) {
		currentShader.begin();

		for (Enemy en : meteorites) {
			transform.set(mCamera.combined);
			transform.scale(Par.MOON_METEORITES_SCALE,
					Par.MOON_METEORITES_SCALE, Par.MOON_METEORITES_SCALE);
			transform.translate(en.mPosition.x, en.mPosition.y, en.mPosition.z);

			// transform.rotate(Vector3.Z, moon.mHeading);
			// transform.rotate(Vector3.X, moon.mHeading);
			shaderSetup(transform, lightDirection, 1f, 1f, 1f);

			meteroriteMesh.render(currentShader);
		}

		currentShader.end();

	}
	
	

	// TODO Move to an external class and generate scenery
	private int backgroundscroolingX = 0;
	private Color backgroundColor = Color.WHITE;
	private void renderBackground() {

		if (backgroundscroolingX >= Par.VIEWPORT_MAX_X)
			backgroundscroolingX = 0;

		viewMatrix.setToOrtho2D(0, 0, Par.VIEWPORT_MAX_X, Par.VIEWPORT_MAX_Y);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.setColor(backgroundColor);

		spriteBatch.draw(backgroundTexture, Par.VIEWPORT_MAX_X
				- backgroundscroolingX, 0);
		spriteBatch.draw(backgroundTexture, -backgroundscroolingX, 0);

		spriteBatch.end();
	}

	private final Message title = HUD.Instance().GetMainTitleMessage();
	private final Message intro = HUD.Instance().GetIntroMessage();
	private void renderSprite(HUD hud, Simulation simulation) {
		viewMatrix.setToOrtho2D(0, 0, Par.VIEWPORT_MAX_X, Par.VIEWPORT_MAX_Y);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();

		spriteBatch.enableBlending();
		
		
		fontMainTitle.setColor(Color.WHITE);
		fontLarge.setColor(Color.WHITE);
		fontMainTitle.draw(spriteBatch, title.msg, title.mPosition.x, title.mPosition.y);
		fontLarge.drawMultiLine(spriteBatch, intro.msg, intro.mPosition.x, intro.mPosition.y);
		fontLarge.draw(spriteBatch,Par.SETTINGS_AUDIO_NAME+ (Par.SETTINGS_AUDIO?": ON":": OFF"),Par.SETTINGS_AUDIO_X,Par.SETTINGS_AUDIO_Y);
		fontLarge.draw(spriteBatch,Par.SETTINGS_VIBRA_NAME+ (Par.SETTINGS_VIBRA?": ON":": OFF"),Par.SETTINGS_VIBRA_X,Par.SETTINGS_VIBRA_Y);
		fontLarge.draw(spriteBatch,Par.SETTINGS_FX_NAME+ (Par.SETTINGS_FX?": ON":": OFF"),Par.SETTINGS_FX_X,Par.SETTINGS_FX_Y);
		fontMainTitle.draw(spriteBatch, "START", Par.START_X, Par.START_Y);
		
		
		if (Par.HUD_DEBUG) {
			fontStandard.setColor(Color.WHITE);
			fontStandard
					.drawMultiLine(
							spriteBatch,
							String.format(
									"Cam.Pos:%2.2f %2.2f %2.2f\nCam.Dir:%2.2f %2.2f %2.2f\nCam.Angle:%2.2f\nCam.FOV:%s",
									simulation.mCameraMan.mPosition.x,
									simulation.mCameraMan.mPosition.y,
									simulation.mCameraMan.mPosition.z,
									simulation.mCameraMan.mDirection.x,
									simulation.mCameraMan.mDirection.y,
									simulation.mCameraMan.mDirection.z,
									simulation.mCameraMan.mAngle,
									simulation.mCameraMan.FOV), 750, 250);
		}

		spriteBatch.end();
	}


	public void dispose() {
		super.dispose();
		
		backgroundTexture.dispose();
		moonMesh.dispose();
	}

}
