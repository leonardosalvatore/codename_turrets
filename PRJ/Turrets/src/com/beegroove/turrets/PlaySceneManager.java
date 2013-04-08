package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Vector3;
import com.beegroove.turrets.HUD.Message;

public class PlaySceneManager extends OpenGLSceneManager {

	private StillModel spaceshipBasicMesh;
	private StillModel spaceshipStandardMesh;
	private StillModel spaceshipAdvancedMesh;
	private StillModel spaceshipGunshipMesh;
	private StillModel spaceshipBattleCruisedMesh;
	private StillModel shootMesh;
	private StillModel singleSmallTurretMesh;
	private StillModel doubleSmallTurretMesh;
	private StillModel meteroriteMesh;

	private Texture backgroundTexture;
	private Texture explosionTexture;
	private Texture hitTexture;
	private Texture plasmaTexture;
	private Texture asteroidDOFTexture;
	private Texture shieldTexture;
	
	private int backgroundscroolingX = 0;
	private Color backgroundColor = Color.WHITE;

	public PlaySceneManager() {
		try {

			spaceshipBasicMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Starship0.obj"));
			spaceshipStandardMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files.internal("data/SpaceShip1.obj"));
			spaceshipAdvancedMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files.internal("data/SpaceShip2.obj"));
			spaceshipGunshipMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/SpaceShip3.obj"));
			spaceshipBattleCruisedMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files.internal("data/SpaceShip4.obj"));
			singleSmallTurretMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files.internal("data/Turret0.obj"));
			doubleSmallTurretMesh = ModelLoaderRegistry
					.loadStillModel(Gdx.files
							.internal("data/TurretsDoubleSmall.obj"));

			shootMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Shoot0.obj"));

			backgroundTexture = new Texture(
					Gdx.files.internal("data/background.png"));

			explosionTexture = new Texture(
					Gdx.files.internal("data/explosion.png"));
			hitTexture = new Texture(Gdx.files.internal("data/hit.png"));
			plasmaTexture = new Texture(Gdx.files.internal("data/plasma.png"));
			asteroidDOFTexture = new Texture(
					Gdx.files.internal("data/AsteroidBlur1.png"));
			shieldTexture = new Texture(Gdx.files.internal("data/shield.png"));
			
			meteroriteMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Meteorite.obj"));
			cubeMesh = ModelLoaderRegistry.loadStillModel(Gdx.files
					.internal("data/Cube.obj"));

		} catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}

	public void render(PlaySimulation simulation, float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		renderBackground(simulation.spaceship.mSpeed.x);

		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_CULL_FACE);

		setProjectionAndCamera(simulation.mCameraMan);

		currentShader = lightTexShader;

		renderSpaceship(simulation.spaceship);

		for (Asteroid k : simulation.enemies) {
			renderAsteroids(k);
		}

		renderTurret(simulation.spaceship);

		gl.glDisable(GL20.GL_CULL_FACE);
		gl.glDisable(GL20.GL_DEPTH_TEST);

		renderSprite(HUD.Instance(), simulation);
	}

	private void renderSpaceship(SpaceShip ship) {
		currentShader.begin();

		ship.mScreenPosition.set(ship.mPosition);
		mCamera.project(ship.mScreenPosition);

		transform.set(mCamera.combined);
		transform.translate(ship.mPosition.x, ship.mPosition.y,
				ship.mPosition.z);
		transform.rotate(ship.mRotation);

		shaderSetup(transform, mCamera.up, 0.5f, 0.5f, 1.0f);

		switch (ship.type) {
		case BASIC:
		case BASIC_DOUBLE:
			spaceshipBasicMesh.render(currentShader);
			break;
		case STANDARD:
		case STANDARD_DOUBLE:
			spaceshipStandardMesh.render(currentShader);
			break;
		case ADVANCED_DOUBLE:
			spaceshipAdvancedMesh.render(currentShader);
			break;
		case GUNSIHP_DOUBLE:
			spaceshipGunshipMesh.render(currentShader);
			break;
		case BATTLECRUISER:
			spaceshipBattleCruisedMesh.render(currentShader);
			break;
		default:
			break;

		}
		currentShader.end();
	}

	private void renderTurret(SpaceShip ship) {
		for (Turret turret : ship.turrets) {
			currentShader.begin();
			transform.set(mCamera.combined);

			Vector3 tmp = new Vector3(ship.mPosition);
			transform.translate(tmp);
			transform.rotate(ship.mRotation);
			transform.translate(tmp.mul(-1));
			
			transform.translate(turret.mPosition.x, turret.mPosition.y, 
					turret.mPosition.z - Par.TURRET_HALF_DIAMETER);
			transform.rotate(turret.mRotation);
			transform.translate(0, 0, Par.TURRET_HALF_DIAMETER);
			
			shaderSetup(transform, mCamera.up, 1.0f, turret.mEnergy
					/ Par.TURRET_ENERGY, turret.mEnergy / Par.TURRET_ENERGY);

			switch (turret.type) {
			case DOUBLE_AUTOCANNON:
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
			}
			currentShader.end();
		}

	}

	private void renderShoot(Shoot shoot) {
		currentShader.begin();
		currentShader.setUniformi("u_diffuse", 0);
		transform.set(mCamera.combined);
		transform.translate(shoot.mPosition.x, shoot.mPosition.y,
				shoot.mPosition.z);
		transform.rotate(shoot.mRotation);
		shaderSetup(transform, mCamera.up, 1f, 1f, 1f);
		currentShader.setUniformMatrix("u_normal", normal3);
		shootMesh.render(currentShader);
		currentShader.end();
	}

	private void renderAsteroids(Asteroid enemy) {
		currentShader.begin();
		transform.set(mCamera.combined);
		transform.translate(enemy.mPosition.x, enemy.mPosition.y,
				enemy.mPosition.z);

		transform.scale(enemy.mSize, enemy.mSize, enemy.mSize);

		switch (enemy.mType) {
		case METEORITE:
			transform.rotate(enemy.mRotation);
			break;
		default:
			break;
		}

		
		shaderSetup(transform, mCamera.up, 1f, 1f, 1f);
		
		switch (enemy.mType) {
		case METEORITE:
			meteroriteMesh.render(currentShader);
			break;
		case BONUS:
			break;
		default:
			break;
		}
		currentShader.end();
	}

	private void renderBackground(float speed) {

		backgroundscroolingX += speed / Par.BACKGROUND_BASIC_SPEED_SHIP_FACTOR
				+ WaveFactory.mWaveNumber;
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

	private void renderSprite(HUD hud, PlaySimulation simulation) {
		viewMatrix.setToOrtho2D(0, 0, Par.VIEWPORT_MAX_X, Par.VIEWPORT_MAX_Y);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.begin();
		spriteBatch.enableBlending();
		spriteBatch.setColor(Color.WHITE);
		
		if(simulation.spaceship.isHit)
		{
			simulation.spaceship.mScreenPosition.set(simulation.spaceship.mPosition);
			mCamera.project(simulation.spaceship.mScreenPosition);
			simulation.spaceship.mScreenPosition.y -= 192/2;
			simulation.spaceship.mScreenPosition.x -= 40;
			
			spriteBatch.draw(shieldTexture, simulation.spaceship.mScreenPosition.x, simulation.spaceship.mScreenPosition.y);
		}

		for (Turret turret : simulation.spaceship.turrets) {
			for (Shoot shoot : turret.shoots) {
				shoot.mScreenPosition.set(shoot.mPosition);
				shoot.mScreenPosition.y += 0.5;
				mCamera.project(shoot.mScreenPosition);
				
				spriteBatch.draw(plasmaTexture, shoot.mScreenPosition.x,
						shoot.mScreenPosition.y - shoot.mEnergy / 2, 0, 0, 60,
						shoot.mEnergy, 1f, 1f, shoot.mRotationDegree.y, 0, 0,
						60, shoot.mEnergy, false, false);
			}
		}

		if (Par.SETTINGS_FX) {
			for (PhysicItem exp : simulation.explosions) {
				spriteBatch.draw(explosionTexture, exp.mScreenPosition.x - 50
						* exp.mSize, exp.mScreenPosition.y - 50 * exp.mSize,
						100 * exp.mSize, 100 * exp.mSize);
			}

			for (Message m : hud.GetMessage()) {
				fontStandard.setColor(Color.GRAY);
				fontStandard.draw(spriteBatch, m.msg, m.mPosition.x,
						m.mPosition.y);
			}

			for (Message m : hud.GetMessageRoller()) {
				fontLarge.setColor(Color.GRAY);
				fontLarge.draw(spriteBatch, m.msg, m.mPosition.x, m.mPosition.y);
			}
		}

		RenderStatusMessage(simulation, hud.GetStatusBar(
				WaveFactory.mWaveNumber, simulation.Score,
				simulation.spaceship.mNextTo));

		RenderCountDownMessage(simulation,
				hud.GetCountDown(simulation.mCountDown));

		RenderEnergyMessage(simulation,
				hud.GetEnergy(simulation.spaceship.mEnergy));

		if (Par.HUD_DEBUG) {
			fontStandard.setColor(Color.WHITE);
			fontStandard
					.drawMultiLine(
							spriteBatch,
							String.format(
									"Cam.Pos:%2.2f %2.2f %2.2f\nCam.Dir:%2.2f %2.2f %2.2f\nCam.Angle:%2.2f\nCam.FOV:%s\nWave size:%d\nShipPos:%2.2f %2.2f %2.2f\nShipDes:%2.2f %2.2f %2.2f\nShipSpeed:%2.2f %2.2f %2.2f\nTur.Rot:%s\nTur.RotSpeed:%s\nTur.RotDeg:%s\nTur.En:%s",
									simulation.mCameraMan.mPosition.x,
									simulation.mCameraMan.mPosition.y,
									simulation.mCameraMan.mPosition.z,
									simulation.mCameraMan.mDirection.x,
									simulation.mCameraMan.mDirection.y,
									simulation.mCameraMan.mDirection.z,
									simulation.mCameraMan.mAngle,
									simulation.mCameraMan.FOV,
									simulation.enemies.size,
									simulation.spaceship.mPosition.x,
									simulation.spaceship.mPosition.y,
									simulation.spaceship.mPosition.z,
									simulation.spaceship.mDestination.x,
									simulation.spaceship.mDestination.y,
									simulation.spaceship.mDestination.z,
									simulation.spaceship.mSpeed.x,
									simulation.spaceship.mSpeed.y,
									simulation.spaceship.mSpeed.z,
									simulation.spaceship.turrets.get(0).mRotation,
									simulation.spaceship.turrets.get(0).mRotationSpeed,
									simulation.spaceship.turrets.get(0).mRotationDegree,
									simulation.spaceship.turrets.get(0).mEnergy),
							750, 300);
		}
		spriteBatch.end();
	}

	private void RenderStatusMessage(PlaySimulation simulation, Message msg) {

		fontLarge.setColor(Color.DARK_GRAY);
		fontLarge.draw(spriteBatch, msg.msg, msg.mPosition.x - 3,
				msg.mPosition.y - 3);
		fontLarge.setColor(Color.GRAY);

		fontLarge.draw(spriteBatch, msg.msg, msg.mPosition.x, msg.mPosition.y);
	}

	private void RenderCountDownMessage(PlaySimulation simulation, Message msg) {

		fontLarge.setColor(Color.DARK_GRAY);
		fontLarge.draw(spriteBatch, msg.msg, msg.mPosition.x - 3,
				msg.mPosition.y - 3);

		if (simulation.mCountDown > Par.INITIAL_COUNTDOWN / 2) {
			fontLarge.setColor(Color.GRAY);
		} else if (simulation.mCountDown > Par.INITIAL_COUNTDOWN / 4) {
			fontLarge.setColor(Color.ORANGE);
		} else {
			fontLarge.setColor(Color.RED);
		}

		fontLarge.draw(spriteBatch, msg.msg, msg.mPosition.x, msg.mPosition.y);
	}

	private void RenderEnergyMessage(PlaySimulation simulation, Message msg) {
		fontLarge.setColor(Color.DARK_GRAY);
		fontLarge.draw(spriteBatch, msg.msg, msg.mPosition.x - 3,
				msg.mPosition.y - 3);

		if (simulation.spaceship.mEnergy > simulation.spaceship.mEnergy_Initial / 2) {
			fontLarge.setColor(Color.GRAY);
		} else if (simulation.spaceship.mEnergy > simulation.spaceship.mEnergy_Initial / 4) {
			fontLarge.setColor(Color.ORANGE);
		} else {
			fontLarge.setColor(Color.RED);
		}

		fontLarge.draw(spriteBatch, msg.msg, msg.mPosition.x, msg.mPosition.y);
	}

	public void dispose() {
		super.dispose();

		spriteBatch.dispose();
		backgroundTexture.dispose();
		explosionTexture.dispose();
		hitTexture.dispose();
		plasmaTexture.dispose();
		spaceshipBasicMesh.dispose();
		spaceshipStandardMesh.dispose();
		spaceshipAdvancedMesh.dispose();
		spaceshipGunshipMesh.dispose();
		spaceshipBattleCruisedMesh.dispose();
		shootMesh.dispose();
		asteroidDOFTexture.dispose();
	}

}
