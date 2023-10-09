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
@Table(name = "profile")
public class Profile extends AbstractEntity {

    @Column(name = "name", unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profile")
    @JoinColumn(name = "meter_id", referencedColumnName = "id")
    private Meter meter;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Fraction> fractions = new ArrayList<>();

}
