package com.pmu.races.dataAccess;

import org.springframework.data.repository.CrudRepository;

import com.pmu.races.controllers.beans.HorseAndPosition;

public interface HorseAndPositionRepository extends CrudRepository<HorseAndPosition, Integer> 
{

}
