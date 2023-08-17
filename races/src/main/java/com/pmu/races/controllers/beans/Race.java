package com.pmu.races.controllers.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.MapsId;


/**
 	Represents a race of horses and their positions in the race
 
 */
@Entity
public class Race implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RaceId raceId;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
            name = "race_with_horses",
            joinColumns = {
            	    @JoinColumn(name="day", referencedColumnName="day"),
            	    @JoinColumn(name="name", referencedColumnName="name"),
            	    @JoinColumn(name="daily_id", referencedColumnName="daily_id")
            	},
            inverseJoinColumns = @JoinColumn(name = "horse_and_position_id")
            )
	private List<HorseAndPosition> horseAndPositionList;
	
	public List<HorseAndPosition> getHorseAndPositionList() 
	{
		return horseAndPositionList;
	}

	public void setHorseAndPositionList(List<HorseAndPosition> horseAndPositionList) 
	{
		this.horseAndPositionList = horseAndPositionList;
	}

	public RaceId getRaceId() 
	{
		return raceId;
	}

	public void setRaceId(RaceId raceId) 
	{
		this.raceId = raceId;
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(horseAndPositionList, raceId);
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
		Race other = (Race) obj;
		return Objects.equals(horseAndPositionList, other.horseAndPositionList) && Objects.equals(raceId, other.raceId);
	}
	
	
}
