package com.pmu.races.dataAccess;

import org.springframework.data.repository.CrudRepository;

import com.pmu.races.controllers.beans.Race;
import com.pmu.races.controllers.beans.RaceId;

public interface RaceRepository extends CrudRepository<Race, RaceId> 
{
	
}
