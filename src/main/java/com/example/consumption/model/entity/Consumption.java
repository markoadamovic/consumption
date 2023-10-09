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
@Table(name = "consumption")
public class Consumption extends AbstractEntity {

    @Column(name = "consumption_value")
    private double consumptionValue;

    @Column(name = "month")
    private String month;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;
}
