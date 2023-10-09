package com.example.consumption.service;

import com.example.consumption.model.dto.UpdateFractionDto;
import com.example.consumption.model.dto.FractionDto;
import com.example.consumption.model.entity.Fraction;

import java.util.List;

public interface FractionService {

    Fraction getFractionModel(Long profileId, Long fractionId);

    FractionDto getFraction(Long profileId, Long fractionId);

    List<FractionDto> getFractions(Long profileId);

    FractionDto createFraction(Long profileId, FractionDto updateFractionDto);

    List<FractionDto> updateFractions(Long profileId, UpdateFractionDto fractionDto);

    FractionDto updateFraction(Long profileId, Long fractionId, FractionDto fractionDto);

    void deleteFraction(Long profileId, Long fractionId);

}
