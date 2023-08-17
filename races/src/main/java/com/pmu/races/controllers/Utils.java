package com.pmu.races.controllers;

import com.pmu.races.controllers.beans.RaceId;
import com.pmu.races.dataAccess.dto.RaceDTO;

public class Utils 
{
	public static RaceId extractRaceId(RaceDTO raceDTO)
	{
		RaceId raceId = new RaceId();
		raceId.setDay(raceDTO.getDay());
		raceId.setName(raceDTO.getName());
		raceId.setDaily_id(raceDTO.getDaily_id());
		return raceId;
	}
}
