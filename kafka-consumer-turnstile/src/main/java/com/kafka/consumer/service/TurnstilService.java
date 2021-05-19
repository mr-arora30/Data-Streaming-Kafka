package com.kafka.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kafka.consumer.jpa.TurnstileRepo;
import com.kafka.consumer.entity.Turnstile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TurnstilService {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private TurnstileRepo repo;
	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	public void processTurnstileEvent(ConsumerRecord<String, String> consumerRecord)
			throws JsonMappingException, JsonProcessingException {

		Turnstile tevent = objectMapper.readValue(consumerRecord.value(), Turnstile.class);
		log.info("Arrival Event {}", tevent);
		save(tevent);
	}

	private void save(Turnstile turnstileEvent) {
		repo.save(turnstileEvent);

	}
}
