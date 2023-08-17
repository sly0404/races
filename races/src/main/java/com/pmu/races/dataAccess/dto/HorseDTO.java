package com.pmu.races.dataAccess.dto;

/**
Data Transfer Object to represent a horse.
Automatically serialized from REST API service.
*/
public class HorseDTO 
{
	private String name;

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
}
