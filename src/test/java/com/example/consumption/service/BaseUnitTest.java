package com.example.consumption.service;

import com.example.consumption.model.entity.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;

import static com.example.consumption.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {

    protected Profile createProfile() {
        Meter meter = buildMeter();
        Profile profile = buildProfile(meter);
        meter.setProfile(profile);

        return profile;
    }

    private Profile buildProfile(Meter meter) {
        Profile profile = Profile.builder().build();
        profile.setMeter(meter);
        Month[] month = Month.values();
        for (int i = 0; i < 12; i++) {
            if (i == 5 || i == 6) {
                continue;
            }
            Fraction fraction = Fraction.builder()
                    .id((long) i)
                    .profile(profile)
                    .month(month[i].toString())
                    .value(0.9)
                    .build();
            profile.getFractions().add(fraction);
        }
        return profile;
    }

    private Meter buildMeter() {
        String month = JANUARY;
        Month parsedMonth = Month.valueOf(month.toUpperCase());
        int year = Year.now().getValue();
        LocalDateTime startOfMonth = LocalDateTime.of(year, parsedMonth, 1, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, parsedMonth, parsedMonth.maxLength(), 23, 59, 59);

        Meter meter = Meter.builder()
                .id(ID_1)
                .meterCounter(28)
                .build();
        MeterReading meterReading1 = MeterReading.builder()
                .id(ID_1)
                .timeOfMeasuring(startOfMonth)
                .value(5)
                .meter(meter)
                .build();
        MeterReading meterReading2 = MeterReading.builder()
                .id(ID_2)
                .timeOfMeasuring(endOfMonth)
                .value(10)
                .meter(meter)
                .build();
        Consumption consumption = Consumption.builder()
                .id(ID_1)
                .startDate(meterReading1.getTimeOfMeasuring())
                .endDate(meterReading2.getTimeOfMeasuring())
                .consumptionValue(meterReading2.getValue() - meterReading1.getValue())
                .month(meterReading2.getTimeOfMeasuring().getMonth().toString())
                .meter(meter)
                .build();
        meter.setMeterReadings(List.of(meterReading1, meterReading2));
        meter.setConsumptions(List.of(consumption));

        return meter;
    }
}
