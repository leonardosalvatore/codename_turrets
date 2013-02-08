package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.beegroove.turrets.Parameters.DIRECTION;

public class PlayScreen extends GenericScreen implements SimulationListener {
	/** the simulation **/
	private final Simulation simulation;
	/** the renderer **/
	private final SceneManager renderer;
	
	public Plane gamePlane;

	public PlayScreen() {
		simulation = new Simulation();
		simulation.listener = this;
		simulation.starship = SpaceshipFactory.NewSingleBasicSpaceship();
		
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
	private Vector3 pointOnPlane=new Vector3();
	private Vector3 lastLeftPointOnPlane=new Vector3();
	private Vector3 lastRightPointOnPlane=new Vector3();
	

	@Override
	public void update(float delta) {
		
		ProcessImput(delta);
		simulation.update(delta);
		
		if(simulation.Score == 30 &&
				simulation.starship.type != StarShip.STYPE.BASIC_DOUBLE)
		{
			simulation.starship =  SpaceshipFactory.NewDoubleBasicSpaceship();
		}
	}

	private void ProcessImput(float delta) {
		int pointer0result = ApplyInput(0);
		int pointer1result = ApplyInput(1);	
		
		if( pointer0result != Parameters.LEFT_FINGER &&
				pointer1result != Parameters.LEFT_FINGER )
		{
			simulation.StopShip();
		}
		else
		{
			simulation.SetStarshipDestination(lastLeftPointOnPlane);					
		}
		
		if( pointer0result != Parameters.RIGHT_FINGER &&
			pointer1result != Parameters.RIGHT_FINGER )
		{
			simulation.Fire(false);
		}
		else
		{
			simulation.SetTurretTarget(lastRightPointOnPlane);
		}
		
		if (Gdx.input.isKeyPressed(Keys.W))
			simulation.SetStarshipDirection(DIRECTION.UP);

		if (Gdx.input.isKeyPressed(Keys.S))
			simulation.SetStarshipDirection(DIRECTION.DOWN);

		if (Gdx.input.isKeyPressed(Keys.A))
			simulation.SetStarshipDirection(DIRECTION.LEFT);

		if (Gdx.input.isKeyPressed(Keys.D))
			simulation.SetStarshipDirection(DIRECTION.RIGHT);

		if (Gdx.input.isKeyPressed(Keys.DPAD_UP))
			simulation.rotateTurret(Parameters.KEY_ANGLE_STEP);

		if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
			simulation.rotateTurret(-Parameters.KEY_ANGLE_STEP);

		if (Gdx.input.isKeyPressed(Keys.K))
			simulation.moveCameraDown(Parameters.CAMERA_STEP);

		if (Gdx.input.isKeyPressed(Keys.I))
			simulation.moveCameraUp(Parameters.CAMERA_STEP);

		if (Gdx.input.isKeyPressed(Keys.O))
			simulation.FOVMinus(Parameters.CAMERA_FOV_STEP);

		if (Gdx.input.isKeyPressed(Keys.L))
			simulation.FOVPlus(Parameters.CAMERA_FOV_STEP);
	}
	
	private int ApplyInput(int pointer)
	{
		if (Gdx.input.isTouched(pointer)) {
			rayFromCamera = renderer.unproject(Gdx.input.getX(pointer),Gdx.input.getY(pointer));
			
			Intersector.intersectRayPlane(rayFromCamera, gamePlane, pointOnPlane);
			
			
			if(pointOnPlane.x < 0)
			{
				Gdx.app.log("PlayScreen", String.format("LEFT Int[%d]:%s",pointer,pointOnPlane));	
				lastLeftPointOnPlane = pointOnPlane.cpy();
				return Parameters.LEFT_FINGER;
			}
			else
			{
				Gdx.app.log("PlayScreen", String.format("RIGHT Int[%d]:%s",pointer,pointOnPlane));	
				simulation.Fire(true);
				lastRightPointOnPlane = pointOnPlane.cpy();
				return Parameters.RIGHT_FINGER;
			}
		}
		return Parameters.NO_FINGER;
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
