package com.beegroove.turrets;

public class HighscoreAndStats {

	public static int sScore = 0;
	public static int sWave = 0;
	public static int sNumberHits = 0;
	public static int sNumberOfShoot = 0;
	public static int sAsteroidsDestroyed = 0;
	public static int sAsteroidsLost = 0;
	
	
	public static void Clear()
	{
		sScore=0;
		sWave=0;
		sNumberHits=0;
		sNumberOfShoot=0;
		sAsteroidsDestroyed=0;
		sAsteroidsLost=0;
	}
	//TODO load and save...
	
}
