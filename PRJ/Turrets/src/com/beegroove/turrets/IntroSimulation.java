package com.beegroove.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.PhysicItem.TASK_TYPE;
import com.beegroove.turrets.StateMachine.STATE;

public class IntroSimulation extends Simulation {
	public PhysicItem moon;
	public Array<Asteroid> meteorites = new Array<Asteroid>();
	public Array<PhysicItem> explosions = new Array<PhysicItem>();

	public IntroSimulation() {
		super();
		
		moon = new PhysicItem();

		moon.mPosition.set(Par.MOON_POSITION);
		moon.mRotationSpeed.setEulerAngles(0, 0, Par.MOON_ROTATION_SPEED);
		
		Asteroid single = new Asteroid();
		single.mDirection.set(Vector3.Zero);
		single.mPosition.set(Par.MOON_POSITION.tmp().add(0, 20, 0f));
		single.mSpeed.set(new Vector3(0,-9f,-1f));
		single.mRotation.setEulerAngles(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		meteorites.add(single);
		
		for (int k = 0; k <= Par.MOON_METEORITES_NUMBER; k++) {
			Asteroid tmp = new Asteroid();

			tmp.mPosition.set(Par.MOON_POSITION);
			tmp.mRotation.setEulerAngles(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
			//tmp.mRotationSpeed.setEulerAngles(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
			tmp.scheduleTask(TASK_TYPE.SLEEP, Vector3.Zero,
					(int) Par.MOON_METEORITES_SLEEP, false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.SPEED,
					new Vector3((rand.nextFloat() - 0.5f)*Par.MOON_METEORITES_SPEED,
							(rand.nextFloat() - 0.5f)*Par.MOON_METEORITES_SPEED, 
							(rand.nextFloat() - 0.5f)*Par.MOON_METEORITES_SPEED),
					(int) Par.MOON_EXPLOSION, false, 0, 0);
			meteorites.add(tmp);
		}
		
		launchIntroAnimation();
	}
	public void update(float delta) {

		if(Gdx.input.isTouched())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			
			Gdx.app.log("T", "X:"+x+" Y:"+y);
			
			if((x >= Par.SETTINGS_AUDIO_X && x < (Par.SETTINGS_AUDIO_X+Par.SETTINGS_SIZE_X))&&
					(y >= Par.VIEWPORT_MAX_Y-Par.SETTINGS_AUDIO_Y && y < ((Par.VIEWPORT_MAX_Y-Par.SETTINGS_AUDIO_Y) +Par.SETTINGS_SIZE_Y)))
			{
				Par.SETTINGS_AUDIO = !Par.SETTINGS_AUDIO;
			}
			else if((x >= Par.SETTINGS_VIBRA_X && x < (Par.SETTINGS_VIBRA_X+Par.SETTINGS_SIZE_X))&&
					(y >= Par.VIEWPORT_MAX_Y-Par.SETTINGS_VIBRA_Y && y < ((Par.VIEWPORT_MAX_Y-Par.SETTINGS_VIBRA_Y) +Par.SETTINGS_SIZE_Y)))
			{
				Par.SETTINGS_VIBRA = !Par.SETTINGS_VIBRA;
			}
			else if((x >= Par.SETTINGS_FX_X && x < (Par.SETTINGS_FX_X+Par.SETTINGS_SIZE_X))&&
					(y >= Par.VIEWPORT_MAX_Y-Par.SETTINGS_FX_Y && y < ((Par.VIEWPORT_MAX_Y-Par.SETTINGS_FX_Y) +Par.SETTINGS_SIZE_Y)))
			{
				Par.SETTINGS_FX = !Par.SETTINGS_FX;
			}
			else if ((x >= Par.START_X && x < (Par.START_X+Par.SETTINGS_SIZE_X))&& 
				(y >= Par.VIEWPORT_MAX_Y-Par.START_Y && y < ((Par.VIEWPORT_MAX_Y-Par.START_Y) +Par.SETTINGS_SIZE_Y)))
			{
				Par.SaveSettings();
				StateMachine.SetNextState(STATE.PLAY,new PlayScreen(Par.Level_Start));
			}
		}
		
		HUD.Instance().Update(delta);
		mCameraMan.Update(delta);
		moon.Update(delta);

		for (Asteroid en : meteorites) {
			en.Update(delta);
		}
		
		for (PhysicItem ex : explosions) {
			ex.Update(delta);
		}
			

	}
	
	public boolean gameAnimationIntroLaunched = false;
	public void launchIntroAnimation()
	{
		if(gameAnimationIntroLaunched)
		{
			return;
		}
		gameAnimationIntroLaunched=true;
		
		PhysicItem single = new PhysicItem();
		single.mScreenPosition.x = (int) Par.VIEWPORT_MAX_X/2;
		single.mScreenPosition.y = (int) Par.VIEWPORT_MAX_Y/2;
		single.mSize=0f;
		single.scheduleTask(TASK_TYPE.SLEEP, new Vector3(0.0f, 0, 0), (int) ((Par.MOON_DURATION/10)-120), false, 0, 0);
		single.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(rand.nextFloat()/3+0.5f, 0, 0), 50, false, 0, 0);
		single.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(rand.nextFloat()/3+0.5f, 0, 0), 400, false, 0, 0);
		explosions.add(single);
		
		for(int k=0;k<=10;k++)
		{
			PhysicItem tmp = new PhysicItem();
			tmp.mScreenPosition.x = rand.nextInt((int) Par.VIEWPORT_MAX_X);
			tmp.mScreenPosition.y = rand.nextInt((int) Par.VIEWPORT_MAX_Y);
			tmp.mSize=0f;
			tmp.scheduleTask(TASK_TYPE.SLEEP, new Vector3(0.0f, 0, 0), (int) (Par.MOON_DURATION/10), false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.SIZE_UP, new Vector3(rand.nextFloat()/3+0.5f, 0, 0), 50, false, 0, 0);
			tmp.scheduleTask(TASK_TYPE.SIZE_DOWN, new Vector3(rand.nextFloat()/3+0.5f, 0, 0), 400, false, 0, 0);
			explosions.add(tmp);
		}
	}	
}
