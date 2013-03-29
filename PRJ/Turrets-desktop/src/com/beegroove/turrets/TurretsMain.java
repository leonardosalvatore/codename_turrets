package com.beegroove.turrets;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class TurretsMain {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Turrets";
		cfg.useGL20 = true;
		cfg.vSyncEnabled = true;
		cfg.width = 1280;
		cfg.height = 800;
		//cfg.width = 800;
		//cfg.height = 480;
		//cfg.width = 480;
		//cfg.height = 200;
		
		new LwjglApplication(new TGdxGame(), cfg);
	}
}
