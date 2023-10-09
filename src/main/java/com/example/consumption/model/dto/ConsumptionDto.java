package com.example.consumption.model.dto;

import com.example.consumption.model.entity.Consumption;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionDto {

    private Long id;

    private Long meterId;

    private String month;

    private double consumptionValue;

    private LocalDateTime firstReading;

    private LocalDateTime lastReading;

    public static ConsumptionDto fromEntity(Consumption consumption) {
        return ConsumptionDto.builder()
                .id(consumption.getId())
                .meterId(consumption.getMeter().getId())
                .month(consumption.getMonth())
                .consumptionValue(consumption.getConsumptionValue())
                .firstReading(consumption.getStartDate())
                .lastReading(consumption.getEndDate())
                .build();
    }
}
