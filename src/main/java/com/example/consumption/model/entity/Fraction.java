package com.example.consumption.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "status = 'ACTIVE'")
@Table(name = "fraction")
public class Fraction extends AbstractEntity {

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "fraction_value", nullable = false)
    private double value;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
