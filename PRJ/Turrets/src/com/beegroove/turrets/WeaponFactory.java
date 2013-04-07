package com.beegroove.turrets;


public class WeaponFactory {

	public static Shoot NewBasicShoot(Turret turret) {
		Shoot s = new Shoot();
		s.mPosition.set(turret.mPosition);
		s.mRotation.set(turret.mRotation);
		s.mPosition.z -= Par.SHOOT_SINGLE_TURRET_Z_CORRECTION;
		s.mSpeed.set(Par.SHOOT_BASIC_SPEED);
		turret.mRotation.transform(s.mSpeed);
		s.mEnergy = Par.SHOOT_BASIC_ENERGY;
		s.mRotationDegree.set(turret.mRotationDegree);
		return s;
	}

	static int alternate=1;
	public static Shoot NewBasicLeftShoot(Turret turret) {
		Shoot s = new Shoot();
		s.mPosition.set(turret.mPosition);
		s.mRotation.set(turret.mRotation);
		s.mPosition.z -= Par.SHOOT_DOUBLE_TURRET_LEFT_TURRET_Z_CORRECTION;
		s.mSpeed.set(Par.SHOOT_BASIC_SPEED);
		turret.mRotation.transform(s.mSpeed);
		s.mEnergy = Par.SHOOT_BASIC_ENERGY;
		s.mRotationDegree.set(turret.mRotationDegree);
		return s;
	}

	public static Shoot NewBasicRightShoot(Turret turret) {
		Shoot s = new Shoot();
		s.mPosition.set(turret.mPosition);
		s.mRotation.set(turret.mRotation);
		s.mPosition.z -= Par.SHOOT_DOUBLE_TURRET_RIGHT_TURRET_Z_CORRECTION;
		s.mSpeed.set(Par.SHOOT_BASIC_SPEED);
		turret.mRotation.transform(s.mSpeed);
		s.mEnergy = Par.SHOOT_BASIC_ENERGY;
		s.mRotationDegree.set(turret.mRotationDegree);
		return s;
	}
}
