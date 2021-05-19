package com.kafka.wheather.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.kafka.wheather.domain.Wheather;
import com.kafka.wheather.producer.WheatherEventProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WheatherController {
	@Autowired
	WheatherEventProducer eventproducer;
	@PostMapping("/wheather")
	public ResponseEntity<Wheather> postArrivalEvent(@RequestBody Wheather event) throws JsonProcessingException{
		log.info("event id from the restend {}",event.getTemp());
		eventproducer.sendWheatherEventProducer(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(event);
		
	}

}
