package com.example.consumption.service.impl;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.MeterReadingDto;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.MeterReading;
import com.example.consumption.model.enums.EntityStatus;
import com.example.consumption.repository.MeterReadingRepository;
import com.example.consumption.service.MeterReadingService;
import com.example.consumption.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;

    private final MeterService meterService;

    @Override
    public MeterReadingDto getMeterReading(Long profileId, Long meterId, Long meterReadingId) {
        return MeterReadingDto.fromEntity(getMeterReadingModel(profileId, meterId, meterReadingId));
    }

    @Override
    public MeterReadingDto createMeterReading(Long profileId, Long meterId) {
        Meter meter = meterService.getMeterModel(profileId, meterId);
        MeterReading meterReading = MeterReading.builder()
                .meter(meter)
                .value(meter.getMeterCounter())
                .dateOfMeasuring(LocalDateTime.now())
                .build();

        return MeterReadingDto.fromEntity(meterReading);
    }

    @Override
    public void deleteMeterReading(Long profileId, Long meterId, Long meterReadingId) {
        MeterReading meterReading = getMeterReadingModel(profileId, meterId, meterReadingId);
        meterReading.setStatus(EntityStatus.DELETED);

        meterReadingRepository.save(meterReading);
    }

    @Override
    public List<MeterReadingDto> getAllMeterReadings(Long profileId, Long meterId) {
        return meterReadingRepository.findAll(profileId, meterId)
                .stream()
                .map(MeterReadingDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MeterReading getMeterReadingByTimeOfMeasuring(Long profileId, Long meterId, LocalDateTime timeOfMeasuring) {
        return meterReadingRepository
                .findMeterReading(profileId, meterId, timeOfMeasuring)
                .orElseThrow(() -> new NotFoundException(String.format("MeterReading with id %s for profile " +
                        "with id %s and dateTime %s is not found", meterId, profileId, timeOfMeasuring)));
    }

    private MeterReading getMeterReadingModel(Long profileId, Long meterId, Long meterReadingId) {
        return meterReadingRepository.findByProfileAndMeterAndId(profileId, meterId, meterReadingId)
                .orElseThrow(() -> new NotFoundException(String.format("MeterReading with id %s for profile " +
                        "with id %s and meter with id %s is not found", meterReadingId, profileId, meterId)));
    }
}
