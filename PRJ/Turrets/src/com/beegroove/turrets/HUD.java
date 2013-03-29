package com.beegroove.turrets;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.beegroove.turrets.HUD.Message;

public class HUD {
	
	private static HUD instance = null;
	private Message titleMsg;
	private Message introMsg;
	private Message gameoverMsg;
	
	protected HUD() {
		statusBarMsg = new Message();
		statusBarMsg.mPosition = new Vector3(10,Par.LARGE_FONT_SIZE,0);
		countDownMsg = new Message();
		countDownMsg.mPosition = new Vector3(10,Par.VIEWPORT_MAX_Y-Par.LARGE_FONT_SIZE,0);
		energyMsg = new Message();
		energyMsg.mPosition = new Vector3(Par.VIEWPORT_MAX_X/2,Par.VIEWPORT_MAX_Y-Par.LARGE_FONT_SIZE,0);
		titleMsg = new Message();
		titleMsg.mPosition = new Vector3(Par.MAIN_TITLE_FONT_SIZE,Par.VIEWPORT_MAX_Y-Par.MAIN_TITLE_FONT_SIZE,0);
		introMsg = new Message();
		introMsg.mPosition = new Vector3(Par.MAIN_TITLE_FONT_SIZE,Par.VIEWPORT_MAX_Y-3*Par.MAIN_TITLE_FONT_SIZE,0);
		gameoverMsg = new Message();
		gameoverMsg.mPosition = new Vector3(Par.MAIN_TITLE_FONT_SIZE,Par.VIEWPORT_MAX_Y-3*Par.MAIN_TITLE_FONT_SIZE,0);
			
	}

	public static HUD Instance() {
		if (instance == null) {
			instance = new HUD();
		}
		return instance;
	}
	
	class Message extends PhysicItem{
		String msg;
		double creationTime;
		double durationTime;
	}
	
	BitmapFont font;
	public Array<Message> messages = new Array<Message>();
	public Array<Message> messagesRoller = new Array<Message>();


	public void Update(float deltaTime)
	{		
		for (Iterator<Message> iterator = messages.iterator(); iterator.hasNext();) {
			Message m = (Message) iterator.next();
			
			if(m.creationTime + m.durationTime > System.currentTimeMillis())
			{
				m.Update(deltaTime);
			}
			else
			{
				iterator.remove();
			}
		}
		
		for (Iterator<Message> iteratorRoller = messagesRoller.iterator(); iteratorRoller.hasNext();) {
			Message m = (Message) iteratorRoller.next();
			
			if(m.creationTime + m.durationTime > System.currentTimeMillis())
			{
				m.Update(deltaTime);
			}
			else
			{
				iteratorRoller.remove();
			}
		}
	}

	private Message statusBarMsg;
	public Message GetStatusBar(int wave,int score,int nextat)
	{
		statusBarMsg.msg = String.format("         SCORE:%d/%d  WAVE:%d  ",score,nextat,wave);
		return statusBarMsg;
	}

	private Message countDownMsg;
	public Message GetCountDown(int countDown )
	{
		int sec = countDown/60;
		countDownMsg.msg = String.format("COUNTDOWN:%d.%d", sec,countDown%60);
		return countDownMsg;
	}
	
	private Message energyMsg;	
	public Message GetEnergy(int mEnergy) {
		energyMsg.msg = String.format("ENERGY:%d", mEnergy);
		return energyMsg;
	}


	public void NewMessageRoller(String s)
	{
		Message m = new Message();
		
		m.mSpeed = Vector3.X.cpy().mul(-150f);
		m.durationTime = 20000;
		m.mPosition = new Vector3(1500,Par.LARGE_FONT_SIZE*2,0);
		m.creationTime=System.currentTimeMillis();
		m.msg = s;
		
		messagesRoller.add(m);
	}
	
	public void NewMessage(String s, Vector3 position)
	{
		Message m = new Message();
		m.mSpeed = Vector3.Z.cpy().mul(-100f);
		m.mPosition.set(position);
		m.creationTime=System.currentTimeMillis();
		m.msg = s;
		m.durationTime = 1000;
		messages.add(m);
	}

	public void NewMessage(String s, Vector3 position, int duration)
	{
		Message m = new Message();
		m.mSpeed = Vector3.Z.cpy().mul(-100f);
		m.mPosition.set(position);
		m.creationTime=System.currentTimeMillis();
		m.msg = s;
		m.durationTime = duration;
		messages.add(m);
	}
	
	public Message GetMainTitleMessage()
	{
		titleMsg.msg = Par.TITLE_MSG;
		return titleMsg;
	}
	public Message GetIntroMessage()
	{
		introMsg.msg = Par.INTRO_MSG;
		return introMsg;
	}
	
	public Array<Message> GetMessage()
	{
		return messages;
	}
	
	public Array<Message> GetMessageRoller()
	{
		return messagesRoller;
	}

	public Message GetMainGameOverMessage() {
		gameoverMsg.msg = Par.GAMEOVER_MSG;
		return gameoverMsg;
	}

	

}
