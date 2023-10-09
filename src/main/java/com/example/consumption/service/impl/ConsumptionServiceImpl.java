package com.example.consumption.service.impl;

import com.example.consumption.model.dto.ConsumptionDto;
import com.example.consumption.model.entity.Consumption;
import com.example.consumption.model.entity.MeterReading;
import com.example.consumption.repository.ConsumptionRepository;
import com.example.consumption.service.ConsumptionService;
import com.example.consumption.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ConsumptionServiceImpl implements ConsumptionService {

    private final ConsumptionRepository consumptionRepository;

    private final MeterReadingService meterReadingService;

    @Override
    public ConsumptionDto getConsumptionByMonth(Long profileId, Long meterId, String month) {
        Month parsedMonth = Month.valueOf(month.toUpperCase());
        int year = Year.now().getValue();

        LocalDateTime startOfMonth = LocalDateTime.of(year, parsedMonth, 1, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, parsedMonth, parsedMonth.maxLength(), 23, 59, 59);

        MeterReading firstReading = meterReadingService.getMeterReadingByTimeOfMeasuring(profileId, meterId, startOfMonth);
        MeterReading lastReading = meterReadingService.getMeterReadingByTimeOfMeasuring(profileId, meterId, endOfMonth);

        Consumption consumption = Consumption.builder()
                .consumptionValue(lastReading.getValue() - firstReading.getValue())
                .startDate(startOfMonth)
                .endDate(endOfMonth)
                .month(parsedMonth.name().substring(0, 3))
                .meter(lastReading.getMeter())
                .build();

        return ConsumptionDto.fromEntity(consumptionRepository.save(consumption));
    }

}
