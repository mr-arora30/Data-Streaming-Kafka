package com.kafka.arrivals.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Arrivals {
	@NotNull
	private int stationId;
	@NotNull
	private int trainId;
	private String direction;
	private String line;
	private String trainStatus;
	private int prevStationId;
	private String prevDirection;

}
