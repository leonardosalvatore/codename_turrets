package com.beegroove.turrets;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.beegroove.turrets.HUD.Message;

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
	private Random mRandom = new Random(System.currentTimeMillis());

	private SpriteBatch spriteBatch;

	/** view and transform matrix for text rendering and transforming 3D objects **/
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transform = new Matrix4();
	private final Matrix4 normal = new Matrix4();
	private final Matrix3 normal3 = new Matrix3();

	/** perspective camera **/
	private PerspectiveCamera mCamera;

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

			// fh_vs = Gdx.files.internal("data/shaders/toon-vs.glsl");
			// fh_fs = Gdx.files.internal("data/shaders/toon-fs.glsl");

			// toonShader = new ShaderProgram(fh_vs, fh_fs);

			if (!texShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile tex shader");
			if (!colorShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile color shader");
			if (!lightTexShader.isCompiled())
				throw new GdxRuntimeException(
						"Couldn't compile light/tex shader");
			// if (!toonShader.isCompiled())
			// throw new GdxRuntimeException("Couldn't compile toon shader");

			spaceshipBasicMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Starship0.obj"));
			spaceshipStandardMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files.internal("data/SpaceShip1.obj"));
			singleSmallTurretMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files.internal("data/Turret0.obj"));
			doubleSmallTurretMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files
							.internal("data/TurretsDoubleSmall.obj"));

			shootMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Shoot0.obj"));

			shipTexture = new Texture(Gdx.files.internal("data/ship.png"),
					Format.RGBA4444, true);
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
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
					fontFile);
			FreeTypeBitmapFontData fontData = generator.generateData(
					Par.STANDARD_FONT_SIZE,
					FreeTypeFontGenerator.DEFAULT_CHARS, false);
			generator.dispose();
			fontStandard = new BitmapFont(fontData,
					fontData.getTextureRegion(), false);

			mCamera = new PerspectiveCamera(Par.CAMERA_FOV,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		} catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}

	private void setProjectionAndCamera(Cameraman cameraMan) {
		mCamera.position.set(cameraMan.mPosition);
		mCamera.fieldOfView = cameraMan.FOV;
		mCamera.rotate(cameraMan.mDirection, cameraMan.mAngle);
		mCamera.direction.set(cameraMan.mDirection).sub(cameraMan.mPosition).nor();
		mCamera.update();
	}

	public void render(Simulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		renderBackground(simulation.starship.mSpeed.x);
		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_CULL_FACE);
		//gl.glEnable(GL20.GL_TEXTURE_2D);

		setProjectionAndCamera(simulation.mCameraMan);

		/* Shader selection */
		currentShader = lightTexShader;

		for (Turret turret : simulation.starship.turrets) {
			renderTurret(turret);
			for (Shoot shoot : turret.shoots) {
				renderShoot(shoot);
			}
		}

		renderShip(simulation.starship);

		for (Enemy k : simulation.enemies) {
			renderEnemy(k);
		}

		gl.glDisable(GL20.GL_CULL_FACE);
		gl.glDisable(GL20.GL_DEPTH_TEST);

		renderHUD(HUD.Instance(), simulation);

	}

	private void renderShip(StarShip ship) {
		shipTexture.bind();
		currentShader.begin();
		currentShader.setUniformi("u_diffuse", 0);
		transform.set(mCamera.combined);
		transform.translate(ship.mPosition.x, ship.mPosition.y,
				ship.mPosition.z);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);
		switch (ship.type) {
		case ADVANCED:
			break;
		case BASIC:
		case BASIC_DOUBLE:
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
		transform.set(mCamera.combined);
		transform.translate(turret.mPosition.x, turret.mPosition.y,
				turret.mPosition.z - Par.TURRET_SINGLE_HALF_DIAMETER);
		transform.rotate(0, 1, 0, turret.mYAangle);
		transform.translate(0, 0, +Par.TURRET_SINGLE_HALF_DIAMETER);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);

		switch (turret.type) {
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
			singleSmallTurretMesh.render(currentShader);
			break;
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
		transform.set(mCamera.combined);
		transform.translate(shoot.mPosition.x, shoot.mPosition.y,
				shoot.mPosition.z);
		transform.rotate(0, 1, 0, shoot.mYAangle);
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
		transform.set(mCamera.combined);
		transform.translate(enemy.mPosition.x, enemy.mPosition.y,
				enemy.mPosition.z);
		transform.scale(enemy.mSize, enemy.mSize, enemy.mSize);
		transform.rotate(0, 1, 0, enemy.mYAangle);
		currentShader.setUniformMatrix("u_projView", transform);
		normal.idt();
		normal.rotate(0, 1, 0, 180);
		normal3.set(normal.toNormalMatrix());
		currentShader.setUniformMatrix("u_normal", normal3);
		switch (enemy.mType) {
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

	private int backgroundscroolingX = 0;
	// TODO Move to an external class and generate scenery
	private Color backgroundColor = new Color(1.0f, 1.0f, 1.0f, 0);

	private void renderBackground(float speed) {

		backgroundscroolingX += speed / Par.BACKGROUND_BASIC_SPEED_SHIP_FACTOR
				+ Par.BACKGROUND_BASIC_SPEED;
		if (backgroundscroolingX >= 1280)
			backgroundscroolingX = 0;

		viewMatrix.setToOrtho2D(0, 0, 1280, 800);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.disableBlending();

		spriteBatch.setColor(backgroundColor);

		spriteBatch.draw(backgroundTexture, 1280 - backgroundscroolingX, 0);
		spriteBatch.draw(backgroundTexture, -backgroundscroolingX, 0);

		spriteBatch.end();
	}

	private void renderHUD(HUD hud, Simulation simulation) {
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.enableBlending();
		for (Message m : hud.GetMessage()) {
			fontStandard.setColor(Color.BLACK);
			fontStandard.draw(spriteBatch, m.msg, m.mPosition.x - 1,
					m.mPosition.y - 1);
			fontStandard.setColor(Color.RED);
			fontStandard.draw(spriteBatch, m.msg, m.mPosition.x, m.mPosition.y);
		}
		for (Message m : hud.GetMessageRoller()) {
			fontStandard.setColor(Color.BLACK);
			fontStandard.draw(spriteBatch, m.msg, m.mPosition.x - 1,
					m.mPosition.y - 1);
			fontStandard.setColor(Color.WHITE);
			fontStandard.draw(spriteBatch, m.msg, m.mPosition.x, m.mPosition.y);
		}

		Message statusmsg = hud.GetStatusBar(simulation.Score,
				simulation.Missed, simulation.starship.mEnergy);
		fontStandard.setColor(Color.BLACK);
		fontStandard.draw(spriteBatch, statusmsg.msg,
				statusmsg.mPosition.x - 1, statusmsg.mPosition.y - 1);
		fontStandard.setColor(Color.WHITE);
		fontStandard.draw(spriteBatch, statusmsg.msg, statusmsg.mPosition.x,
				statusmsg.mPosition.y);

		if (Par.HUD_DEBUG) {
			fontStandard.setColor(Color.WHITE);
			fontStandard.drawMultiLine(spriteBatch, String
					.format("Cam.Pos:%2.2f %2.2f %2.2f\nCam.Dir:%2.2f %2.2f %2.2f\nCam.Angle:%2.2f\nCam.FOV:%s",
							simulation.mCameraMan.mPosition.x,
							simulation.mCameraMan.mPosition.y,
							simulation.mCameraMan.mPosition.z,
							simulation.mCameraMan.mDirection.x,
							simulation.mCameraMan.mDirection.y,
							simulation.mCameraMan.mDirection.z,
							simulation.mCameraMan.mAngle,
							simulation.mCameraMan.FOV), 800, 300);
		}

		spriteBatch.end();
	}

	public void dispose() {
		spriteBatch.dispose();
		shipTexture.dispose();
		spaceshipBasicMesh.dispose();
		spaceshipStandardMesh.dispose();
		shootMesh.dispose();
		// TODO others...
	}

	public Ray unproject(int x, int y) {
		return mCamera.getPickRay(x, y).cpy();
	}
}
