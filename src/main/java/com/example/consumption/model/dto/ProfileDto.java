package com.example.consumption.model.dto;

import com.example.consumption.model.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ProfileDto {

    private Long id;

    private String name;

    private List<FractionDto> fractions;

    private MeterDto meter;

    public static ProfileDto fromEntity(Profile profile) {
        return ProfileDto.builder()
                .id(profile.getId())
                .name(profile.getName())
                .fractions(profile.getFractions()
                        .stream()
                        .map(FractionDto::fromEntity)
                        .collect(Collectors.toList()))
                .meter(MeterDto.fromEntity(profile.getMeter()))
                .build();
    }

}
