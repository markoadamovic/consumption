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

    private Long meterId;

    private Long profileId;

    private EntityStatus entityStatus;

    private List<MeterReadingDto> meterReadingList;

    private List<ConsumptionDto> consumptionDtos;

    public static MeterDto fromEntity(Meter meter) {
        return MeterDto.builder()
                .meterId(meter.getId())
                .profileId(meter.getProfile().getId())
                .entityStatus(meter.getStatus())
                .meterReadingList(meter.getMeterReadings().stream()
                        .map(MeterReadingDto::fromEntity)
                        .collect(Collectors.toList()))
                .consumptionDtos(meter.getConsumptions().stream()
                        .map(ConsumptionDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

}
