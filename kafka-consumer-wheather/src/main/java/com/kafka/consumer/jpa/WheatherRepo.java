package com.kafka.consumer.jpa;

import org.springframework.data.repository.CrudRepository;

import com.kafka.consumer.entity.Wheather;

public interface WheatherRepo extends CrudRepository<Wheather, Integer>{

}
