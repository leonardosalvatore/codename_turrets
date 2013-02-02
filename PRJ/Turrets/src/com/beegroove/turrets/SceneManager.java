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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.beegroove.turrets.HUD.Message;
import com.sun.xml.internal.bind.api.impl.NameConverter.Standard;

public class SceneManager {

	private StillModel spaceshipBasicMesh;
	private StillModel spaceshipStandardMesh;
	private StillModel shootMesh;
	private StillModel singleSmallTurretMesh;
	private StillModel doubleSmallTurretMesh;
	private StillModel meteroriteMesh;
	private StillModel cubeMesh;
	private Texture shipTexture;
	private Texture turretTexture;
	private Texture backgroundTexture;
	private BitmapFont fontStandard;

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
	ShaderProgram toonShader;

	ShaderProgram currentShader;
	
	
	public SceneManager() {
		try {
			spriteBatch = new SpriteBatch();

			FileHandle fh_vs = Gdx.files.internal("data/shaders/tex-vs.glsl");
			FileHandle fh_fs = Gdx.files.internal("data/shaders/tex-fs.glsl");

			texShader = new ShaderProgram(fh_vs, fh_fs);

			fh_vs = Gdx.files.internal("data/shaders/color-vs.glsl");
			fh_fs = Gdx.files.internal("data/shaders/color-fs.glsl");

			colorShader = new ShaderProgram(fh_vs, fh_fs);

			fh_vs = Gdx.files.internal("data/shaders/light-tex-vs.glsl");
			fh_fs = Gdx.files.internal("data/shaders/light-tex-fs.glsl");

			lightTexShader = new ShaderProgram(fh_vs, fh_fs);

			//fh_vs = Gdx.files.internal("data/shaders/toon-vs.glsl");
			//fh_fs = Gdx.files.internal("data/shaders/toon-fs.glsl");

			//toonShader = new ShaderProgram(fh_vs, fh_fs);

			if (!texShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile tex shader");
			if (!colorShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile color shader");
			if (!lightTexShader.isCompiled())
				throw new GdxRuntimeException(
						"Couldn't compile light/tex shader");
			//if (!toonShader.isCompiled())
			//	throw new GdxRuntimeException("Couldn't compile toon shader");

			spaceshipBasicMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Starship0.obj"));
			spaceshipStandardMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/SpaceShip1.obj"));singleSmallTurretMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Turret0.obj"));
			doubleSmallTurretMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/TurretsDoubleSmall.obj"));
			
			shootMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Shoot0.obj"));
			
			shipTexture = new Texture(Gdx.files.internal("data/ship.png"),
					Format.RGB565, true);
			shipTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			turretTexture = new Texture(
					Gdx.files.internal("data/uv_map_reference.png"),
					Format.RGB565, true);
			turretTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			backgroundTexture = new Texture(
					Gdx.files.internal("data/background.png"));
			
			meteroriteMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Meteorite.obj"));
			cubeMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Cube.obj"));
			
			FileHandle fontFile = Gdx.files.internal("data/font.ttf");
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
			FreeTypeBitmapFontData fontData = generator.generateData(Parameters.STANDARD_FONT_SIZE, FreeTypeFontGenerator.DEFAULT_CHARS, false);		
			generator.dispose();
			fontStandard = new BitmapFont(fontData, fontData.getTextureRegion(), false);

			camera = new PerspectiveCamera(Parameters.CAMERA_FOV,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}

	private void setProjectionAndCamera(Vector3 cameraposition, float FOV) {
		camera.position.set(cameraposition);
		camera.fieldOfView = FOV;
		camera.direction
				.set(Parameters.CAMERA_DIRECTION_X,
						Parameters.CAMERA_DIRECTION_Y,
						Parameters.CAMERA_DIRECTION_Z).sub(camera.position)
				.nor();
		camera.update();
	}

	public void render(Simulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		renderBackground();
		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_CULL_FACE);

		setProjectionAndCamera(simulation.camera_position, simulation.FOV);

		/* Shader selection */
		currentShader = lightTexShader;

		for (Turret turret : simulation.starship.turrets) {
			renderTurret(turret);
			for (Shoot shoot : turret.shoots) {
				renderShoot(shoot);
			}
		}
		
		renderShip(simulation.starship);
		
		
		for(Enemy k : simulation.enemies)
		{
			renderEnemy(k);
		}

		
		gl.glDisable(GL20.GL_CULL_FACE);
		gl.glDisable(GL20.GL_DEPTH_TEST);

		renderHUD(HUD.Instance(),simulation);

	}

	private void renderShip(StarShip ship) {
		shipTexture.bind();
		currentShader.begin();
		currentShader.setUniformi("u_diffuse", 0);
		transform.set(camera.combined);
		transform.translate(ship.position.x, ship.position.y, ship.position.z);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);
		switch(ship.type)
		{
		case ADVANCED:
			break;
		case BASIC:
			spaceshipBasicMesh.render(currentShader);
			break;
		case PRO:
			break;
		case STANDARD:
			spaceshipStandardMesh.render(currentShader);
			break;
		default:
			break;
			
		}
		currentShader.end();
	}

