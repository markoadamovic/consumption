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

    private Long meterReadingId;

    private LocalDateTime dateOfMeasuring;

    private double readingValue;

    public static MeterReadingDto fromEntity(MeterReading meterReading) {
        return MeterReadingDto.builder()
                .meterReadingId(meterReading.getId())
                .readingValue(meterReading.getValue())
                .dateOfMeasuring(meterReading.getDateOfMeasuring())
                .build();
    }
}
