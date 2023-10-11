package com.example.consumption.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "status = 'ACTIVE'")
@Table(name = "meter")
public class Meter extends AbstractEntity {

    @Column(name = "meter_counter")
    private double meterCounter;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false, referencedColumnName = "id")
    private Profile profile;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MeterReading> meterReadings = new ArrayList<>();

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Consumption> consumptions = new ArrayList<>();

}