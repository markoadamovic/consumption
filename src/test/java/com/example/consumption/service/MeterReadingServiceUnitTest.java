package com.example.consumption.service;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.MeterReadingDto;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.MeterReading;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.model.enums.EntityStatus;
import com.example.consumption.repository.MeterReadingRepository;
import com.example.consumption.service.impl.MeterReadingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.example.consumption.utils.TestUtils.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MeterReadingServiceUnitTest extends BaseUnitTest {


    @Mock
    MeterReadingRepository meterReadingRepository;

    @InjectMocks
    MeterReadingServiceImpl meterReadingService;

    Profile profile;

    Meter meter;

    MeterReading meterReading;

    @BeforeEach
    public void setUp() {
        profile = createProfile();
        meter = profile.getMeter();
        meterReading = meter.getMeterReadings().get(0);
    }

    @Test
    void shouldGetMeterReading() {
        when(meterReadingRepository.findByProfileAndMeterAndId(profile.getId(), meter.getId(), meterReading.getId()))
                .thenReturn(Optional.of(meterReading));

        MeterReadingDto expected = meterReadingService.getMeterReading(profile.getId(), meter.getId(), meterReading.getId());

        assertEquals(expected.getId(), meterReading.getId());
        assertEquals(expected.getValue(), meterReading.getValue());
        assertEquals(expected.getTimeOfMeasuring(), meterReading.getTimeOfMeasuring());
    }

    @Test
    void shouldThrowNotFoundException_whenGetMeterReading_ifMeterReadingIsNotFound() {
        when(meterReadingRepository.findByProfileAndMeterAndId(profile.getId(), meter.getId(), meterReading.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> meterReadingService.getMeterReading(profile.getId(), meter.getId(), meterReading.getId()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldThrowNotFoundException_whenGetMeterReadingModel_ifMeterReadingIsNotFound() {
        when(meterReadingRepository.findByProfileAndMeterAndId(profile.getId(), meter.getId(), meterReading.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> meterReadingService.getMeterReadingModel(profile.getId(), meter.getId(), meterReading.getId()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldGetModel() {
        when(meterReadingRepository.findByProfileAndMeterAndId(profile.getId(), meter.getId(), meterReading.getId()))
                .thenReturn(Optional.of(meterReading));

        MeterReading expected = meterReadingService.getMeterReadingModel(profile.getId(), meter.getId(), meterReading.getId());

        assertEquals(expected.getId(), meterReading.getId());
        assertEquals(expected.getValue(), meterReading.getValue());
        assertEquals(expected.getTimeOfMeasuring(), meterReading.getTimeOfMeasuring());
        assertEquals(expected.getMeter(), meterReading.getMeter());
        assertEquals(expected.getMeter().getProfile().getId(), meterReading.getMeter().getProfile().getId());
    }

    @Test
    void shouldGetMeterReadings() {
        List<MeterReading> meterReadings = meter.getMeterReadings();
        when(meterReadingRepository.findAll(profile.getId(), meter.getId())).thenReturn(meterReadings);

        List<MeterReadingDto> expected = meterReadingService.getMeterReadings(profile.getId(), meter.getId());

        assertEquals(expected.size(), meterReadings.size());
        assertEquals(expected.get(0).getTimeOfMeasuring(), meterReadings.get(0).getTimeOfMeasuring());
        assertEquals(expected.get(1).getTimeOfMeasuring(), meterReadings.get(1).getTimeOfMeasuring());
        assertEquals(expected.get(0).getValue(), meterReadings.get(0).getValue());
        assertEquals(expected.get(1).getValue(), meterReadings.get(1).getValue());
        assertEquals(expected.get(0).getId(), meterReadings.get(0).getId());
        assertEquals(expected.get(1).getId(), meterReadings.get(1).getId());
    }

    @Test
    void shouldGetMeterReadingByTimeOfMeasuring() {
        when(meterReadingRepository.findMeterReading(profile.getId(), meter.getId(), meterReading.getTimeOfMeasuring()))
                .thenReturn(Optional.of(meterReading));

        MeterReading expected = meterReadingService
                .getMeterReadingByTimeOfMeasuring(profile.getId(), meter.getId(), meterReading.getTimeOfMeasuring());

        assertEquals(expected.getId(), meterReading.getId());
        assertEquals(expected.getValue(), meterReading.getValue());
        assertEquals(expected.getTimeOfMeasuring(), meterReading.getTimeOfMeasuring());
        assertEquals(expected.getMeter(), meterReading.getMeter());
        assertEquals(expected.getMeter().getProfile().getId(), meterReading.getMeter().getProfile().getId());
    }

    @Test
    void shouldThrowNotFoundException_GetMeterReadingByTimeOfMeasuring_ifMeterReadingIsNotFound() {
        when(meterReadingRepository.findMeterReading(profile.getId(), meter.getId(), meterReading.getTimeOfMeasuring()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> meterReadingService.getMeterReadingByTimeOfMeasuring(profile.getId(), meter.getId(), meterReading.getTimeOfMeasuring()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldDeleteMeterReading() {
        when(meterReadingRepository.findByProfileAndMeterAndId(profile.getId(), meter.getId(), meterReading.getId()))
                .thenReturn(Optional.of(meterReading));
        when(meterReadingRepository.save(any())).thenReturn(meterReading);

        meterReadingService.deleteMeterReading(profile.getId(), meter.getId(), meterReading.getId());

        assertEquals(EntityStatus.DELETED, meterReading.getStatus());
    }
}
