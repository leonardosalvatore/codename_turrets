package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.beegroove.turrets.StateMachine.STATE;

public class IntroScreen extends GenericScreen implements SimulationListener {
	private final IntroSceneManager renderer;
	private final IntroSimulation simulation;
	
	public IntroScreen()
	{
		renderer = new IntroSceneManager();
		simulation = new IntroSimulation();
		simulation.listener = this;

	}
	
	@Override
	public void update(float delta) {
		simulation.update(delta);
	}

	@Override
	public void draw(float delta) {
		renderer.render(simulation, delta);
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void explosion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shot() {
		// TODO Auto-generated method stub
		
	}
	

}
