package com.example.consumption.service;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.ConsumptionDto;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.repository.ConsumptionRepository;
import com.example.consumption.service.impl.ConsumptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

import static com.example.consumption.utils.TestUtils.JANUARY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConsumptionServiceUnitTest extends BaseUnitTest {

    @Mock
    private ConsumptionRepository consumptionRepository;

    @Mock
    private MeterReadingService meterReadingService;

    @InjectMocks
    private ConsumptionServiceImpl consumptionService;

    Profile profile;

    Meter meter;

    @BeforeEach
    public void setUp() {
        profile = createProfile();
        meter = profile.getMeter();
    }

    @Test
    void shouldGetConsumptionByMonth() {
        String month = JANUARY;
        Month parsedMonth = Month.valueOf(month.toUpperCase());
        int year = Year.now().getValue();

        LocalDateTime startOfMonth = LocalDateTime.of(year, parsedMonth, 1, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, parsedMonth, parsedMonth.maxLength(), 23, 59, 59);

        when(meterReadingService.getMeterReadingByTimeOfMeasuring(profile.getId(), meter.getId(), startOfMonth))
                .thenReturn(meter.getMeterReadings().get(0));
        when(meterReadingService.getMeterReadingByTimeOfMeasuring(profile.getId(), meter.getId(), endOfMonth))
                .thenReturn(meter.getMeterReadings().get(1));
        when(consumptionRepository.save(any())).thenReturn(meter.getConsumptions().get(0));

        ConsumptionDto expected = consumptionService.getConsumptionByMonth(profile.getId(), meter.getId(), month);

        assertEquals(expected.getMonth(), meter.getConsumptions().get(0).getMonth());
        assertEquals(expected.getMeterId(), meter.getId());
        assertEquals(expected.getFirstReading(), meter.getMeterReadings().get(0).getTimeOfMeasuring());
        assertEquals(expected.getLastReading(), meter.getMeterReadings().get(1).getTimeOfMeasuring());
    }

}
