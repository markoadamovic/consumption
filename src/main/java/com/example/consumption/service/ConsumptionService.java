package com.example.consumption.service;

import com.example.consumption.model.dto.ConsumptionDto;

public interface ConsumptionService {

    ConsumptionDto getConsumptionByMonth(Long profileId, Long meterId, String month);

}
