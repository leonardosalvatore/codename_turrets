package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.beegroove.turrets.Par.DIRECTION;

public class PlayScreen extends GenericScreen implements SimulationListener {
	/** the simulation **/
	private final Simulation simulation;
	/** the renderer **/
	private final SceneManager renderer;

	public Plane gamePlane;

	public PlayScreen() {
		simulation = new Simulation();
		simulation.listener = this;
		//simulation.starship = SpaceshipFactory.NewSingleBasicSpaceship();
		simulation.starship = SpaceshipFactory.NewDoubleAdvancedSpaceship();
		renderer = new SceneManager();
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
	private Vector3 lastLeftPointOnPlane = new Vector3();
	private Vector3 lastRightPointOnPlane = new Vector3();

	@Override
	public void update(float delta) {

		ProcessImput(delta);
		simulation.update(delta);

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

			if (pointOnPlane.x < 0) {
				Gdx.app.log("PlayScreen",
						String.format("LEFT Int[%d]:%s", pointer, pointOnPlane));
				lastLeftPointOnPlane.set(pointOnPlane);
				return Par.LEFT_FINGER;
			} else {
				Gdx.app.log("PlayScreen", String.format("RIGHT Int[%d]:%s",
						pointer, pointOnPlane));
				simulation.Fire(true,mSuperFire);
				lastRightPointOnPlane.set(pointOnPlane);
				return Par.RIGHT_FINGER;
			}
		}
		return Par.NO_FINGER;
	}
	
	private void ProcessImput(float delta) {
		int pointer0result = ApplyInput(0);
		int pointer1result = ApplyInput(1);

		if (pointer0result != Par.LEFT_FINGER
				&& pointer1result != Par.LEFT_FINGER) {
			simulation.StopShip();
		} else {
			simulation.SetStarshipDestination(lastLeftPointOnPlane);
		}

		if (pointer0result != Par.RIGHT_FINGER
				&& pointer1result != Par.RIGHT_FINGER) {
			simulation.Fire(false,mSuperFire);
		} else {
			simulation.SetTurretTarget(lastRightPointOnPlane);
		}

		
//		if (lastRightPointOnPlane.y - lastLeftPointOnPlane.y < 1f) {
//			if (alignmentStart > Par.SHIP_ALIGNMENT_TIME) {
//				Gdx.app.log("PlayScreen", "SuperFire firing" );
//				mSuperFire = true;
//			}
//			else
//			{
//				Gdx.app.log("PlayScreen", "SuperFire timer start." );
//				alignmentStart = System.currentTimeMillis();
//			}
//		} else 
//			mSuperFire = false;{
//			alignmentStart = 0;
//			Gdx.app.log("PlayScreen", "SuperFire stop firing" );
//		}

		if (Gdx.input.isKeyPressed(Keys.SPACE))
			simulation.Fire(true,mSuperFire);

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
			simulation.moveCameraAddX(-Par.CAMERA_STEP,Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		else if (Gdx.input.isKeyPressed(Keys.H))
			simulation.moveCameraAddX(Par.CAMERA_STEP,Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));

		if (Gdx.input.isKeyPressed(Keys.U))
			simulation.moveCameraAddY(-Par.CAMERA_STEP,Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		else if (Gdx.input.isKeyPressed(Keys.J))
			simulation.moveCameraAddY(Par.CAMERA_STEP,Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		
		if (Gdx.input.isKeyPressed(Keys.I))
			simulation.moveCameraAddZ(-Par.CAMERA_STEP,Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		else if (Gdx.input.isKeyPressed(Keys.K))
			simulation.moveCameraAddZ(Par.CAMERA_STEP,Gdx.input.isKeyPressed(Keys.SHIFT_LEFT));
		
		if (Gdx.input.isKeyPressed(Keys.B))
			simulation.moveCameraRotate(-Par.CAMERA_STEP/10);
		else if (Gdx.input.isKeyPressed(Keys.M))
			simulation.moveCameraRotate(Par.CAMERA_STEP/10);
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
