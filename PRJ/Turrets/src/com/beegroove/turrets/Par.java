package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class Par {
	public static final boolean HUD_DEBUG = false;
	public static final Vector3 SINGLE_TURRET_POSITION 	= new Vector3(-25f, 0, -10.15f);
	public static final Vector3 DOUBLE_TURRET_POSITION 	= new Vector3(-25f, 0, -10.15f);
	public static final Vector3 SHOOT_BASIC_SPEED 		= new Vector3(40, 0, 0);
	public static final float TURRET_HEADING_MIN = -45;
	public static final float TURRET_HEADING_MAX = 45;

	public static final Vector3 SPACESHIP_BASIC_POSITION 	= new Vector3(-25f, 0, -10);
	public static final Vector3 SPACESHIP_STANDARD_POSITION = new Vector3(-25f, 0, -10);

	public static final Vector3 SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION = new Vector3(0.45f,0.1f,0.1f);
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MIN = -30;
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MAX = 30;
	public static final Vector3 SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION = new Vector3(0.45f,0.1f,3.3f);
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MIN = 30;
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX = 30;
	
	public static final Vector3 SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_CORRECTION = new Vector3(0.45f,0.1f,0.1f);
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_HEADING_MIN = -30;
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_HEADING_MAX =30;
	public static final Vector3 SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_CORRECTION = new Vector3(-1.8f,1.0f,1.5f);
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_HEADING_MIN = -45;
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_HEADING_MAX = 45;
	public static final Vector3 SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_CORRECTION = new Vector3(0.45f,0.1f,3.3f);
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_HEADING_MIN = -30;
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_HEADING_MAX = 30;
	
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_CORRECTION = new Vector3(0.45f,0.1f,0.1f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_HEADING_MIN = -40;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_HEADING_MAX =20;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_CORRECTION = new Vector3(-1.8f,1.0f,1.5f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_HEADING_MIN = -50;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_HEADING_MAX = 50;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_CORRECTION = new Vector3(0.45f,0.1f,3.3f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_HEADING_MIN = -20;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_HEADING_MAX = 40;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_CORRECTION = new Vector3(-3.4f,0.1f,-1.3f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_HEADING_MIN = 0;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_HEADING_MAX =30;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_CORRECTION = new Vector3(-3.4f,0.1f,4.7f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_HEADING_MIN = -30;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_HEADING_MAX = 0;
	
	public static final float SHOOT_SINGLE_TURRET_Z_CORRECTION = 1.4f;
	public static final float SHOOT_DOUBLE_TURRET_LEFT_TURRET_Z_CORRECTION = 1.1f;
	public static final float SHOOT_DOUBLE_TURRET_RIGHT_TURRET_Z_CORRECTION = 1.7f;

	public static final float CAMERA_FOV = 60;
	public static final float CAMERA_FOV_STEP = 1;
	public static final float CAMERA_STEP = 0.1f;
	public static final Vector3 CAMERA_INITIAL_POSITION = new Vector3(0,30,0);
	public static final Vector3 CAMERA_INITIAL_DIRECTION = new Vector3(0,0,-5);
	public static final float SHIP_MASS = 1f;
	public static final float SHIP_VELOCITY = 0.5f;
	public static final float SHIP_MAX_SPEED = 2.5f;
	public static final float SHIP_MAX_SPEED_KEYBOARD = 30f;
	
	public static final float VIEWPORT_MAX_X = 1280;
	public static final float VIEWPORT_MIN_X = 0;
	public static final float VIEWPORT_MIN_Y = 0;
	public static final float VIEWPORT_MAX_Y = 800;
	public static final float TOUCH_Y_SCALE = 8;
	public static final float KEY_Y_SCALE = 60;
	public static final float KEY_ANGLE_STEP = 3f;
	public static final float TURRET_SINGLE_HALF_DIAMETER = 1.4f;
	public static final float TURRET_DOUBLE_HALF_DIAMETER = 1.4f;
	public static final float TURRET_DOUBLE_QUARTER_DIAMETER = 0.6f;
	public static final int FPS = 30;
	public static final float THUMB_CORRECTION = 5f;
	public static final int DRIVE_FINGER = 1;
	public static final int TARGET_FINGER = 2;
	public static final int NO_FINGER = 0;
	public static final int INITIAL_WAVE_NUMBER = 50;
	public static final int STANDARD_FONT_SIZE = 26;
	public static final String MSG_HIT = "HIT";
	public static final String MSG_NEW_ENEMY_WAVE = "ALERT -- ALERT -- NEW ENEMY WAVE APPROACHING -- ALERT -- ALERT";
	public static final float BACKGROUND_BASIC_SPEED = 5;
	public static final float BACKGROUND_BASIC_SPEED_SHIP_FACTOR = 12;
	public static final long SHIP_ALIGNMENT_TIME = 2000;
	public static final long STATE_INTRO_DURATION = 1000;
	
	enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}

}
