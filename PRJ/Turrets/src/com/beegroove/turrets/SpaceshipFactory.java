package com.beegroove.turrets;

import com.beegroove.turrets.StarShip.STYPE;
import com.beegroove.turrets.Turret.TTYPE;

public class SpaceshipFactory {
	
	static StarShip NewSingleBasicSpaceship()
	{
		return NewSingleBasicSpaceship(new StarShip());
	}
	static StarShip NewSingleBasicSpaceship(StarShip ret)
	{
		ret.mEnergy_Initial = Par.BASIC_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_1;
		ret.mMaxSpeed=Par.BASIC_SHIP_SPEED;
		ret.turrets.clear();
		ret.mSize = 2;
		ret.mPosition.set(Par.SPACESHIP_BASIC_POSITION);
		ret.turrets.add(NewSingleSmallTurret());
		ret.type = STYPE.BASIC;

		return ret;	
	}
	
	
	static StarShip NewDoubleBasicSpaceship()
	{
		return NewDoubleBasicSpaceship(new StarShip());
	}
	static StarShip NewDoubleBasicSpaceship(StarShip ret)
	{
		ret.mEnergy_Initial = Par.BASIC_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_2;
		ret.mMaxSpeed=Par.BASIC_SHIP_SPEED;
		ret.turrets.clear();
		ret.mSize = 2;
		ret.mPosition.set(Par.SPACESHIP_BASIC_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.BASIC_DOUBLE;

		return ret;	
	}
	
	static StarShip NewSingleStandardSpaceship()
	{
		return NewSingleStandardSpaceship(new StarShip());
	}
	static StarShip NewSingleStandardSpaceship(StarShip ret)
	{
		ret.mEnergy_Initial = Par.STANDARD_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_3;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewSingleSmallTurret());
		ret.turrets.add(NewSingleSmallTurret());
		ret.type = STYPE.STANDARD;
		ret.mMaxSpeed=Par.STANDARD_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MIN;		
		
		return ret;	
	}
	
	static StarShip NewDoubleStandardSpaceship()
	{
		return NewDoubleStandardSpaceship(new StarShip());
	}
	static StarShip NewDoubleStandardSpaceship(StarShip ret)
	{
		ret.mEnergy_Initial = Par.STANDARD_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_4;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.STANDARD_DOUBLE;
		ret.mMaxSpeed=Par.STANDARD_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MIN;		
		return ret;	
	}
	

	static StarShip NewDoubleAdvancedSpaceship()
	{
		return NewDoubleAdvancedSpaceship(new StarShip());
	}
	static StarShip NewDoubleAdvancedSpaceship(StarShip ret)
	{
		ret.mEnergy_Initial = Par.ADVANCED_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_5;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.ADVANCED_DOUBLE;
		ret.mMaxSpeed=Par.ADVANCED_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=Par.SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=Par.SPACESHIP_ADVANCED_POSITION_TURRET_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=Par.SPACESHIP_ADVANCED_POSITION_TURRET_RIGHT_HEADING_MIN;
		ret.turrets.get(2).mPosition.add(Par.SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_CORRECTION);
		ret.turrets.get(2).mHeadingMax=Par.SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_HEADING_MAX;
		ret.turrets.get(2).mHeadingMin=Par.SPACESHIP_ADVANCED_POSITION_TURRET_CENTER_HEADING_MIN;
		return ret;	
	}

	static StarShip NewDoubleGunShipSpaceship()
	{
	return NewDoubleGunShipSpaceship(new StarShip());
	}
	static StarShip NewDoubleGunShipSpaceship(StarShip ret)
	{
		ret.mEnergy_Initial = Par.GUNSHIP_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_6;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.GUNSIHP_DOUBLE;
		ret.mMaxSpeed=Par.GUNSHIP_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_HEADING_MIN;
		ret.turrets.get(2).mPosition.add(Par.SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_CORRECTION);
		ret.turrets.get(2).mHeadingMax=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_HEADING_MAX;
		ret.turrets.get(2).mHeadingMin=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_CENTER_HEADING_MIN;
		ret.turrets.get(3).mPosition.add(Par.SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_CORRECTION);
		ret.turrets.get(3).mHeadingMax=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_HEADING_MAX;
		ret.turrets.get(3).mHeadingMin=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_LEFT_BACK_HEADING_MIN;
		ret.turrets.get(4).mPosition.add(Par.SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_CORRECTION);
		ret.turrets.get(4).mHeadingMax=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_HEADING_MAX;
		ret.turrets.get(4).mHeadingMin=  Par.SPACESHIP_GUNSHIP_POSITION_TURRET_RIGHT_BACK_HEADING_MIN;
		return ret;	
	}

	static StarShip NewDoubleBattleCrusierSpaceship()
	{
	return NewDoubleBattleCrusierSpaceship(new StarShip());
	}
	static StarShip NewDoubleBattleCrusierSpaceship(StarShip ret)
	{
		ret.IsTheLast = true;
		ret.mEnergy_Initial = Par.BATTLECRIUSER_SHIP_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.mNextTo = Par.Level_7;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(Par.SPACESHIP_STANDARD_POSITION);
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.turrets.add(NewDoubleSmallTurret());
		ret.type = STYPE.BATTLECRUISER;
		ret.mMaxSpeed=Par.BATTLECRIUSER_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_1_RIGHT_HEADING_MIN;
		ret.turrets.get(2).mPosition.add(Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_LEFT_CORRECTION);
		ret.turrets.get(2).mHeadingMax=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_LEFT_HEADING_MAX;
		ret.turrets.get(2).mHeadingMin=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_LEFT_HEADING_MIN;
		ret.turrets.get(3).mPosition.add(Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_RIGHT_CORRECTION);
		ret.turrets.get(3).mHeadingMax=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_RIGHT_HEADING_MAX;
		ret.turrets.get(3).mHeadingMin=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_2_RIGHT_HEADING_MIN;
		ret.turrets.get(4).mPosition.add(Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_LEFT_CORRECTION);
		ret.turrets.get(4).mHeadingMax=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_LEFT_HEADING_MAX;
		ret.turrets.get(4).mHeadingMin=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_LEFT_HEADING_MIN;
		ret.turrets.get(5).mPosition.add(Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_RIGHT_CORRECTION);
		ret.turrets.get(5).mHeadingMax=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_RIGHT_HEADING_MAX;
		ret.turrets.get(5).mHeadingMin=  Par.SPACESHIP_BATTLECRUISER_POSITION_TURRET_3_RIGHT_HEADING_MIN;
		
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
