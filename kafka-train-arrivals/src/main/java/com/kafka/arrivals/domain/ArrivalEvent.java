package com.kafka.arrivals.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArrivalEvent {
	@NotNull
	private String eventId;
	@NotNull
	@Valid
	private Arrivals arrivals;
}
