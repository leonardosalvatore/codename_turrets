package com.beegroove.turrets;

public class StateMachine {

	private static STATE mCurrentState;
	private static STATE mPreviousState;

	private static long mEnterInCurrentState =0;
	private static long mTimeInPreviousState =0;
	
	enum STATE{
		INTRO,
		PLAY,
		PAUSE,
		GAMEOVER
	}
	
	TGdxGame mGame;
	
	public static void SetNextState(StateMachine.STATE s)
	{
		mTimeInPreviousState = System.currentTimeMillis() - mEnterInCurrentState;
		mEnterInCurrentState = System.currentTimeMillis();
		mPreviousState = mCurrentState;
		mCurrentState = s;
	}
	
	public static void BackToThePreviousState()
	{
		SetNextState(mPreviousState);
	}
	
	public static STATE GetPreviousState()
	{
		return mPreviousState;
	}
	
	public static STATE GetCurrentState()
	{
		return mCurrentState;
	}
	
	public static long GetTimeInCurrentState()
	{
		return System.currentTimeMillis() - mEnterInCurrentState ;
	}
	
	public static long GetTimeInPreviousState()
	{
		return mTimeInPreviousState;
	}
}
