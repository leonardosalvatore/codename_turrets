package com.beegroove.turrets;

import com.beegroove.turrets.StarShip.STYPE;
import com.beegroove.turrets.Turret.TTYPE;

public class SpaceshipFactory {
	static StarShip NewSingleBasicSpaceship()
	{
		StarShip ret = new StarShip();
		ret.mSize = 2;
		ret.mPosition.set(Par.SPACESHIP_BASIC_POSITION);
		ret.turrets.add(NewSingleSmallTurret());
		ret.type = STYPE.BASIC;

		return ret;	
	}
	
	static StarShip NewDoubleBasicSpaceship()
	{
		StarShip ret = new StarShip();
		ret.mSize = 2;
		ret.mPosition.set(Par.SPACESHIP_BASIC_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.BASIC_DOUBLE;

		return ret;	
	}
	
	static StarShip NewSingleStandardSpaceship()
	{
		StarShip ret = new StarShip();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewSingleSmallTurret());
		ret.turrets.add(NewSingleSmallTurret());
		ret.type = STYPE.STANDARD;

		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION);

		return ret;	
	}
	
	static StarShip NewDoubleStandardSpaceship()
	{
		StarShip ret = new StarShip();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.STANDARD_DOUBLE;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION);
		return ret;	
	}
	
	
	static StarShip NewDoubleAdvancedSpaceship()
	{
		StarShip ret = new StarShip();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.ADVANCED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(2).mPosition.add(Par.SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_CORRECTION);
		return ret;	
	}
	
	private static Turret NewSingleSmallTurret()
	{
		Turret t = new Turret();
		t.mPosition.set(Par.SINGLE_TURRET_POSITION);
		t.type = TTYPE.SINGLE_SMALL;
		return t;
	}
	
	private static Turret NewDoubleSmallTurret()
	{
		Turret t = new Turret();
		t.mPosition.set(Par.DOUBLE_TURRET_POSITION);
		t.type = TTYPE.DOUBLE_SMALL;
		return t;
	}
}
