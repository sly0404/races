package com.pmu.races.dataAccess.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pmu.races.controllers.beans.Race;
import com.pmu.races.controllers.beans.RaceId;
import com.pmu.races.dataAccess.RaceRepository;

@Component
public class RaceDAOImplementation implements RaceDAO 
{

	private RaceRepository raceRepository;
	
	public RaceDAOImplementation(RaceRepository raceRepository)
	{
		this.raceRepository = raceRepository;
	}
	
	@Override
	public Optional<Race> getById(RaceId raceId) 
	{
		return raceRepository.findById(raceId);
	}

	@Override
	public Race saveRace(Race race)
	{
		return raceRepository.save(race);
	}
	/*
	@Override
	public Race updateRace(Race race)
	{
		return raceRepository.(race);
	}*/
}
