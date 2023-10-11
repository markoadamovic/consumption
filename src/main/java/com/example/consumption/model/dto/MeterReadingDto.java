package com.example.consumption.model.dto;

import com.example.consumption.model.entity.MeterReading;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingDto {

    private Long id;

    private LocalDateTime timeOfMeasuring;

    private double value;

    public static MeterReadingDto fromEntity(MeterReading meterReading) {
        return MeterReadingDto.builder()
                .id(meterReading.getId())
                .value(meterReading.getValue())
                .timeOfMeasuring(meterReading.getTimeOfMeasuring())
                .build();
    }
}
