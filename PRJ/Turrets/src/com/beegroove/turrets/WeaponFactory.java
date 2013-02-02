package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class WeaponFactory {

	public static Shoot NewBasicShoot(Turret turret) {
		Shoot s = new Shoot();
		s.Create();
		s.position = turret.position.cpy();
		s.position.z -= Parameters.TURRET_SINGLE_HALF_DIAMETER;
		s.speed = new Vector3(40, 0, 0);
		s.speed.rotate(Vector3.Y, turret.y_angle);
		s.y_angle = turret.y_angle;
		return s;
	}

	public static Shoot NewBasicLeftShoot(Turret turret) {
		Shoot s = new Shoot();
		s.Create();
		s.position = turret.position.cpy();
		s.position.z -= Parameters.TURRET_DOUBLE_QUARTER_DIAMETER;
		s.speed = new Vector3(40, 0, 0);
		s.speed.rotate(Vector3.Y, turret.y_angle);
		s.y_angle = turret.y_angle;
		return s;
	}

	public static Shoot NewBasicRightShoot(Turret turret) {
		Shoot s = new Shoot();
		s.Create();
		s.position = turret.position.cpy();
		//s.position.z -= Parameters.TURRET_DOUBLE_QUARTER_DIAMETER;
		s.speed = new Vector3(40, 0, 0);
		s.speed.rotate(Vector3.Y, turret.y_angle);
		s.y_angle = turret.y_angle;
		return s;
	}
}
