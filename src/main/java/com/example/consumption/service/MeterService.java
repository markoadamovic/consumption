package com.example.consumption.service;

import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.entity.Meter;

import java.util.List;
import java.util.Optional;

public interface MeterService {

    Meter getMeterModel(Long meterId, String profileName);

    Meter getMeterModel(Long meterId, Long profileId);

    Optional<Meter> findByIdAndProfileId(Long meterId, String profileName);

    List<Meter> getAllMeters();

    List<MeterDto> saveAllMeters(List<Meter> meters);

}
