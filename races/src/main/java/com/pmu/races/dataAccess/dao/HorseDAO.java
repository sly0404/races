package com.pmu.races.dataAccess.dao;

import java.util.Optional;

import com.pmu.races.controllers.beans.Horse;

/**
	DAO that manage database operations on horses

*/
public interface HorseDAO 
{	
	public Optional<Horse> getById(int horseId);
	public Horse saveHorse(Horse horse);
}
