package com.pmu.races.dataAccess;

import org.springframework.data.repository.CrudRepository;

import com.pmu.races.controllers.beans.Horse;

public interface HorseRepository extends CrudRepository<Horse, Integer> 
{
	
}
