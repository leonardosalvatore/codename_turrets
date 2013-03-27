package com.beegroove.turrets;

public class GameOverScreen extends GenericScreen implements SimulationListener {
	private final GameOverSimulation simulation;
	private final GameOverSceneManager renderer;

	public GameOverScreen()
	{
		simulation = new GameOverSimulation();
		simulation.listener = this;
		
		renderer = new GameOverSceneManager();

	}
	
	@Override
	public void explosion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shot() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

}
