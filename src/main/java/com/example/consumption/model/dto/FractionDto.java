package com.example.consumption.model.dto;

import com.example.consumption.model.entity.Fraction;
import com.example.consumption.model.enums.EntityStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FractionDto {

    private Long id;

    private Long profileId;

    private String month;

    private double value;

    private EntityStatus entityStatus;

    public static FractionDto fromEntity(Fraction fraction) {
        return FractionDto.builder()
                .id(fraction.getId())
                .profileId(fraction.getProfile().getId())
                .entityStatus(fraction.getStatus())
                .month(fraction.getMonth())
                .value(fraction.getFractionValue())
                .build();
    }

}
