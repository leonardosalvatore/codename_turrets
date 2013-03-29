package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector3;

public class Par {
	public static final boolean HUD_DEBUG = false;
	public static final int INITIAL_COUNTDOWN = 10800;
	public static final int INITIAL_WAVE_NUMBER = 40;
	public static int STANDARD_FONT_SIZE = 26;
	public static int LARGE_FONT_SIZE = 34;
	public static int MAIN_TITLE_FONT_SIZE = 70;

	public static final float HUD_SPLITPOINT = -10;
	public static final int Level_1 = 10;
	public static final int Level_2 = 50;
	public static final int Level_3 = 100;
	public static final int Level_4 = 200;
	public static final int Level_5 = 400;
	public static final int Level_6 = 800;
	public static final int Level_7 = 5000;
	public static final int Level_8 = 10000;

	public static final Vector3 MOON_POSITION = new Vector3(0f, 0, -0.5f);
	public static final float MOON_ROTATION_SPEED = 0.2f;
	public static final float MOON_SCALE = 10;
	public static final float MOON_DURATION = 5000;
	public static final float MOON_EXPLOSION = 3000;
	public static final float MOON_METEORITES_SCALE = 2;
	public static final int MOON_METEORITES_NUMBER = 45;
	public static final float MOON_METEORITES_SPEED = 10f;
	public static final int MOON_METEORITES_SLEEP = 280;
	public static final String TITLE_MSG = "MOON APOCALYPSE";
	public static final String INTRO_MSG = "The Asteroid '2012 DA14' missed the Earth...\nBut it blew up our Moon!!!\nYou have 3 MINUTES to destroy \nthe moon shards ...\n\nFree game preview:\nReview and rate the beta\nfeedbacks needed to complete\nthe official game. ";

	public static final Vector3 SINGLE_TURRET_POSITION = new Vector3(-25f, 0,
			-10.15f);
	public static final Vector3 DOUBLE_TURRET_POSITION = new Vector3(-25f, 0,
			-10.15f);
	public static final Vector3 TURRET_LIGHT_FIRING_POSITION = new Vector3(
			-25f, 0, 0);

	public static final Vector3 SHOOT_BASIC_SPEED = new Vector3(40, 0, 0);
	public static final float TURRET_HEADING_MIN = -45;
	public static final float TURRET_HEADING_MAX = 45;
	public static final int TURRET_ENERGY = 150;
	public static final float BASIC_SHIP_SPEED = 2.2f;
	public static final float STANDARD_SHIP_SPEED = 3.2f;
	public static final float ADVANCED_SHIP_SPEED = 3f;
	public static final float GUNSHIP_SHIP_SPEED = 2.5f;
	public static final float BATTLECRIUSER_SHIP_SPEED = 4f;
	public static final float ADV_BATTLECRIUSER_SHIP_SPEED = 4f;

	public static final float PHY_MAX_SPEED = 2.5f;
	public static final float SHIP_MAX_SPEED_KEYBOARD = 30f;

	public static final String MSG_HIT = "HIT";
	public static final String MSG_NEW_ENEMY_WAVE = "ALERT -- NEW ENEMY WAVE APPROACHING -- ALERT";
	public static final String MSG_NEW_SPACESHIP = "WOW!!! A NEW IMPROBABLE STARSHIP!!! GET THE NEXT ONE AT ";

	public static final Vector3 SPACESHIP_BASIC_POSITION = new Vector3(-25f, 0,
			-10);
	public static final Vector3 SPACESHIP_STANDARD_POSITION = new Vector3(-25f,
			0, -10);

	public static final Vector3 SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION = new Vector3(
			0.45f, 0.1f, 0.1f);
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MIN = -40;
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MAX = 70;
	public static final Vector3 SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION = new Vector3(
			0.45f, 0.1f, 3.3f);
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MIN = -70;
	public static final float SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX = 40;

