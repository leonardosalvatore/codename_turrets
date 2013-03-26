package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class OpenGLSceneManager {

	private Random mRandom = new Random(System.currentTimeMillis());
	protected SpriteBatch spriteBatch;
	
	protected BitmapFont fontStandard;
	protected BitmapFont fontLarge;
	protected BitmapFont fontMainTitle;
	
	protected final Matrix4 viewMatrix = new Matrix4();
	protected final Matrix4 transform = new Matrix4();
	protected final Matrix4 normal = new Matrix4();
	protected final Matrix3 normal3 = new Matrix3();
	
	protected PerspectiveCamera mCamera;
	protected StillModel cubeMesh;
	protected ShaderProgram lightTexShader;
	protected ShaderProgram currentShader;

	public OpenGLSceneManager()
	{
		try {
		Par.VIEWPORT_MAX_X = Gdx.graphics.getWidth();
		Par.VIEWPORT_MAX_Y = Gdx.graphics.getHeight();

		spriteBatch = new SpriteBatch();
		

		FileHandle fh_vs = Gdx.files.internal("data/shaders/light-tex-vs.glsl");
		FileHandle fh_fs = Gdx.files.internal("data/shaders/light-tex-fs.glsl");
		
		lightTexShader = new ShaderProgram(fh_vs, fh_fs);

		if (!lightTexShader.isCompiled())
			throw new GdxRuntimeException(
					"Couldn't compile light/tex shader");
		
		FileHandle fontFile = Gdx.files.internal("data/font.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				fontFile);
		FreeTypeBitmapFontData fontData = generator.generateData(
				Par.STANDARD_FONT_SIZE,
				FreeTypeFontGenerator.DEFAULT_CHARS, false);

		fontStandard = new BitmapFont(fontData,
				fontData.getTextureRegion(), false);
		fontData = generator.generateData(Par.LARGE_FONT_SIZE,
				FreeTypeFontGenerator.DEFAULT_CHARS, false);
		fontLarge = new BitmapFont(fontData, fontData.getTextureRegion(),
				false);
		fontData = generator.generateData(Par.MAIN_TITLE_FONT_SIZE,
				FreeTypeFontGenerator.DEFAULT_CHARS, false);
		fontMainTitle= new BitmapFont(fontData, fontData.getTextureRegion(),
				false);
		generator.dispose();

		mCamera = new PerspectiveCamera(Par.CAMERA_FOV,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		} catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	protected void setProjectionAndCamera(Cameraman cameraMan) {
		mCamera.position.set(cameraMan.mPosition);
		mCamera.fieldOfView = cameraMan.FOV;
		mCamera.rotate(cameraMan.mDirection, cameraMan.mAngle);
		mCamera.direction.set(cameraMan.mDirection).sub(cameraMan.mPosition)
				.nor();
		mCamera.update();
	}

	protected void shaderSetup(Matrix4 transf,Vector3 lightDir,float r,float g,float b)
	{
		currentShader.setUniformMatrix("u_projView", transf);
		currentShader.setUniformf("u_lightDir", lightDir);
		currentShader.setUniformf("u_lightColor", new Vector3(r,g,b));
		
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);	
	}
	
	protected Ray unproject(int x, int y) {
		return mCamera.getPickRay(x, y).cpy();
	}
	
	protected void dispose()
	{
		fontLarge.dispose();
		fontStandard.dispose();
		fontMainTitle.dispose();
		spriteBatch.dispose();
		lightTexShader.dispose();
		
	}
	

}
