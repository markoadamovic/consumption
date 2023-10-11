package com.example.consumption.service;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.repository.MeterRepository;
import com.example.consumption.service.impl.MeterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.example.consumption.utils.TestUtils.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MeterServiceUnitTest extends BaseUnitTest {

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterServiceImpl meterService;

    Profile profile;

    Meter meter;

    @BeforeEach
    public void setUp() {
        profile = createProfile();
        meter = profile.getMeter();
    }

    @Test
    void shouldGetMeterModel_withProfileName() {
        when(meterRepository.findByProfileNameAndId(meter.getId(), profile.getName())).thenReturn(Optional.of(meter));

        Meter expected = meterService.getMeterModel(meter.getId(), profile.getName());

        assertEquals(expected.getId(), meter.getId());
        assertEquals(expected.getMeterReadings().size(), meter.getMeterReadings().size());
        assertEquals(expected.getMeterReadings().get(0).getTimeOfMeasuring(), meter.getMeterReadings().get(0).getTimeOfMeasuring());
        assertEquals(expected.getMeterReadings().get(1).getTimeOfMeasuring(), meter.getMeterReadings().get(1).getTimeOfMeasuring());
        assertEquals(expected.getMeterReadings().get(0).getValue(), meter.getMeterReadings().get(0).getValue());
        assertEquals(expected.getMeterReadings().get(1).getValue(), meter.getMeterReadings().get(1).getValue());
        assertEquals(expected.getConsumptions().size(), meter.getConsumptions().size());
        assertEquals(expected.getConsumptions().get(0).getConsumptionValue(), meter.getConsumptions().get(0).getConsumptionValue());
    }

    @Test
    void shouldThrowNotFoundException_whenGetMeterModel_ifProfileByNameIsNotFound() {
        when(meterRepository.findByProfileNameAndId(meter.getId(), profile.getName())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> meterService.getMeterModel(meter.getId(), profile.getName()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldGetMeterModel() {
        when(meterRepository.findByProfileAndId(profile.getId(), meter.getId())).thenReturn(Optional.of(meter));

        Meter expected = meterService.getMeterModel(profile.getId(), meter.getId());

        assertEquals(expected.getId(), meter.getId());
        assertEquals(expected.getMeterReadings().size(), meter.getMeterReadings().size());
        assertEquals(expected.getMeterReadings().get(0).getTimeOfMeasuring(), meter.getMeterReadings().get(0).getTimeOfMeasuring());
        assertEquals(expected.getMeterReadings().get(1).getTimeOfMeasuring(), meter.getMeterReadings().get(1).getTimeOfMeasuring());
        assertEquals(expected.getMeterReadings().get(0).getValue(), meter.getMeterReadings().get(0).getValue());
        assertEquals(expected.getMeterReadings().get(1).getValue(), meter.getMeterReadings().get(1).getValue());
        assertEquals(expected.getConsumptions().size(), meter.getConsumptions().size());
        assertEquals(expected.getConsumptions().get(0).getConsumptionValue(), meter.getConsumptions().get(0).getConsumptionValue());
    }

    @Test
    void shouldThrowNotFoundException_whenGetMeterModel_ifProfileIsNotFound() {
        when(meterRepository.findByProfileAndId(meter.getId(), profile.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> meterService.getMeterModel(meter.getId(), profile.getId()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldSaveAllMeters() {
        when(meterRepository.saveAll(List.of(meter))).thenReturn(List.of(meter));

        List<MeterDto> expected = meterService.saveAllMeters(List.of(meter));

        assertEquals(expected.get(0).getId(), meter.getId());
        assertEquals(expected.get(0).getProfileId(), meter.getProfile().getId());
        assertEquals(expected.get(0).getMeterReadings().get(0).getTimeOfMeasuring(), meter.getMeterReadings().get(0).getTimeOfMeasuring());
        assertEquals(expected.get(0).getMeterReadings().get(1).getTimeOfMeasuring(), meter.getMeterReadings().get(1).getTimeOfMeasuring());
        assertEquals(expected.get(0).getMeterReadings().get(0).getValue(), meter.getMeterReadings().get(0).getValue());
        assertEquals(expected.get(0).getMeterReadings().get(1).getValue(), meter.getMeterReadings().get(1).getValue());
        assertEquals(expected.get(0).getConsumptions().size(), meter.getConsumptions().size());
        assertEquals(expected.get(0).getConsumptions().get(0).getConsumptionValue(), meter.getConsumptions().get(0).getConsumptionValue());
    }

}
