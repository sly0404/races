package com.pmu.races.dataAccess.dao;

import java.util.Optional;

import com.pmu.races.controllers.beans.Horse;
import com.pmu.races.controllers.beans.Race;
import com.pmu.races.controllers.beans.RaceId;

/**
DAO that manage database operations on races

*/
public interface RaceDAO 
{
	public Optional<Race> getById(RaceId raceId);
	public Race saveRace(Race race);
/*	public Race updateRace(Race race);*/
}
