package com.pmu.races.dataAccess.dao;

import java.util.Optional;

import com.pmu.races.controllers.beans.HorseAndPosition;
import com.pmu.races.dataAccess.HorseAndPositionRepository;
import com.pmu.races.dataAccess.RaceRepository;

public class HorseAndPositionDAOImplementation implements HorseAndPositionDAO 
{
private HorseAndPositionRepository horseAndPositionRepository;
	
	public HorseAndPositionDAOImplementation(HorseAndPositionRepository horseAndPositionRepository)
	{
		this.horseAndPositionRepository = horseAndPositionRepository;
	}

	@Override
	public Optional<HorseAndPosition> getById(int horseAndPositionId) 
	{
		return horseAndPositionRepository.findById(horseAndPositionId);
	}

	@Override
	public HorseAndPosition saveHorseAndPosition(HorseAndPosition horseAndPosition) 
	{
		return horseAndPositionRepository.save(horseAndPosition);
	}

}
