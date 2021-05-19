package com.kafka.turnstiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turnstile {
 private int stationID;
 private String stationName;
 private String line;
}
