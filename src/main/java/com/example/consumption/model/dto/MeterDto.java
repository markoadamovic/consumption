package com.example.consumption.model.dto;

import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.enums.EntityStatus;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterDto {

    private Long id;

    private Long profileId;

    private EntityStatus entityStatus;

    private List<MeterReadingDto> meterReadings;

    private List<ConsumptionDto> consumptions;

    public static MeterDto fromEntity(Meter meter) {
        return MeterDto.builder()
                .id(meter.getId())
                .profileId(meter.getProfile().getId())
                .entityStatus(meter.getStatus())
                .meterReadings(meter.getMeterReadings().stream()
                        .map(MeterReadingDto::fromEntity)
                        .collect(Collectors.toList()))
                .consumptions(meter.getConsumptions().stream()
                        .map(ConsumptionDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

}
