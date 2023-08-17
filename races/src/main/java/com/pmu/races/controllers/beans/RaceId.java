package com.pmu.races.controllers.beans;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
Represents the composite primary key of a race

*/
@Embeddable
public class RaceId 
{
	private Date day;
	private String name;
	private int daily_id;
	
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

	@Override
	public int hashCode() 
	{
		return Objects.hash(daily_id, day, name);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RaceId other = (RaceId) obj;
		return daily_id == other.daily_id && Objects.equals(day, other.day) && Objects.equals(name, other.name);
	}
}
