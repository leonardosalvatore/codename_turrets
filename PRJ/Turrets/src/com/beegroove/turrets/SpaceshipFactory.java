package com.beegroove.turrets;

import com.beegroove.turrets.StarShip.STYPE;
import com.beegroove.turrets.Turret.TTYPE;

public class SpaceshipFactory {
	static StarShip NewSingleBasicSpaceship()
	{
		StarShip ret = new StarShip();
		ret.Create();
		ret.size = 2;
		ret.position = Parameters.SPACESHIP_BASIC_POSITION();
		ret.turrets.add(NewSingleSmallTurret());
		ret.type = STYPE.BASIC;
		for (Turret turret : ret.turrets) {
			turret.Create();
		}
		return ret;	
	}
	
	static StarShip NewDoubleBasicSpaceship()
	{
		StarShip ret = new StarShip();
		ret.Create();
		ret.size = 2;
		ret.position = Parameters.SPACESHIP_BASIC_POSITION();
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.BASIC_DOUBLE;
		for (Turret turret : ret.turrets) {
			turret.Create();
		}
		return ret;	
	}
	
	static StarShip NewSingleStandardSpaceship()
	{
		StarShip ret = new StarShip();
		ret.Create();
		ret.size = 2;
		ret.position = Parameters.SPACESHIP_BASIC_POSITION();
		ret.turrets.add(NewSingleSmallTurret());
		ret.type = STYPE.STANDARD;
		for (Turret turret : ret.turrets) {
			turret.Create();
		}
		return ret;	
	}
	
	static StarShip NewDoubleStandardSpaceship()
	{
		StarShip ret = new StarShip();
		ret.Create();
		ret.size = 2;
		ret.position = Parameters.SPACESHIP_BASIC_POSITION();
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.STANDARD;
		for (Turret turret : ret.turrets) {
			turret.Create();
		}
		return ret;	
	}
	
	private static Turret NewSingleSmallTurret()
	{
		Turret t = new Turret();
		t.position = Parameters.SINGLE_TURRET_POSITION();
		t.type = TTYPE.SINGLE_SMALL;
		return t;
	}
	
	private static Turret NewDoubleSmallTurret()
	{
		Turret t = new Turret();
		t.position = Parameters.DOUBLE_TURRET_POSITION();
		t.type = TTYPE.DOUBLE_SMALL;
		return t;
	}
}
