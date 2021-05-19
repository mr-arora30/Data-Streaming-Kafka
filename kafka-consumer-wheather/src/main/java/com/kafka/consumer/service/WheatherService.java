package com.kafka.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kafka.consumer.jpa.WheatherRepo;

import com.kafka.consumer.entity.Wheather;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WheatherService {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private WheatherRepo repo;
	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	public void processWheatherEvent(ConsumerRecord<String, String> consumerRecord)
			throws JsonMappingException, JsonProcessingException {

		Wheather tevent = objectMapper.readValue(consumerRecord.value(), Wheather.class);
		log.info("Arrival Event {}", tevent);
		save(tevent);
	}

	private void save(Wheather turnstileEvent) {
		repo.save(turnstileEvent);

	}
}
