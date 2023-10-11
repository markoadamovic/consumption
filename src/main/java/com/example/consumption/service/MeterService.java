package com.example.consumption.service;

import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.entity.Meter;

import java.util.List;

public interface MeterService {

    Meter getMeterModel(Long meterId, String profileName);

    Meter getMeterModel(Long meterId, Long profileId);

    List<MeterDto> saveAllMeters(List<Meter> meters);

}
