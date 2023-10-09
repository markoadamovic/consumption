package com.example.consumption.service.impl;

import com.example.consumption.exception.BadRequestException;
import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.UpdateFractionDto;
import com.example.consumption.model.dto.FractionDto;
import com.example.consumption.model.entity.Fraction;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.model.enums.EntityStatus;
import com.example.consumption.repository.FractionRepository;
import com.example.consumption.service.FractionService;
import com.example.consumption.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FractionServiceImpl implements FractionService {

    private final FractionRepository fractionRepository;

    private final ProfileService profileService;

    @Override
    public Fraction getFractionModel(Long profileId, Long fractionId) {
        return fractionRepository.findByIdAndProfileId(profileId, fractionId)
                .orElseThrow(() -> new NotFoundException(String.format("Fraction data for profile with id %s and fractionId %s " +
                        "is not found", profileId, fractionId)));
    }

    @Override
    public List<FractionDto> getFractions(Long profileId) {
        Profile profile = profileService.getProfileModel(profileId);

        return profile.getFractions().stream()
                .map(FractionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public FractionDto getFraction(Long profileId, Long fractionId) {
        return FractionDto.fromEntity(getFractionModel(profileId, fractionId));
    }

    @Override
    public FractionDto createFraction(Long profileId, FractionDto fractionDto) {
        Profile profile = profileService.getProfileModel(profileId);

        if(profile.getFractions().stream().anyMatch(fraction -> fraction.getMonth().equals(fractionDto.getMonth()))) {
            throw new BadRequestException(String.format("Fraction for month %s is already created", fractionDto.getMonth()));
        }

        Fraction fraction = Fraction.builder()
                .month(fractionDto.getMonth())
                .fractionValue(fractionDto.getValue())
                .profile(profile)
                .build();

        double fractionsValue = fractionDto.getValue() + profile.getFractions().stream().mapToDouble(Fraction::getFractionValue).sum();
        if (fractionsValue > 1.0) {
            throw new BadRequestException("Validation error: Fractions must have a total size and value not greater then 12 or 1.0");
        }

        return FractionDto.fromEntity(fractionRepository.save(fraction));
    }

    @Override
    public FractionDto updateFraction(Long profileId, Long fractionId, FractionDto fractionDto) {
        Fraction fraction = getFractionModel(profileId, fractionId);
        Profile profile = fraction.getProfile();
        double fractionsValue = profile.getFractions().stream().mapToDouble(Fraction::getFractionValue).sum();
        if (fractionsValue + fractionDto.getValue() > 1.0) {
            throw new BadRequestException("Validation error: Fractions must have a total value of 1.0");
        }
        fraction.setFractionValue(fractionDto.getValue());

        return FractionDto.fromEntity(fractionRepository.save(fraction));
    }

    @Override
    public List<FractionDto> updateFractions(Long profileId, UpdateFractionDto fractionDto) {
        Profile profile = profileService.getProfileModel(profileId);
        List<Fraction> fractions = profile.getFractions();
        for (Map.Entry<String, Double> entry : fractionDto.getFractions().entrySet()) {
            String month = entry.getKey();
            double fractionValue = entry.getValue();
            fractions.stream()
                    .filter(fraction -> fraction.getMonth().equals(month))
                    .forEach(fraction -> fraction.setFractionValue(fractionValue));
        }
        double fractionsValue = fractions.stream().mapToDouble(Fraction::getFractionValue).sum();
        if (fractionsValue != 1.0) {
            throw new BadRequestException("Validation error: Fractions must have exactly 12 entries with a total value of 1.0");
        }

        return fractionRepository.saveAll(fractions)
                .stream().map(FractionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFraction(Long profileId, Long fractionId) {
        Fraction fraction = getFractionModel(profileId, fractionId);
        fraction.setStatus(EntityStatus.DELETED);
        fractionRepository.save(fraction);
    }

}
