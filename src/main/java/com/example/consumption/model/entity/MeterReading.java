package com.example.consumption.model.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "status = 'ACTIVE'")
@Table(name = "meter_reading")
public class MeterReading extends AbstractEntity {

    @Column(name = "date_of_measuring")
    private LocalDateTime dateOfMeasuring;

    @Column(name = "value")
    private double value = 0;

    @ManyToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;

}
