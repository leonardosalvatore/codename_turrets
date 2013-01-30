package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class PlayScreen extends GenericScreen implements SimulationListener {
	/** the simulation **/
	private final Simulation simulation;
	/** the renderer **/
	private final SceneManager renderer;

	public PlayScreen() {
		simulation = new Simulation();
		simulation.listener = this;
		renderer = new SceneManager();
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
	
	private float deltaX,deltaY,X,Y;
	private void ApplyInput(int finger)
	{
		if (Gdx.input.isTouched(finger)) {
			
			deltaY = Gdx.input.getDeltaY(finger);
			deltaX = Gdx.input.getDeltaX(finger);
			Y = Gdx.input.getY(finger);
			X = Gdx.input.getX(finger);
			
			Gdx.app.log("PlayScreen", String.format("Finger %d touch: %f,%f",finger,X,Y));
			
			if(X < Gdx.graphics.getWidth()/2)
			{
				simulation.ApplyForceToShip(Parameters.SHIP_TRUSTER_FORCE.cpy().mul(deltaY));
			}
			else
			{
				simulation.rotateTurret((Gdx.graphics.getHeight()/2-Y)/Gdx.graphics.getHeight());
			}
		} else {
			simulation.StopShip();
		}

	}

	@Override
	public void update(float delta) {

		ApplyInput(0);
		ApplyInput(1);
		
		if (Gdx.input.isKeyPressed(Keys.W))
			simulation.ApplyForceToShip(Parameters.SHIP_TRUSTER_UP_FORCE);

		if (Gdx.input.isKeyPressed(Keys.S))
			simulation.ApplyForceToShip(Parameters.SHIP_TRUSTER_DOWN_FORCE);

		if (Gdx.input.isKeyPressed(Keys.A))
			simulation.ApplyForceToShip(Parameters.SHIP_TRUSTER_BACK_FORCE);

		if (Gdx.input.isKeyPressed(Keys.D))
			simulation.ApplyForceToShip(Parameters.SHIP_TRUSTER_FWD_FORCE);

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

		// if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE))
		// simulation.shot();

		simulation.update(delta);

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
