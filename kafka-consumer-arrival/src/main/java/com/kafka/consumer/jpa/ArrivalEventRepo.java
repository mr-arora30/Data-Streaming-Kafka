package com.kafka.consumer.jpa;

import org.springframework.data.repository.CrudRepository;

import com.kafka.consumer.entity.ArrivalEvent;

public interface ArrivalEventRepo extends CrudRepository<ArrivalEvent, String> {

}
