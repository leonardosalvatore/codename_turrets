package com.beegroove.turrets;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class HUD {
	
	private static HUD instance = null;

	protected HUD() {
		statusBar = new Message();
		statusBar.mPosition = new Vector3(Par.VIEWPORT_MAX_X/3,20,0);
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

	private Message statusBar;
	public Message GetStatusBar(int score,int missed,int energy )
	{
		statusBar.msg = "SCORE:" + score + "  MISSED:" +missed + " ENERGY:" + energy;
		return statusBar;
	}
	
	public void NewMessageRoller(String s)
	{
		Message m = new Message();
		
		m.mSpeed = Vector3.X.mul(-150f);
		m.durationTime = 20000;
		m.mPosition = new Vector3(1500,Par.VIEWPORT_MAX_Y,0);
		m.creationTime=System.currentTimeMillis();
		m.msg = s;
		
		messagesRoller.add(m);
	}
	
	public void NewMessage(String s, Vector3 position)
	{
		Message m = new Message();
		m.mSpeed = Vector3.Z.mul(-100f);
		m.mPosition = position;
		m.creationTime=System.currentTimeMillis();
		m.msg = s;
		m.durationTime = 1000;
		messages.add(m);
	}

	
	public Array<Message> GetMessage()
	{
		return messages;
	}
	
	public Array<Message> GetMessageRoller()
	{
		return messagesRoller;
	}

}
