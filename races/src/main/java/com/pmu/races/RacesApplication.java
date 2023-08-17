package com.pmu.races;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pmu.races.streams.Producer;

@SpringBootApplication
public class RacesApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(RacesApplication.class, args);
	}
/*
	@Bean
	public ApplicationRunner runner(Producer producer) 
	{
		return (args) -> {
			for (int i = 1; i < 5; i++) {
				producer.send(new SampleMessage(i, "A simple test message"));
			}
		};
	}*/
}
