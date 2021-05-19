package com.kafka.arrivals.producer;

import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.arrivals.domain.ArrivalEvent;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class ArrivalEventProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	String topic = "org.station.arrivals";
	@Autowired
	ObjectMapper objectMapper;

	public ListenableFuture<SendResult<String, String>> sendArrivalEventProducer(ArrivalEvent event)
			throws JsonProcessingException {
		String key = event.getEventId();
		log.info("got event id {}",key);
		String value = objectMapper.writeValueAsString(event);
		ProducerRecord<String, String> producerRecord = buildProducerRecord(key, value, topic);
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				// TODO Auto-generated method stub
				handleSuccess(key, value, result);

			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				handleFailure(key, value, ex);
			}

		});
		return listenableFuture;
	}

	private ProducerRecord<String, String> buildProducerRecord(String key, String value, String topic) {

		List<Header> recordHeaders = List.of(new RecordHeader("arrival-event-source", "scanner".getBytes()));

		return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}

	private void handleFailure(String key, String value, Throwable ex) {
		log.error("Error Sending the Message and the exception is {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in OnFailure: {}", throwable.getMessage());
		}

	}

	private void handleSuccess(String key, String value, SendResult<String, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value,
				result.getRecordMetadata().partition());
	}

}
