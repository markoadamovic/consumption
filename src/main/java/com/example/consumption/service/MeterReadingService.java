package com.example.consumption.service;

import com.example.consumption.model.dto.MeterReadingDto;
import com.example.consumption.model.entity.MeterReading;

import java.time.LocalDateTime;
import java.util.List;

public interface MeterReadingService {

    List<MeterReadingDto> getMeterReadings(Long profileId, Long meterId);

    MeterReading getMeterReadingByTimeOfMeasuring(Long profileId, Long meterId, LocalDateTime timeOfMeasuring);

    MeterReadingDto getMeterReading(Long profileId, Long meterId, Long meterReadingId);

    MeterReadingDto createMeterReading(Long profileId, Long meterId);

    void deleteMeterReading(Long profileId, Long meterId, Long meterReadingId);

    MeterReading getMeterReadingModel(Long profileId, Long meterId, Long meterReadingId);

}
