package com.pmu.races.streams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer 
{
	 private final KafkaTemplate<String, String> kafkaTemplate;
	 private final String TOPIC = "races-bus";

	    @Value(TOPIC)
	    private String topic;

	    Producer(KafkaTemplate<String, String> kafkaTemplate) 
	    {
	        this.kafkaTemplate = kafkaTemplate;
	    }

	    public void send(StreamMessage message)
	    {
	    	
	        kafkaTemplate.send(topic, message.getMessage());
	    }
}
