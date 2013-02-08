package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class WeaponFactory {

	public static Shoot NewBasicShoot(Turret turret) {
		Shoot s = new Shoot();
		s.Create();
		s.position = turret.position.cpy();
		s.position.z -= Parameters.SHOOT_SINGLE_TURRET_Z_CORRECTION;
		s.speed = Parameters.SHOOT_BASIC_SPEED();
		s.speed.rotate(Vector3.Y, turret.y_angle);
		s.y_angle = turret.y_angle;
		return s;
	}

	static int alternate=1;
	public static Shoot NewBasicLeftShoot(Turret turret) {
		Shoot s = new Shoot();
		s.Create();
		s.position = turret.position.cpy();
		s.position.z -= Parameters.SHOOT_DOUBLE_TURRET_LEFT_TURRET_Z_CORRECTION;
		s.speed = Parameters.SHOOT_BASIC_SPEED();
		s.speed.rotate(Vector3.Y, turret.y_angle);
		s.y_angle = turret.y_angle;
		return s;
	}

	public static Shoot NewBasicRightShoot(Turret turret) {
		Shoot s = new Shoot();
		s.Create();
		s.position = turret.position.cpy();
		s.position.z -= Parameters.SHOOT_DOUBLE_TURRET_RIGHT_TURRET_Z_CORRECTION;
		s.speed = Parameters.SHOOT_BASIC_SPEED();
		s.speed.rotate(Vector3.Y, turret.y_angle);
		s.y_angle = turret.y_angle;
		return s;
	}
}
