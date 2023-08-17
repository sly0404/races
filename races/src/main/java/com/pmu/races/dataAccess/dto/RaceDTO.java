package com.pmu.races.dataAccess.dto;

import java.util.Date;
import java.util.List;

import com.pmu.races.controllers.beans.Horse;

/**
	Data Transfer Object to represent a race and a list of horse to associate to the race.
	Automatically serialized from REST API service.
*/
public class RaceDTO
{
	private Date day;
	private String name;
	private int daily_id;
	private List<Integer> horseIds;
	
	public Date getDay() 
	{
		return day;
	}
	
	public void setDay(Date day) 
	{
		this.day = day;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public int getDaily_id()
	{
		return daily_id;
	}

	public void setDaily_id(int daily_id)
	{
		this.daily_id = daily_id;
	}

	public List<Integer> getHorseIds() 
	{
		return horseIds;
	}

	public void setHorseIds(List<Integer> horseIds) 
	{
		this.horseIds = horseIds;
	}
}
