package com.example.consumption.service.impl;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.repository.MeterRepository;
import com.example.consumption.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeterServiceImpl implements MeterService {

    private final MeterRepository meterRepository;

    @Override
    public Meter getMeterModel(Long meterId, String profileName) {
        return meterRepository.findByProfileNameAndId(meterId, profileName)
                .orElseThrow(() -> new NotFoundException(String.format("Meter with id %s for profile with name %s is not found", meterId, profileName)));
    }

    @Override
    public Meter getMeterModel(Long meterId, Long profileId) {
        return meterRepository.findByProfileAndId(meterId, profileId)
                .orElseThrow(() -> new NotFoundException(String.format("Meter with id %s for profile %s is not found", meterId, profileId)));
    }

    @Override
    public List<MeterDto> saveAllMeters(List<Meter> meters) {
        return meterRepository.saveAll(meters).stream()
                .map(MeterDto::fromEntity)
                .collect(Collectors.toList());
    }

}

