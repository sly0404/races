package com.pmu.races.dataAccess.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pmu.races.controllers.beans.Horse;
import com.pmu.races.dataAccess.HorseRepository;

@Component
public class HorseDAOImplementation implements HorseDAO 
{
	private HorseRepository horseRepository;
	
	public HorseDAOImplementation(HorseRepository horseRepository)
	{
		this.horseRepository = horseRepository;
	}

	@Override
	public Optional<Horse> getById(int horseId) 
	{
		return horseRepository.findById(horseId);
	}

	@Override
	public Horse saveHorse(Horse horse) 
	{
		return horseRepository.save(horse);
	}

}
