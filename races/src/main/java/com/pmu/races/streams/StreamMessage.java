package com.pmu.races.streams;

public class StreamMessage 
{	
	private int id;
	private String message;
	
	public StreamMessage(int id, String message)
	{
		this.id = id;
		this.message = message;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getMessage() 
	{
		return message;
	}
	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	
}
