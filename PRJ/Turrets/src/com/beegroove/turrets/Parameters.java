package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class Parameters {

	public static final Vector3 SPACESHIP_BASIC_POSITION = new Vector3(-25f, 0,
			-10);
	public static final Vector3 SPACESHIP_STANDARD_POSITION = new Vector3(-25f, 0,
			-10);
	public static final Vector3 SINGLE_TURRET_POSITION = new Vector3(-25f, 0,
			-10.15f);
	public static final Vector3 DOUBLE_TURRET_POSITION = new Vector3(-27f, 0,
			-12.15f);
	public static final float CAMERA_FOV = 60;
	public static final float CAMERA_FOV_STEP = 1;
	public static final float CAMERA_STEP = 1;
	public static final float CAMERA_X = 0;
	public static final float CAMERA_Y = 30;
	public static final float CAMERA_Z = 0;
	public static final float CAMERA_DIRECTION_X = 0;
	public static final float CAMERA_DIRECTION_Y = 0;
	public static final float CAMERA_DIRECTION_Z = -5;
	public static final float SHIP_MASS = 1f;
	public static final float SHIP_VELOCITY = 0.5f;
	public static final float SHIP_MAX_SPEED = 1.5f;
	public static final Vector3 SHIP_TRUSTER_FWD_FORCE = new Vector3(50, 0, 0);
	public static final Vector3 SHIP_TRUSTER_BACK_FORCE = new Vector3(-50, 0, 0);
	public static final Vector3 SHIP_TRUSTER_UP_FORCE = new Vector3(0, 0, -50);
	public static final Vector3 SHIP_TRUSTER_DOWN_FORCE = new Vector3(0, 0, 50);
	public static final Vector3 SHIP_TRUSTER_FORCE = new Vector3(0, 0, 50);
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
	public static final float THUMB_CORRECTION = 4f;
	public static final int LEFT_FINGER = 1;
	public static final int RIGHT_FINGER = 2;
	public static final int NO_FINGER = 0;
	public static final int INITIAL_WAVE_NUMBER = 10;
	public static final int STANDARD_FONT_SIZE = 26;
	public static final String MSG_HIT = "HIT";
	public static final String MSG_NEW_ENEMY_WAVE = "ALERT -- ALERT -- NEW ENEMY WAVE APPROACHING -- ALERT -- ALERT";

	enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
}
