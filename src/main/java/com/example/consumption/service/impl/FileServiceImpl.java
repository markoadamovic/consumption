package com.example.consumption.service.impl;

import com.example.consumption.exception.BadRequestException;
import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.entity.*;
import com.example.consumption.service.MeterService;
import com.example.consumption.service.FileService;
import com.example.consumption.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import java.util.*;


import static com.example.consumption.util.ApplicationConstants.monthAbbreviationToLastDayOfMonth;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MeterService meterService;

    private final ProfileService profileService;

    @Override
    public void processAndSaveProfileFractionData(MultipartFile file) {
        try {
            csvToFraction(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MeterDto> processAndSaveMeterReadingsData(MultipartFile file) {
        try {
            return csvToMeterReading(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<MeterDto> csvToMeterReading(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CSVRecord> records = csvParser.getRecords();
            List<Long> metersWithInvalidData = new ArrayList<>();
            Map<Long, Meter> meterMap = new HashMap<>();

            for (CSVRecord csvRecord : records) {
                String profileName = csvRecord.get("Profile");
                Long meterId = removeZerosAndConvertToLong(csvRecord.get("MeterID"));
                double meterValue = Double.parseDouble(csvRecord.get("Meter"));
                String month = csvRecord.get("Month");
                Meter meter;

                if (meterMap.containsKey(meterId)) {
                    meter = meterMap.get(meterId);
                } else {
                    meter = meterService.getMeterModel(meterId, profileName);
                    meterMap.put(meterId, meter);
                }
                LocalDateTime dayOfMeasuring = monthAbbreviationToLastDayOfMonth.get(month);
                MeterReading meterReading = createMeterReading(meter, meterValue, dayOfMeasuring);

                validateAndAddMeterReading(meter, metersWithInvalidData, meterReading, dayOfMeasuring, meterValue);
            }
            validateNumberOfMeterReadings(meterMap, metersWithInvalidData);

            createConsumptions(meterMap, metersWithInvalidData);

            for (Long meterId : metersWithInvalidData) {
                meterMap.remove(meterId);
            }
            return meterService.saveAllMeters(new ArrayList<>(meterMap.values()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createConsumptions(Map<Long, Meter> meterWithMeterReadingsMap,
                                    List<Long> metersWithInvalidData) {
        for (Map.Entry<Long, Meter> entry : meterWithMeterReadingsMap.entrySet()) {
            Long meterId = entry.getKey();
            if (metersWithInvalidData.contains(meterId)) {
                continue;
            }
            Meter meter = entry.getValue();
            List<MeterReading> meterReadings = meter.getMeterReadings();
            meterReadings.sort(Comparator.comparing(MeterReading::getDateOfMeasuring));
            List<Fraction> fractions = meter.getProfile().getFractions();
            fractions.sort(Comparator.comparing(this::getMonthOrder));

            double totalConsumptionValue = meterReadings.get(meterReadings.size() - 1).getValue();

            if (isValidConsumptionValue(meterReadings.get(0).getValue(), totalConsumptionValue, fractions.get(0).getFractionValue())) {
                Consumption consumption = Consumption.builder()
                        .meter(meter)
                        .consumptionValue(meterReadings.get(0).getValue())
                        .month(meterReadings.get(0).getDateOfMeasuring().getMonth().toString())
                        .build();
                meter.getConsumptions().add(consumption);
            } else {
                metersWithInvalidData.add(meterId);
                continue;
            }

            for (int i = 1; i < meterReadings.size(); i++) {

                double meterReadingValue = meterReadings.get(i).getValue();
                double previousMeterReadingValue = meterReadings.get(i - 1).getValue();
                double consumptionValue = meterReadingValue - previousMeterReadingValue;
                if (isValidConsumptionValue(consumptionValue, totalConsumptionValue, fractions.get(i).getFractionValue())) {
                    Consumption c = Consumption.builder()
                            .meter(meter)
                            .consumptionValue(consumptionValue)
                            .month(meterReadings.get(i).getDateOfMeasuring().getMonth().toString())
                            .build();
                    meter.getConsumptions().add(c);
                } else {
                    metersWithInvalidData.add(meterId);
                    break;
                }
            }
        }
    }


    private void csvToFraction(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CSVRecord> records = csvParser.getRecords();
            Map<String, Profile> profileMap = new HashMap<>();
            Map<Profile, Double> fractionsValue = new HashMap<>();
            for (CSVRecord csvRecord : records) {
                String profileName = csvRecord.get("Profile");
                String month = csvRecord.get("Month");
                double fractionValue = Double.parseDouble(csvRecord.get("Fraction"));

                Profile profile = getOrCreateProfile(profileMap, profileName, month, fractionValue);
                validateFractionValue(fractionsValue, profile, fractionValue);
            }
            for (Map.Entry<Profile, Double> entry : fractionsValue.entrySet()) {
                if (entry.getValue() != 1) {
                    throw new BadRequestException(String.format("Sum of all fraction values for profile %s is not 1", entry.getKey().getName()));
                }
            }
            List<Profile> profiles = new ArrayList<>(profileMap.values());
            validateFractionValueSize(profiles);
            profileService.saveProfiles(profiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long removeZerosAndConvertToLong(String id) {
        // Remove leading zeros from the string
        String cleanedId = id.replaceFirst("^0+", "");

        return Long.parseLong(cleanedId);
    }

    private Profile createProfile(String profileName) {
        Profile profile = Profile.builder()
                .name(profileName)
                .build();
        Meter meter = Meter.builder()
                .profile(profile)
                .build();

        profile.setMeter(meter);

        return profile;
    }

    private Profile getOrCreateProfile(Map<String, Profile> profileMap,
                                       String profileName,
                                       String month,
                                       double fractionValue) {
        Profile profile;
        if (profileMap.containsKey(profileName)) {
            profile = profileMap.get(profileName);
        } else {
            profile = createProfile(profileName);
            profileMap.put(profileName, profile);
        }
        Fraction fraction = Fraction.builder()
                .month(month)
                .fractionValue(fractionValue)
                .profile(profile)
                .build();

        profile.getFractions().add(fraction);

        return profile;
    }

    private MeterReading createMeterReading(Meter meter,
                                            double meterValue,
                                            LocalDateTime dateOfMeasuring) {
        meter.setMeterCounter(meterValue);
        return MeterReading.builder()
                .meter(meter)
                .value(meterValue)
                .dateOfMeasuring(dateOfMeasuring)
                .build();
    }


    private boolean validateAndAddMeterReading(Meter meter,
                                               List<Long> metersWithInvalidData,
                                               MeterReading meterReading,
                                               LocalDateTime dayOfMeasuring,
                                               double readingValue) {
        if (meter.getMeterReadings().isEmpty() || !invalidMeterReadingValue(meter.getMeterReadings(), dayOfMeasuring, readingValue)) {
            meter.getMeterReadings().add(meterReading);
            return true;
        } else {
            metersWithInvalidData.add(meter.getId());
            return false;
        }
    }

    private boolean invalidMeterReadingValue(List<MeterReading> meterReadings,
                                             LocalDateTime dayOfMeasuring,
                                             Double newValue) {
        return meterReadings.stream()
                .anyMatch(meterReading -> meterReading.getDateOfMeasuring().isBefore(dayOfMeasuring) &&
                        meterReading.getValue() > newValue);
    }

    private void validateDuplicateMonthlyMeterReadings(Map<Long, List<String>> meterReadingsPerMeter,
                                                       List<Long> metersWithInvalidData,
                                                       Long meterId,
                                                       String month) {
        if (meterReadingsPerMeter.containsKey(meterId)) {
            List<String> monthsForMeter = meterReadingsPerMeter.get(meterId);
            if (monthsForMeter.contains(month)) {
                metersWithInvalidData.add(meterId);
                return;
            }
            monthsForMeter.add(month);
        } else {
            List<String> monthsForMeter = new ArrayList<>();
            monthsForMeter.add(month);
            meterReadingsPerMeter.put(meterId, monthsForMeter);
        }
    }

    private void validateFractionValue(Map<Profile, Double> fractionValuesMap,
                                       Profile profile,
                                       double value) {
        if (fractionValuesMap.containsKey(profile)) {
            Double currentFractionsValues = fractionValuesMap.get(profile);
            if (currentFractionsValues + value > 1) {
                throw new BadRequestException("Input ERROR: fraction value is greater then 1");
            }
            fractionValuesMap.put(profile, currentFractionsValues + value);
        } else {
            fractionValuesMap.put(profile, value);
        }
    }

    private void validateFractionValueSize(List<Profile> profiles) {
        boolean validateSize = profiles.stream()
                .allMatch(profile -> profile.getFractions().size() == 12);
        if (!validateSize) {
            throw new BadRequestException("Input error, missing data for FRACTIONS");
        }
    }

    private void validateNumberOfMeterReadings(Map<Long, Meter> meterReadingsPerMeter,
                                               List<Long> metersWithInvalidData) {
        for (Map.Entry<Long, Meter> entry : meterReadingsPerMeter.entrySet()) {
            Long meterId = entry.getKey();
            Meter meter = entry.getValue();

            if (meter.getMeterReadings().size() != 12) {
                metersWithInvalidData.add(meterId);
            }
        }
    }

    private boolean isValidConsumptionValue(double consumptionValue,
                                            double totalConsumption,
                                            double fractionValue) {
        double predefinedConsumptionValue = totalConsumption * fractionValue;
        double minValue = predefinedConsumptionValue - predefinedConsumptionValue / 100 * 25;
        double maxValue = predefinedConsumptionValue + predefinedConsumptionValue / 100 * 25;

        return consumptionValue >= minValue && consumptionValue <= maxValue;
    }

    private int getMonthOrder(Fraction fraction) {
        Map<String, Integer> monthOrderMap = new HashMap<>();
        monthOrderMap.put("JAN", 1);
        monthOrderMap.put("FEB", 2);
        monthOrderMap.put("MAR", 3);
        monthOrderMap.put("APR", 4);
        monthOrderMap.put("MAY", 5);
        monthOrderMap.put("JUN", 6);
        monthOrderMap.put("JUL", 7);
        monthOrderMap.put("AUG", 8);
        monthOrderMap.put("SEP", 9);
        monthOrderMap.put("OCT", 10);
        monthOrderMap.put("NOV", 11);
        monthOrderMap.put("DEC", 12);

        String month = fraction.getMonth();

        return monthOrderMap.get(month);
    }

}
