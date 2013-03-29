package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class HighscoreAndStats {

	public static int sScore = 0;
	public static int sWave = 0;
	public static int sNumberHits = 0;
	public static int sNumberOfShoot = 0;
	public static int sAsteroidsDestroyed = 0;
	public static int sAsteroidsLost = 0;
	
	private static Preferences file;
	
	static
	{
		file = Gdx.app.getPreferences(Par.PREFENCES_FILE_NAME);
		if(!file.contains("HS"))
		{
			Gdx.app.log("T", "First use of HISCORE preferences file");
			file.putInteger("HS", 0);
		}
	}
	
	public static void Clear()
	{
		sScore=0;
		sWave=0;
		sNumberHits=0;
		sNumberOfShoot=0;
		sAsteroidsDestroyed=0;
		sAsteroidsLost=0;
	}

	public static int LoadHighScore()
	{
		return file.getInteger("HS");
	}

	
	public static void SaveIfIs() {
		if(isHighScore())
		{
			Gdx.app.log("T", String.format("Is a new highscore, %d > %d", LoadHighScore(), sScore));
			file.putInteger("HS", sScore);			
		}
		else
		{
			Gdx.app.log("T", String.format("Is NOT a new highscore, %d < %d" , LoadHighScore(), sScore));
		}
		
		file.flush();
	}
	
	public static boolean isHighScore()
	{
		return sScore > LoadHighScore();
	}
	
}
