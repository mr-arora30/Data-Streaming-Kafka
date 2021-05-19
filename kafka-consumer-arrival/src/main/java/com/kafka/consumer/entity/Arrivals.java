package com.kafka.consumer.entity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Arrivals {
	@Id
	private int stationId;	
	private int trainId;
	private String direction;
	private String line;
	private String trainStatus;
	private int prevStationId;
	private String prevDirection;
	@OneToOne
	@JoinColumn(name="eventId")
	private ArrivalEvent arrivalEvent;
}
