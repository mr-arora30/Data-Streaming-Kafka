package com.kafka.turnstiles.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.kafka.turnstiles.domain.Turnstile;
import com.kafka.turnstiles.producer.TurnstileEventProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TurnstileEventController {
	@Autowired
	TurnstileEventProducer eventProducer;
	
	@PostMapping("/turnstile")
	public ResponseEntity<Turnstile> postArrivalEvent(@RequestBody Turnstile event) throws JsonProcessingException{
		log.info("event id from the restend {}",event.getStationID());
		eventProducer.sendTurnstileEventProducer(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(event);
		
	}
}
