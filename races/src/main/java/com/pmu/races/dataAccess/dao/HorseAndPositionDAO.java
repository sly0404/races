package com.pmu.races.dataAccess.dao;

import java.util.Optional;

import com.pmu.races.controllers.beans.HorseAndPosition;

/**
DAO that manage database operations on horseAndPosition

*/
public interface HorseAndPositionDAO
{
	public Optional<HorseAndPosition> getById(int horseAndPositionId);
	public HorseAndPosition saveHorseAndPosition(HorseAndPosition horseAndPosition);
}