	public static final Vector3 SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_CORRECTION = new Vector3(
			0.45f, 0.1f, 0.1f);
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_HEADING_MIN = -40;
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_HEADING_MAX = 70;
	public static final Vector3 SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_CORRECTION = new Vector3(
			-1.8f, 1.0f, 1.5f);
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_HEADING_MIN = -85;
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_HEADING_MAX = 85;
	public static final Vector3 SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_CORRECTION = new Vector3(
			0.45f, 0.1f, 3.3f);
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_HEADING_MIN = -70;
	public static final float SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_HEADING_MAX = 40;

	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_CORRECTION = new Vector3(
			0.45f, 0.1f, 0.1f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_HEADING_MIN = -85;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_HEADING_MAX = 50;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_CORRECTION = new Vector3(
			-1.8f, 1.0f, 1.5f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_HEADING_MIN = -85;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_HEADING_MAX = 85;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_CORRECTION = new Vector3(
			0.45f, 0.1f, 3.3f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_HEADING_MIN = -85;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_HEADING_MAX = 50;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_CORRECTION = new Vector3(
			-3.4f, 0.1f, -1.3f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_HEADING_MIN = -20;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_HEADING_MAX = 85;
	public static final Vector3 SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_CORRECTION = new Vector3(
			-3.4f, 0.1f, 4.7f);
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_HEADING_MIN = -85;
	public static final float SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_HEADING_MAX = 20;

	public static final Vector3 SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_LEFT_CORRECTION = new Vector3(
			.2f, 0.0f, 0.6f);
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_LEFT_HEADING_MAX = 90;
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_LEFT_HEADING_MIN = -40;
	public static final Vector3 SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_RIGHT_CORRECTION = new Vector3(
			.2f, 0.0f, 2.8f);
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_RIGHT_HEADING_MAX = 40;
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_RIGHT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_LEFT_CORRECTION = new Vector3(
			-3f, 0.6f, 0.3f);
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_LEFT_HEADING_MAX = 90;
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_LEFT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_RIGHT_CORRECTION = new Vector3(
			-3f, 0.6f, 3.0f);
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_RIGHT_HEADING_MAX = 90;
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_RIGHT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_LEFT_CORRECTION = new Vector3(
			-6f, 1f, 0.1f);
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_LEFT_HEADING_MAX = 90;
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_LEFT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_RIGHT_CORRECTION = new Vector3(
			-6f, 1f, 3.3f);
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_RIGHT_HEADING_MAX = 90;
	public static final float SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_RIGHT_HEADING_MIN = -90;

	public static final Vector3 SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_1_LEFT_CORRECTION = new Vector3(
			.2f, 0.0f, 0.6f);
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_1_LEFT_HEADING_MAX = 90;
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_1_LEFT_HEADING_MIN = -40;
	public static final Vector3 SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_1_RIGHT_CORRECTION = new Vector3(
			.2f, 0.0f, 2.8f);
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_1_RIGHT_HEADING_MAX = 40;
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_1_RIGHT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_2_LEFT_CORRECTION = new Vector3(
			-3f, 0.6f, 0.3f);
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_2_LEFT_HEADING_MAX = 90;
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_2_LEFT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_2_RIGHT_CORRECTION = new Vector3(
			-3f, 0.6f, 3.0f);
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_2_RIGHT_HEADING_MAX = 90;
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_2_RIGHT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_3_LEFT_CORRECTION = new Vector3(
			-6f, 1f, 0.1f);
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_3_LEFT_HEADING_MAX = 90;
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_3_LEFT_HEADING_MIN = -90;
	public static final Vector3 SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_3_RIGHT_CORRECTION = new Vector3(
			-6f, 1f, 3.3f);
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_3_RIGHT_HEADING_MAX = 90;
	public static final float SPACESHIP_ADV_BATTLECRUISER_POSITION_TURRET_3_RIGHT_HEADING_MIN = -90;

	public static final float SHOOT_SINGLE_TURRET_Z_CORRECTION = 1.4f;
	public static final float SHOOT_DOUBLE_TURRET_LEFT_TURRET_Z_CORRECTION = 1.1f;
	public static final float SHOOT_DOUBLE_TURRET_RIGHT_TURRET_Z_CORRECTION = 1.7f;
	public static final int SHOOT_BASIC_ENERGY = 3;

	public static final float CAMERA_FOV = 60;
	public static final float CAMERA_FOV_STEP = 1;
	public static final float CAMERA_STEP = 0.1f;
	public static final Vector3 CAMERA_INITIAL_POSITION = new Vector3(0, 30, 0);
	public static final Vector3 CAMERA_INITIAL_DIRECTION = new Vector3(0, 0, -5);

	public static float VIEWPORT_MAX_X = 1280;
	public static final float VIEWPORT_MIN_X = 0;
	public static final float VIEWPORT_MIN_Y = 0;
	public static float VIEWPORT_MAX_Y = 800;
	public static final float TOUCH_Y_SCALE = 8;
	public static final float KEY_Y_SCALE = 60;
	public static final float KEY_ANGLE_STEP = 3f;
	public static final float TURRET_SINGLE_HALF_DIAMETER = 1.4f;
	public static final float TURRET_DOUBLE_HALF_DIAMETER = 1.4f;
	public static final float TURRET_DOUBLE_QUARTER_DIAMETER = 0.6f;
	public static final int FPS = 30;
	public static final float THUMB_CORRECTION = 7f;
	public static final int DRIVE_FINGER = 1;
	public static final int TARGET_FINGER = 2;
	public static final int NO_FINGER = 0;
	public static final float BACKGROUND_BASIC_SPEED_SHIP_FACTOR = 20;
	public static final long SHIP_ALIGNMENT_TIME = 2000;
	public static final long STATE_INTRO_DURATION = 1000;
	public static final String GAMEOVER_MSG = "GAME OVER";

	enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}

	public static final int VIBRATION_SPACESHIP_HIT_DURATION = 200;
	public static final String GAMEOVER_MESSAGE = "High Score:%d\nLatest score:%d\nWave:%d\nTotal hits:%d\nTotal shoot:%d\nAsteroids destroyed:%d\nAsteroids lost:%d";
	public static final long GAMEOVER_MIN_DURATION = 500;
	public static final long INTRO_MIN_DURATION = 2000;
	public static final int START_X = Par.MAIN_TITLE_FONT_SIZE;
	public static final int START_Y = 70;
	public static final int START_SIZE = 45;
	public static final String MSG_TOUCH_TO_MOVE = "<-- touch here to MOVE";
	public static final String MSG_TOUCH_TO_SHOT = "touch here to SHOT -->";
	public static final String PREFENCES_FILE_NAME = "moonpreferences";
	public static boolean SETTINGS_VIBRA = true;
	public static String SETTINGS_VIBRA_NAME = "VIBRATION";
	public static int SETTINGS_VIBRA_X = Par.MAIN_TITLE_FONT_SIZE;
	public static int SETTINGS_VIBRA_Y = 140;
	public static boolean SETTINGS_AUDIO = true;
	public static String SETTINGS_AUDIO_NAME = "AUDIO";
	public static int SETTINGS_AUDIO_X = Par.MAIN_TITLE_FONT_SIZE;
	public static int SETTINGS_AUDIO_Y = 190;
	public static boolean SETTINGS_FX = true;
	public static String SETTINGS_FX_NAME = "FX";
	public static int SETTINGS_FX_X = (int) Par.MAIN_TITLE_FONT_SIZE;
	public static int SETTINGS_FX_Y = 240;
	public static int SETTINGS_SIZE_X = 250;
	public static int SETTINGS_SIZE_Y = 45;

	static private Preferences preferences;

	public static void LoadDefaultSettings() {

		if (preferences == null) {
			preferences = Gdx.app.getPreferences("moonsettings");
		}

		if (!preferences.contains(Par.SETTINGS_AUDIO_NAME)) {
			SETTINGS_AUDIO = true;
		} else {
			SETTINGS_AUDIO = preferences.getBoolean(Par.SETTINGS_AUDIO_NAME);
		}

		if (!preferences.contains(Par.SETTINGS_VIBRA_NAME)) {
			SETTINGS_VIBRA = true;
		} else {
			SETTINGS_VIBRA = preferences.getBoolean(Par.SETTINGS_VIBRA_NAME);
		}

		if (!preferences.contains(Par.SETTINGS_FX_NAME)) {
			SETTINGS_FX = true;
		} else {
			SETTINGS_FX = preferences.getBoolean(Par.SETTINGS_FX_NAME);
		}

		SaveSettings();
	}

	public static void SaveSettings() {
		if (preferences == null) {
			preferences = Gdx.app.getPreferences(Par.PREFENCES_FILE_NAME);
		}

		preferences.putBoolean(Par.SETTINGS_AUDIO_NAME, SETTINGS_AUDIO);
		preferences.putBoolean(Par.SETTINGS_VIBRA_NAME, SETTINGS_VIBRA);
		preferences.putBoolean(Par.SETTINGS_FX_NAME, SETTINGS_FX);
		preferences.flush();
	}

}
