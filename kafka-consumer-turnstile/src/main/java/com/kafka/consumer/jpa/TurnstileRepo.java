package com.kafka.consumer.jpa;

import org.springframework.data.repository.CrudRepository;

import com.kafka.consumer.entity.Turnstile;

public interface TurnstileRepo extends CrudRepository<Turnstile, Integer>{

}
