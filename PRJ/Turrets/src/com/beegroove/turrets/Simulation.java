package com.beegroove.turrets;

import java.util.Random;

public class Simulation {

	public transient SimulationListener listener;
	public Cameraman mCameraMan;
	protected Random rand;

	public Simulation() {
		rand = new Random(System.currentTimeMillis());
		mCameraMan = new Cameraman();
		
	}
	
	enum GAME
	{
		OVER_COUNTDOWN,OVER_ENERGY,PLAY
	}

}