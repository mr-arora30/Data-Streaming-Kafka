package com.kafka.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.consumer.entity.ArrivalEvent;
import com.kafka.consumer.jpa.ArrivalEventRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArrivalEventService {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private ArrivalEventRepo repo;
	public void processArrivalEvent(ConsumerRecord<String, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		ArrivalEvent arrivalEvent= objectMapper.readValue(consumerRecord.value(), ArrivalEvent.class);
		log.info("Arrival Event {}", arrivalEvent);
		save(arrivalEvent);
	}
	private void save(ArrivalEvent arrivalEvent)
	{
		arrivalEvent.getArrivals().setArrivalEvent(arrivalEvent);
		repo.save(arrivalEvent);
		
		
	}
}
