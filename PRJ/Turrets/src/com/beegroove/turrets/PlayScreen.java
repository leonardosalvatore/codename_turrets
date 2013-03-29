package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.beegroove.turrets.Par.DIRECTION;
import com.beegroove.turrets.Simulation.GAME;
import com.beegroove.turrets.StarShip.STYPE;
import com.beegroove.turrets.StateMachine.STATE;

public class PlayScreen extends GenericScreen implements SimulationListener {
	private final PlaySimulation simulation;
	private final PlaySceneManager renderer;
	
	public Plane gamePlane;

	public PlayScreen(int level) {
		HUD.Instance().NewMessage(Par.MSG_TOUCH_TO_MOVE,
				new Vector3(100, Par.VIEWPORT_MAX_Y / 2, 0), 6000);
		HUD.Instance().NewMessage(Par.MSG_TOUCH_TO_SHOT,
				new Vector3(Par.VIEWPORT_MAX_X / 2, Par.VIEWPORT_MAX_Y / 2, 0),
				6000);
		simulation = new PlaySimulation();
		simulation.listener = this;

		
		switch (level) {
		case Par.Level_1:
			WaveFactory.mWaveNumber = 0;
			simulation.starship = SpaceshipFactory.NewSingleBasicSpaceship();
			break;
		case Par.Level_2:
			WaveFactory.mWaveNumber = 1;
			simulation.starship = SpaceshipFactory.NewDoubleBasicSpaceship();
			break;
		case Par.Level_3:
			WaveFactory.mWaveNumber = 5;
			simulation.starship = SpaceshipFactory.NewSingleStandardSpaceship();
			break;
		case Par.Level_4:
			WaveFactory.mWaveNumber = 5;
			simulation.starship = SpaceshipFactory.NewDoubleStandardSpaceship();
			break;
		case Par.Level_5:
			WaveFactory.mWaveNumber = 5;
			simulation.starship = SpaceshipFactory.NewDoubleAdvancedSpaceship();
			break;
		case Par.Level_6:
			WaveFactory.mWaveNumber = 10;
			simulation.starship = SpaceshipFactory.NewDoubleGunShipSpaceship();
			break;
		case Par.Level_7:
			WaveFactory.mWaveNumber = 10;
			simulation.starship = SpaceshipFactory
					.NewDoubleBattleCrusierSpaceship();
			simulation.starship = SpaceshipFactory
					.NewDoubleBattleCrusierSpaceship();
			break;
		}
		renderer = new PlaySceneManager();
		gamePlane = new Plane(Vector3.Y, 0);

	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	@Override
	public boolean isDone() {
		// return simulation.ship.lives == 0;
		return false;
	}

	@Override
	public void draw(float delta) {
		renderer.render(simulation, delta);
	}

	private Ray rayFromCamera;
	private Vector3 pointOnPlane = new Vector3();
	private Vector3 lastDrivePointOnPlane = new Vector3();
	private Vector3 lastTargetPointOnPlane = new Vector3();

	@Override
	public void update(float delta) {

		if (GAME.PLAY == simulation.isGameOver()) {
			ProcessImput(delta);
		} else if (GAME.OVER_ENERGY == simulation.isGameOver()) {
			HighscoreAndStats.sScore = simulation.Score;
			HighscoreAndStats.sWave = WaveFactory.mWaveNumber;
			StateMachine.SetNextState(STATE.GAMEOVER, new GameOverScreen());
			return;
		} else if (GAME.OVER_COUNTDOWN == simulation.isGameOver()) {
			HighscoreAndStats.sScore = simulation.Score;
			HighscoreAndStats.sWave = WaveFactory.mWaveNumber;
			StateMachine.SetNextState(STATE.GAMEOVER, new GameOverScreen());
			return;
		}

		simulation.update(delta);

		if (simulation.Score >= simulation.starship.mNextTo
				&& !simulation.starship.IsTheLast) {
			if (simulation.starship.type == STYPE.BASIC) {
				simulation.starship = SpaceshipFactory
						.NewDoubleBasicSpaceship(simulation.starship);
			} else if (simulation.starship.type == STYPE.BASIC_DOUBLE) {
				simulation.starship = SpaceshipFactory
						.NewSingleStandardSpaceship(simulation.starship);
			} else if (simulation.starship.type == STYPE.STANDARD) {
				simulation.starship = SpaceshipFactory
						.NewDoubleStandardSpaceship(simulation.starship);
			} else if (simulation.starship.type == STYPE.STANDARD_DOUBLE) {
				simulation.starship = SpaceshipFactory
						.NewDoubleAdvancedSpaceship(simulation.starship);
			} else if (simulation.starship.type == STYPE.ADVANCED_DOUBLE) {
				simulation.starship = SpaceshipFactory
						.NewDoubleGunShipSpaceship(simulation.starship);
			} else if (simulation.starship.type == STYPE.GUNSIHP_DOUBLE) {
				simulation.starship = SpaceshipFactory
						.NewDoubleBattleCrusierSpaceship(simulation.starship);
			}
			simulation.starship.setDestination(new Vector3(30, 0, 0));
			HUD.Instance().NewMessageRoller(
					Par.MSG_NEW_SPACESHIP + simulation.starship.mNextTo);
		}
	}

	long alignmentStart = 0;
	boolean mSuperFire = false;
	boolean mFire = false;

	private int ApplyInput(int pointer) {
		if (Gdx.input.isTouched(pointer)) {
			rayFromCamera = renderer.unproject(Gdx.input.getX(pointer),
					Gdx.input.getY(pointer));

			Intersector.intersectRayPlane(rayFromCamera, gamePlane,
					pointOnPlane);

			if (pointOnPlane.x < lastDrivePointOnPlane.x) {
				lastDrivePointOnPlane.set(pointOnPlane);
				return Par.DRIVE_FINGER;
			} else {
				simulation.Fire(true, mSuperFire);
				lastTargetPointOnPlane.set(pointOnPlane);
				return Par.TARGET_FINGER;
			}
		}
		return Par.NO_FINGER;
	}

	private void ProcessImput(float delta) {
		int pointer0result = ApplyInput(0);
		int pointer1result = ApplyInput(1);

		if (pointer0result != Par.DRIVE_FINGER
				&& pointer1result != Par.DRIVE_FINGER) {
			simulation.StopShip();
		} else {
			simulation.SetStarshipDestination(lastDrivePointOnPlane);
		}

		if (pointer0result != Par.TARGET_FINGER
				&& pointer1result != Par.TARGET_FINGER) {
			simulation.Fire(false, mSuperFire);
		} else {
			simulation.SetTurretTarget(lastTargetPointOnPlane);
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			simulation.Fire(true, mSuperFire);
		}

		if (Gdx.input.isKeyPressed(Keys.W))
			simulation.SetStarshipDirection(DIRECTION.UP);
		else if (Gdx.input.isKeyPressed(Keys.S))
			simulation.SetStarshipDirection(DIRECTION.DOWN);

		if (Gdx.input.isKeyPressed(Keys.A))
			simulation.SetStarshipDirection(DIRECTION.LEFT);
		else if (Gdx.input.isKeyPressed(Keys.D))
			simulation.SetStarshipDirection(DIRECTION.RIGHT);

		if (Gdx.input.isKeyPressed(Keys.DPAD_UP))
			simulation.rotateTurret(Par.KEY_ANGLE_STEP);
		else if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
			simulation.rotateTurret(-Par.KEY_ANGLE_STEP);

		if (Gdx.input.isKeyPressed(Keys.Y))
			simulation.moveCameraAddX(-Par.CAMERA_STEP,
					Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		else if (Gdx.input.isKeyPressed(Keys.H))
			simulation.moveCameraAddX(Par.CAMERA_STEP,
					Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));

		if (Gdx.input.isKeyPressed(Keys.U))
			simulation.moveCameraAddY(-Par.CAMERA_STEP,
					Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		else if (Gdx.input.isKeyPressed(Keys.J))
			simulation.moveCameraAddY(Par.CAMERA_STEP,
					Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));

		if (Gdx.input.isKeyPressed(Keys.I))
			simulation.moveCameraAddZ(-Par.CAMERA_STEP,
					Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		else if (Gdx.input.isKeyPressed(Keys.K))
			simulation.moveCameraAddZ(Par.CAMERA_STEP,
					Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));

		if (Gdx.input.isKeyPressed(Keys.B))
			simulation.moveCameraRotate(-Par.CAMERA_STEP / 10);
		else if (Gdx.input.isKeyPressed(Keys.M))
			simulation.moveCameraRotate(Par.CAMERA_STEP / 10);
		else if (Gdx.input.isKeyPressed(Keys.N))
			simulation.moveCameraRotate(0);

		if (Gdx.input.isKeyPressed(Keys.O))
			simulation.FOVMinus(Par.CAMERA_FOV_STEP);
		else if (Gdx.input.isKeyPressed(Keys.L))
			simulation.FOVPlus(Par.CAMERA_FOV_STEP);
	}

	@Override
	public void explosion() {
		// explosion.play();
	}

	@Override
	public void shot() {
		// shot.play();
	}
}
