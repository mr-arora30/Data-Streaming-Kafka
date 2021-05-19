package com.kafka.arrivals.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.arrivals.domain.ArrivalEvent;
import com.kafka.arrivals.producer.ArrivalEventProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ArrivalsEventController {
	@Autowired
	ArrivalEventProducer arrivalEventProducer;
	
	@PostMapping("/arrival/arrivalEvent")
	public ResponseEntity<ArrivalEvent> postArrivalEvent(@RequestBody @Valid ArrivalEvent event) throws JsonProcessingException{
		log.info("event id from the restend {}",event.getEventId());
		arrivalEventProducer.sendArrivalEventProducer(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(event);
		
	}
}
