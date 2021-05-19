package com.kafka.wheather.producer;

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
import com.kafka.wheather.domain.Season;
import com.kafka.wheather.domain.Wheather;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WheatherEventProducer {
	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;
	String topic = "org.station.wheather";
	@Autowired
	ObjectMapper objectMapper;

	public ListenableFuture<SendResult<Integer, String>> sendWheatherEventProducer(Wheather event)
			throws JsonProcessingException {
		Integer key = event.getTemp();
		log.info("got event id {}", key);
		if (key < 10) {
			event.setSeason(Season.WINTER.toString());
		} else if (key > 10 && key < 20) {
			event.setSeason(Season.SPRING.toString());
		} else
			event.setSeason(Season.SUMMER.toString());

		String value = objectMapper.writeValueAsString(event);
		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
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

	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

		List<Header> recordHeaders = List.of(new RecordHeader("trunstile-event-source", "scanner".getBytes()));

		return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error Sending the Message and the exception is {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in OnFailure: {}", throwable.getMessage());
		}

	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value,
				result.getRecordMetadata().partition());
	}

}
