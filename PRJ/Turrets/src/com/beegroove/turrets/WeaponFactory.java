package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;

public class WeaponFactory {

	public static Shoot NewBasicShoot(Turret turret) {
		Shoot s = new Shoot();
		s.mPosition.set(turret.mPosition);
		s.mPosition.z -= Par.SHOOT_SINGLE_TURRET_Z_CORRECTION;
		s.mSpeed.set(Par.SHOOT_BASIC_SPEED);
		s.mSpeed.rotate(Vector3.Y, turret.mYAangle);
		s.mYAangle = turret.mYAangle;
		return s;
	}

	static int alternate=1;
	public static Shoot NewBasicLeftShoot(Turret turret) {
		Shoot s = new Shoot();
		s.mPosition.set(turret.mPosition);
		s.mPosition.z -= Par.SHOOT_DOUBLE_TURRET_LEFT_TURRET_Z_CORRECTION;
		s.mSpeed.set(Par.SHOOT_BASIC_SPEED);
		s.mSpeed.rotate(Vector3.Y, turret.mYAangle);
		s.mYAangle = turret.mYAangle;
		return s;
	}

	public static Shoot NewBasicRightShoot(Turret turret) {
		Shoot s = new Shoot();
		s.mPosition.set(turret.mPosition);
		s.mPosition.z -= Par.SHOOT_DOUBLE_TURRET_RIGHT_TURRET_Z_CORRECTION;
		s.mSpeed.set(Par.SHOOT_BASIC_SPEED);
		s.mSpeed.rotate(Vector3.Y, turret.mYAangle);
		s.mYAangle = turret.mYAangle;
		return s;
	}
}
