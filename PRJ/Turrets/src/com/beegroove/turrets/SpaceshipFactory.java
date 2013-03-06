package com.beegroove.turrets;

import com.beegroove.turrets.StarShip.STYPE;
import com.beegroove.turrets.Turret.TTYPE;

public class SpaceshipFactory {
	static StarShip NewSingleBasicSpaceship()
	{
		StarShip ret = new StarShip();
		ret.Create();
		ret.mSize = 2;
		ret.mPosition = Par.SPACESHIP_BASIC_POSITION.cpy();
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
		ret.mSize = 2;
		ret.mPosition = Par.SPACESHIP_BASIC_POSITION.cpy();
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
		ret.mSize = 2;
		ret.mPosition = Par.SPACESHIP_STANDARD_POSITION.cpy();
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
		ret.mSize = 2;
		ret.mPosition = Par.SPACESHIP_STANDARD_POSITION.cpy();
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
		t.mPosition = Par.SINGLE_TURRET_POSITION.cpy();
		t.type = TTYPE.SINGLE_SMALL;
		return t;
	}
	
	private static Turret NewDoubleSmallTurret()
	{
		Turret t = new Turret();
		t.mPosition = Par.DOUBLE_TURRET_POSITION.cpy();
		t.type = TTYPE.DOUBLE_SMALL;
		return t;
	}
}
