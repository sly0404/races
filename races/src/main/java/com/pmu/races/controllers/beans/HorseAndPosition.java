package com.pmu.races.controllers.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

/**
	Represents a mapping between horse and position

*/
@Entity
public class HorseAndPosition 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Horse horse;
	
	private int racePosition;

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Horse getHorse() 
	{
		return horse;
	}

	public void setHorse(Horse horse) 
	{
		this.horse = horse;
	}

	public int getRacePosition() 
	{
		return racePosition;
	}

	public void setRacePosition(int racePosition) 
	{
		this.racePosition = racePosition;
	}
}
