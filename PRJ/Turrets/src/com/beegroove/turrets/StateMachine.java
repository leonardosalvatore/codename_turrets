package com.beegroove.turrets;

public class StateMachine {

	private static TGdxGame mGame;

	private static STATE mCurrentState;
	private static STATE mPreviousState;
	private static GenericScreen mPreviousScreen;

	private static long mEnterInCurrentState =0;
	private static long mTimeInPreviousState =0;
	
	enum STATE{
		INTRO,
		PLAY,
		PAUSE,
		GAMEOVER
	}
	
	public static void SetGame(TGdxGame game)
	{
		mGame = game;
	}
	
	public static void SetNextState(StateMachine.STATE s, GenericScreen screen)
	{
		mTimeInPreviousState = System.currentTimeMillis() - mEnterInCurrentState;
		mEnterInCurrentState = System.currentTimeMillis();
		mPreviousState = mCurrentState;
		mPreviousScreen = screen;
		mCurrentState = s;
		mGame.setScreen(screen);
	}
	
	public static void BackToThePreviousState()
	{
		SetNextState(mPreviousState,mPreviousScreen);
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
