package com.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.kafka.consumer.service.WheatherService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WheatherConsumer {
	@Autowired
	private WheatherService service;
 @KafkaListener(topics={"org.station.arrivals"})
 public void onMessage(ConsumerRecord<String, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
	 log.info("Consumer Record {}",consumerRecord);
	 service.processWheatherEvent(consumerRecord);
	
 }
}
