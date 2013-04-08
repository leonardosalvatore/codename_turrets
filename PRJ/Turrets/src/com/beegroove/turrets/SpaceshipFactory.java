package com.beegroove.turrets;

import com.badlogic.gdx.math.Vector3;
import com.beegroove.turrets.SpaceShip.STYPE;
import com.beegroove.turrets.Turret.TTYPE;

public class SpaceshipFactory {
	
	static SpaceShip NewSingleBasicSpaceship(SpaceShip ret)
	{
		ret.mNextTo = Par.Level_1;
		ret.mMaxSpeed=Par.BASIC_SHIP_SPEED;
		ret.turrets.clear();
		ret.mSize = 2;
		ret.mPosition.set(Par.SPACESHIP_INITIAL_POSITION);
		ret.turrets.add(NewSingleSmallTurret(ret.mPosition));
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.type = STYPE.BASIC;

		return ret;	
	}
	
	
	static SpaceShip NewDoubleBasicSpaceship(SpaceShip ret)
	{
		ret.mNextTo = Par.Level_2;
		ret.mMaxSpeed=Par.BASIC_SHIP_SPEED;
		ret.turrets.clear();
		ret.mSize = 2;
		ret.mPosition.set(ret.mPosition);
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		ret.type = STYPE.BASIC_DOUBLE;

		return ret;	
	}
	
	
	static SpaceShip NewSingleStandardSpaceship(SpaceShip ret)
	{
		ret.mNextTo = Par.Level_3;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(ret.mPosition);
		ret.turrets.add(NewSingleSmallTurret(ret.mPosition));
		ret.turrets.add(NewSingleSmallTurret(ret.mPosition));
		ret.type = STYPE.STANDARD;
		ret.mMaxSpeed=Par.STANDARD_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MIN;		
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		
		return ret;	
	}
	
	
	static SpaceShip NewDoubleStandardSpaceship(SpaceShip ret)
	{
		ret.mNextTo = Par.Level_4;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(ret.mPosition);
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.type = STYPE.STANDARD_DOUBLE;
		ret.mMaxSpeed=Par.STANDARD_SHIP_SPEED;
		
		ret.turrets.get(0).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_CORRECTION);
		ret.turrets.get(0).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MAX;
		ret.turrets.get(0).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_LEFT_HEADING_MIN;
		ret.turrets.get(1).mPosition.add(Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_CORRECTION);
		ret.turrets.get(1).mHeadingMax=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MAX;
		ret.turrets.get(1).mHeadingMin=Par.SPACESHIP_STANDARD_POSITION_TURRET_RIGHT_HEADING_MIN;		
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		return ret;	
	}
	

	static SpaceShip NewDoubleAdvancedSpaceship()
	{
		return NewDoubleAdvancedSpaceship(new SpaceShip());
	}
	static SpaceShip NewDoubleAdvancedSpaceship(SpaceShip ret)
	{
		ret.mNextTo = Par.Level_5;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(ret.mPosition);
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
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
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		return ret;	
	}

	static SpaceShip NewDoubleGunShipSpaceship(SpaceShip ret)
	{
		ret.mNextTo = Par.Level_6;
		ret.turrets.clear();
		ret.mSize = 4;
		ret.mPosition.set(ret.mPosition);
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
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
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		return ret;	
	}

	static SpaceShip NewDoubleBattleCrusierSpaceship(SpaceShip ret)
	{
		ret.IsTheLast = true;
		
		ret.mNextTo = Par.Level_7;
		ret.turrets.clear();
		ret.mSize = 3;
		ret.mPosition.set(ret.mPosition);
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		ret.turrets.add(NewDoubleSmallTurret(ret.mPosition));
		
		ret.mEnergy_Initial = ret.turrets.size()*Par.TURRET_ENERGY;
		ret.mEnergy = ret.mEnergy_Initial;
		
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
	
	private static Turret NewSingleSmallTurret(Vector3 position)
	{
		Turret t = new Turret();
		t.mPosition.set(position);
		t.type = TTYPE.SINGLE_SMALL;
		t.mEnergy = Par.TURRET_ENERGY;
		return t;
	}
	
	
	private static Turret NewDoubleSmallTurret(Vector3 position)
	{
		Turret t = new Turret();
		t.mPosition.set(position);
		t.type = TTYPE.DOUBLE_SMALL;
		t.mEnergy = Par.TURRET_ENERGY ;
		return t;
	}
}
