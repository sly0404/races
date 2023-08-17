package com.pmu.races;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import com.pmu.races.controllers.beans.Horse;
import com.pmu.races.dataAccess.HorseRepository;
import com.pmu.races.dataAccess.RaceRepository;
import com.pmu.races.dataAccess.dao.HorseDAOImplementation;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestRaces 
{
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	public static final String SERVER = "http://localhost:8080";
/*	
	@Autowired
    private TestEntityManager testEntityManager;*/

	@Autowired
    private HorseRepository horseTestRepository;
	
	@Autowired
    private RaceRepository raceTestRepository;
	
	@Autowired
    private HorseDAOImplementation horseDAOImplementation;
	
	private static OkHttpClient client = new OkHttpClient();
	
	@BeforeAll
	public void init()
	{
		try 
		{
			//client = new OkHttpClient();
			Horse horse1 = new Horse();
			horse1.setName("horse1");
			Horse horse2 = new Horse();
			horse2.setName("horse2");
			Horse horse3 = new Horse();
			horse3.setName("horse3");
			Horse horse4 = new Horse();
			horse4.setName("horse4");
			Horse horse5 = new Horse();
			horse5.setName("horse5");
			horseDAOImplementation.saveHorse(horse1);
			horseDAOImplementation.saveHorse(horse2);
			horseDAOImplementation.saveHorse(horse3);
			horseDAOImplementation.saveHorse(horse4);
			horseDAOImplementation.saveHorse(horse5);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(1)
	public void horseTestRepositoryNotNull()
	{
	    Assert.assertTrue(horseTestRepository != null);
	}
	
	@Test
	@Order(2)
	public void raceTestRepositoryNotNull()
	{
	    Assert.assertTrue(raceTestRepository != null);
	}
	
	@Test
	@Order(3)
	void testCreateHorse()
	{
		String json = "{\r\n"
				+ "    \"name\":\"horse6\"\r\n"
				+ "}";
		StringBuilder url = new StringBuilder(SERVER);
		url.append("/horse/create");
		RequestBody body = RequestBody.create(json, JSON);
	    Request request = new Request.Builder()
	            .url(url.toString())
	            .post(body)
	            .build();
	    try
	    {
	    	Response response = client.newCall(request).execute();
	    	Assert.assertTrue(response.code() == 200);
	    }
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(4)
	void testCreateRaceWith3Horses()
	{
		String json = "{\r\n"
				+ "    \"day\":\"2023-05-25\",\r\n"
				+ "    \"name\":\"race2\",\r\n"
				+ "    \"daily_id\":10,\r\n"
				+ "    \"horseIds\": [2,3,4]\r\n"
				+ "}";
		StringBuilder url = new StringBuilder(SERVER);
		url.append("/race/createOrUpdate");
		RequestBody body = RequestBody.create(json, JSON);
	    Request request = new Request.Builder()
	            .url(url.toString())
	            .post(body)
	            .build();
	    try
	    {
	    	Response response = client.newCall(request).execute();
	    	Assert.assertTrue(response.code() == 200);
	    }
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	void testAddHorsesToRaceAlreadyExists()
	{
		String json = "{\r\n"
				+ "    \"day\":\"2023-05-25\",\r\n"
				+ "    \"name\":\"race2\",\r\n"
				+ "    \"daily_id\":10,\r\n"
				+ "    \"horseIds\": [5,6]\r\n"
				+ "}";
		StringBuilder url = new StringBuilder(SERVER);
		url.append("/race/createOrUpdate");
		RequestBody body = RequestBody.create(json, JSON);
	    Request request = new Request.Builder()
	            .url(url.toString())
	            .post(body)
	            .build();
	    try
	    {
	    	Response response = client.newCall(request).execute();
	    	Assert.assertTrue(response.code() == 200);
	    }
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(6)
	void testCreateRaceWithLessThan3Horses()
	{
		Response response = null;
		String json = "{\r\n"
				+ "    \"day\":\"2023-05-25\",\r\n"
				+ "    \"name\":\"race2\",\r\n"
				+ "    \"daily_id\":11,\r\n"
				+ "    \"horseIds\": [2,3]\r\n"
				+ "}";
		StringBuilder url = new StringBuilder(SERVER);
		url.append("/race/createOrUpdate");
		RequestBody body = RequestBody.create(json, JSON);
	    Request request = new Request.Builder()
	            .url(url.toString())
	            .post(body)
	            .build();
	    try
	    {
	    	response = client.newCall(request).execute();
	    	Assert.assertTrue(response.code() == 500);
	    }
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
