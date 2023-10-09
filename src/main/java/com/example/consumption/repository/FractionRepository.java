package com.example.consumption.repository;

import com.example.consumption.model.entity.Fraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FractionRepository extends JpaRepository<Fraction, Long> {

    @Query("SELECT f FROM Fraction f " +
            "WHERE f.profile.id = :profileId " +
            "AND f.id = :fractionId ")
    Optional<Fraction> findByIdAndProfileId(@Param("profileId") Long profileId,
                                            @Param("fractionId") Long fractionId);

}
