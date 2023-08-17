package com.pmu.races.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pmu.races.controllers.beans.Horse;
import com.pmu.races.controllers.beans.HorseAndPosition;
import com.pmu.races.controllers.beans.Race;
import com.pmu.races.controllers.beans.RaceId;
import com.pmu.races.dataAccess.HorseAndPositionRepository;
import com.pmu.races.dataAccess.HorseRepository;
import com.pmu.races.dataAccess.RaceRepository;
import com.pmu.races.dataAccess.dao.HorseAndPositionDAO;
import com.pmu.races.dataAccess.dao.HorseAndPositionDAOImplementation;
import com.pmu.races.dataAccess.dao.HorseDAO;
import com.pmu.races.dataAccess.dao.HorseDAOImplementation;
import com.pmu.races.dataAccess.dao.RaceDAO;
import com.pmu.races.dataAccess.dao.RaceDAOImplementation;
import com.pmu.races.dataAccess.dto.HorseDTO;
import com.pmu.races.dataAccess.dto.RaceDTO;
import com.pmu.races.streams.StreamMessage;

/**
	Rest Controller that manage creation, update for horses and races

*/
@RestController
public class RacesController
{
	@Autowired
	private HorseRepository horseRepository;
	@Autowired
	private RaceRepository raceRepository;
	@Autowired
	private HorseAndPositionRepository horseAndPositionRepository;
	
	private HorseDAO horseDAO;
	private RaceDAO raceDAO;
	private HorseAndPositionDAO horseAndPositionDAO;
	private final static String TOPIC = "races-bus";

	private KafkaTemplate<String, String> template;
	
	public RacesController(KafkaTemplate<String, String> template) 
	{
		String bootstrapServers = "pkc-e0zxq.eu-west-3.aws.confluent.cloud:9092";
		Map<String,Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<String, String>(config);
        this.template = new KafkaTemplate<String, String>(factory);
    }
	
	/**
	  * create horse
	  * 
	  * @param horseDTO
	  * @return JSON flow that represents the horse
	  */
	@PostMapping("/horse/create")
	public @ResponseBody Horse createHorse(@RequestBody HorseDTO horseDTO)
	{
		HorseDAO horseDAO = getHorseDAOInstance();
		Horse horse = new Horse();
		horse.setName(horseDTO.getName());
		return horseDAO.saveHorse(horse);
	}
	
	/**
	  * create or update a race with a list of horses and their positions in the race
	  * 
	  * @param raceDTO
	  * 		DTO representation of the race and the horses
	  * @return JSON flow that represents the newly or updated race
	  * @throws Exception if the race contains strictly less than 3 horses
	  */
	@PostMapping("/race/createOrUpdate")
	public @ResponseBody Race createOrUpdateRace(@RequestBody RaceDTO raceDTO) throws Exception
	{
		RaceDAO raceDAO = getRaceDAOInstance();
		Race resultRace = null;
		RaceId raceId = Utils.extractRaceId(raceDTO);
		Optional<Race> raceOptional = raceDAO.getById(raceId);
		if (raceOptional.isPresent())
		{
			Race race = raceOptional.get();
			List<HorseAndPosition> horseAndPositionList = race.getHorseAndPositionList();
			int horseAndPositionListSize = horseAndPositionList.size();
			int i = 1;
			for (Integer horseId : raceDTO.getHorseIds())
			{
				HorseDAO horseDAO = getHorseDAOInstance();
				Horse horse = horseDAO.getById(horseId).get();
				HorseAndPosition horseAndPosition = new HorseAndPosition();
				horseAndPosition.setHorse(horse);
				horseAndPosition.setRacePosition(i+horseAndPositionListSize);
				HorseAndPositionDAO horseAndPositionDAO = getHorseAndPositionDAOInstance();
				horseAndPositionDAO.saveHorseAndPosition(horseAndPosition);
				horseAndPositionList.add(horseAndPosition);
			}
			race.setRaceId(raceId);
			race.setHorseAndPositionList(horseAndPositionList);
			resultRace = raceDAO.saveRace(race);
		}
		else
		{
			if (raceDTO.getHorseIds().size() >= 3)
			{
				Race race = new Race();
				List<HorseAndPosition> horseAndPositionList = new ArrayList<HorseAndPosition>();
				int i = 1;
				for (Integer horseId : raceDTO.getHorseIds())
				{
					HorseDAO horseDAO = getHorseDAOInstance();
					Horse horse = horseDAO.getById(horseId).get();
					HorseAndPosition horseAndPosition = new HorseAndPosition();
					horseAndPosition.setHorse(horse);
					horseAndPosition.setRacePosition(i);
					HorseAndPositionDAO horseAndPositionDAO = getHorseAndPositionDAOInstance();
					horseAndPositionDAO.saveHorseAndPosition(horseAndPosition);
					horseAndPositionList.add(horseAndPosition);
					i++;
				}
				race.setRaceId(raceId);
				race.setHorseAndPositionList(horseAndPositionList);
				resultRace = raceDAO.saveRace(race);
			}
			else
				throw new Exception("race must have at leat 3 horses!");
		}	
		return resultRace;
	}
	
	/**
	  * show race informations
	  * 
	  * @param raceDTO
	  * @return JSON flow that represents the race
	  */
	@GetMapping("/race/show")
	public @ResponseBody Optional<Race> showRace(@RequestBody RaceDTO raceDTO)
	{
		RaceId raceId = Utils.extractRaceId(raceDTO);
		RaceDAO raceDAO = getRaceDAOInstance();
		return raceDAO.getById(raceId);
		
	}
	
	/**
	  * send message to Kafka topic
	  * 
	  * @param streamMessage
	  * 		message to send to topic
	  */
	@PostMapping("/kafka/send")
    public void sendMessage(@RequestBody Race race)
	{
        template.send(TOPIC, race.toString());
    }
	
	/**
	  * give access to Horse DAO instance
	  * 
	  * @param
	  * @return Horse DAO instance
	  */
	public HorseDAO getHorseDAOInstance()
	{
		if (horseDAO == null)
			horseDAO = new HorseDAOImplementation(horseRepository);
		return horseDAO; 
	}
	
	/**
	  * sgive access to Race DAO instance
	  * 
	  * @param
	  * @return Race DAO instance
	  */
	public RaceDAO getRaceDAOInstance()
	{
		if (raceDAO == null)
			raceDAO = new RaceDAOImplementation(raceRepository);
		return raceDAO; 
	}
	
	public HorseAndPositionDAO getHorseAndPositionDAOInstance()
	{
		if (horseAndPositionDAO == null)
			horseAndPositionDAO = new HorseAndPositionDAOImplementation(horseAndPositionRepository);
		return horseAndPositionDAO; 
	}
}