	private void renderTurret(Turret turret) {
		turretTexture.bind();
		currentShader.begin();
		currentShader.setUniformi("u_diffuse", 0);
		transform.set(camera.combined);
		transform.translate(turret.position.x, turret.position.y,
				turret.position.z - Parameters.TURRET_SINGLE_HALF_DIAMETER);
		transform.rotate(0, 1, 0, turret.y_angle);
		transform.translate(0, 0, +Parameters.TURRET_SINGLE_HALF_DIAMETER);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);
		
		switch(turret.type)
		{
		case DOUBLE_CANNON:
			break;
		case DOUBLE_LARGE:
			break;
		case DOUBLE_SMALL:
			doubleSmallTurretMesh.render(currentShader);
			break;
		case SINGLE_LARGE:
			break;
		case SINGLE_MEDIUM:
			break;
		case SINGLE_SMALL:
			singleSmallTurretMesh.render(currentShader);	break;
		case TRIPLE_LARGE:
			break;
		default:
			break;
		}
		
		currentShader.end();
	}
	
	private void renderShoot(Shoot shoot) {
		currentShader.begin();
		currentShader.setUniformi("u_diffuse", 0);
		transform.set(camera.combined);
		transform.translate(shoot.position.x, shoot.position.y,
				shoot.position.z);
		transform.rotate(0, 1, 0, shoot.y_angle);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);
		shootMesh.render(currentShader);
		currentShader.end();
	}
	
	private void renderEnemy(Enemy enemy) {
		currentShader.begin();
		currentShader.setUniformi("u_diffuse", 0);
		transform.set(camera.combined);
		transform.translate(enemy.position.x, enemy.position.y,
				enemy.position.z);
		transform.rotate(0, 1, 0, enemy.y_angle);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);
		switch ( enemy.type)
		{
		case BOMBER:
			break;
		case BONUS:
			break;
		case DESTROYER:
			break;
		case FIGHTER:
			break;
		case INTERCEPTOR:
			break;
		case METEORITE:
			meteroriteMesh.render(currentShader);
			break;
		case SCOUT:
			break;
		case TRANSPORT:
			break;
		default:
			break;
		}
		currentShader.end();
	}

	private int backgroundscrooling = 0;
	private void renderBackground() {
		
		backgroundscrooling +=8;
		if (backgroundscrooling  == 1280)
			backgroundscrooling  = 0;

		viewMatrix.setToOrtho2D(0, 0, 1280, 800);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(backgroundTexture, 1280 - backgroundscrooling , 0);
		spriteBatch.draw(backgroundTexture, -backgroundscrooling , 0);

		spriteBatch.end();
	}
	
	private void renderHUD(HUD hud,Simulation simulation) {
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.enableBlending();
		for(Message m :hud.GetMessage())
		{
			fontStandard.setColor(Color.BLACK);
			fontStandard.draw(spriteBatch, m.msg, m.position.x-1, m.position.y-1);
			fontStandard.setColor(Color.RED);
			fontStandard.draw(spriteBatch, m.msg, m.position.x, m.position.y);
		}
		for(Message m :hud.GetMessageRoller())
		{
			fontStandard.setColor(Color.BLACK);
			fontStandard.draw(spriteBatch, m.msg, m.position.x-1, m.position.y-1);
			fontStandard.setColor(Color.WHITE);
			fontStandard.draw(spriteBatch, m.msg, m.position.x, m.position.y);
		}
		
		Message statusmsg = hud.GetStatusBar(simulation.Score,simulation.Missed,simulation.starship.Energy);
		fontStandard.setColor(Color.BLACK);
		fontStandard.draw(spriteBatch, statusmsg.msg, statusmsg.position.x-1, statusmsg.position.y-1);
		fontStandard.setColor(Color.WHITE);
		fontStandard.draw(spriteBatch, statusmsg.msg, statusmsg.position.x, statusmsg.position.y);

		spriteBatch.end();
	}

	public void dispose() {
		spriteBatch.dispose();
		shipTexture.dispose();
		spaceshipBasicMesh.dispose();
		spaceshipStandardMesh.dispose();
		shootMesh.dispose();
		//TODO others...
	}

	public Ray unproject(int x, int y) {
		return camera.getPickRay(x, y).cpy();
	}
}
