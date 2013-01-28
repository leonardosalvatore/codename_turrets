package com.beegroove.turrets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;


public class SceneManager {
	
	private StillModel shipMesh;
	private Texture shipTexture;
	private StillModel turretMesh;
	private Texture turretTexture;
	private Texture backgroundTexture;

	
	private SpriteBatch spriteBatch;
	
	/** view and transform matrix for text rendering and transforming 3D objects **/
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transform = new Matrix4();
	private final Matrix4 normal = new Matrix4();
	private final Matrix3 normal3 = new Matrix3();

	/** perspective camera **/
	private PerspectiveCamera camera;

	/** shaders **/
	ShaderProgram texShader;
	ShaderProgram colorShader;
	ShaderProgram lightTexShader;

	public SceneManager () {
		try {
			spriteBatch = new SpriteBatch();

			FileHandle fh_vs = Gdx.files.internal("data/shaders/tex-vs.glsl"); 
			FileHandle fh_fs = Gdx.files.internal("data/shaders/tex-fs.glsl"); 

			texShader = new ShaderProgram(fh_vs,fh_fs);
			
			fh_vs = Gdx.files.internal("data/shaders/color-vs.glsl");
			fh_fs = Gdx.files.internal("data/shaders/color-fs.glsl");
			
			colorShader = new ShaderProgram(fh_vs,fh_fs);

			fh_vs = Gdx.files.internal("data/shaders/light-tex-vs.glsl");
			fh_fs = Gdx.files.internal("data/shaders/light-tex-fs.glsl");
			
			lightTexShader = new ShaderProgram(fh_vs,fh_fs);

			if (!texShader.isCompiled()) throw new GdxRuntimeException("Couldn't compile tex shader");
			if (!colorShader.isCompiled()) throw new GdxRuntimeException("Couldn't compile color shader");
			if (!lightTexShader.isCompiled()) throw new GdxRuntimeException("Couldn't compile light/tex shader");

			shipMesh = ModelLoaderRegistry.loadStillModel(Gdx.files.internal("data/Starship0.obj"));
			turretMesh = ModelLoaderRegistry.loadStillModel(Gdx.files.internal("data/Turret0.obj"));
			
			shipTexture = new Texture(Gdx.files.internal("data/ship.png"), Format.RGB565, true);
			shipTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			turretTexture = new Texture(Gdx.files.internal("data/uv_map_reference.png"), Format.RGB565, true);
			turretTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			backgroundTexture = new Texture(Gdx.files.internal("data/background.png"));
			
			camera = new PerspectiveCamera(Parameters.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		} catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	private void setProjectionAndCamera (Vector3 cameraposition,float FOV) {
		camera.position.set(cameraposition);
		camera.fieldOfView = FOV;
		camera.direction.set(Parameters.CAMERA_DIRECTION_X, Parameters.CAMERA_DIRECTION_Y, Parameters.CAMERA_DIRECTION_Z).sub(camera.position).nor();
		camera.update();
	}

	public void render (Simulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		renderBackground();
		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_CULL_FACE);
		
		setProjectionAndCamera(simulation.camera_position,simulation.FOV);

		for (Turret turret : simulation.starship.turrets) {
			renderTurret(turret);
		}

		renderShip(simulation.starship);
		gl.glDisable(GL20.GL_CULL_FACE);
		gl.glDisable(GL20.GL_DEPTH_TEST);

		spriteBatch.setProjectionMatrix(viewMatrix);
		//spriteBatch.begin();

		spriteBatch.enableBlending();
		//font.draw(spriteBatch, status, 0, 320);
		//spriteBatch.end();

	}

	

	private void renderBackground () {
		viewMatrix.setToOrtho2D(0, 0, 1280, 800);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(backgroundTexture,0,0);
		spriteBatch.end();
	}

	private void renderShip (StarShip ship) {

		shipTexture.bind();
		lightTexShader.begin();
		lightTexShader.setUniformi("u_diffuse", 0);
		transform.set(camera.combined);
		transform.translate(ship.position.x, ship.position.y, ship.position.z);
		//transform.rotate(0, 0, 1, 45 * (-Gdx.input.getAccelerometerY() / 5));
		//transform.rotate(0, 1, 0, 180);
		lightTexShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		lightTexShader.setUniformMatrix("u_normal", normal3);
		shipMesh.render(lightTexShader);
		lightTexShader.end();
	}

	private void renderTurret (Turret turret) {

		turretTexture.bind();
		lightTexShader.begin();
		lightTexShader.setUniformi("u_diffuse", 0);
		transform.set(camera.combined);
		transform.translate(turret.position.x, turret.position.y, turret.position.z-Parameters.TURRET_HALF_DIAMETER);
		transform.rotate(0,1,0,turret.y_angle);
		transform.translate(0,0,+Parameters.TURRET_HALF_DIAMETER);
		lightTexShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		lightTexShader.setUniformMatrix("u_normal", normal3);
		turretMesh.render(lightTexShader);
		lightTexShader.end();
	}

	public void dispose () {
		spriteBatch.dispose();
		shipTexture.dispose();
		shipMesh.dispose();
	}
}
